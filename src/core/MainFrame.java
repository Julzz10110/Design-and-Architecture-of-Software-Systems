package core;

import charts.BarChart;
import charts.Chart;
import data.DataMediator;
import data.StatUtils;
import tools.ChartSnapshotManager;
import tools.FileManager;
import tools.ImageConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class MainFrame extends JFrame{
    public static final int MAIN_FRAME_WIDTH = 1600;
    public static final int MAIN_FRAME_HEIGHT = 800;
    private static final int ICON_WIDTH = 20;
    private static final int ICON_HEIGHT = 20;
    private JFrame frame;
    private JMenuBar menuBar;
    private JLabel loadedDataLabel;
    private JLabel statInfoLabel;
    private Box contents;
    private JPanel dataPanel;
    private JPanel tablePanel;
    private JPanel chartPanel;
    private JTable inputTable = new JTable();
    private JTable statTable = new JTable();

    private ChartSnapshotManager snapshotManager = new ChartSnapshotManager();

    private JLabel savingDirectoryPathLabel;
    private ChartSettingsFrame chartSettingsFrame;


    private DataMediator mediator;

    public MainFrame() {
        create();
        Container contentPane = this.getContentPane();
        GridLayout mainLayout = new GridLayout(0, 2);
        setLayout(mainLayout);
        dataPanel = new JPanel();
        loadedDataLabel = new JLabel("Загруженные данные: ");
        dataPanel.add(loadedDataLabel);
        contents = new Box(BoxLayout.Y_AXIS);
        contents.add(new JScrollPane(inputTable));
        tablePanel = new JPanel();
        tablePanel.add(contents);
        dataPanel.add(tablePanel);
        tablePanel.repaint();
        tablePanel.revalidate();

        statInfoLabel = new JLabel("Статистические сведения о данных: ");
        dataPanel.add(statInfoLabel);

        chartPanel = new JPanel();
        dataPanel.setPreferredSize(new Dimension((int) (0.3 * MAIN_FRAME_WIDTH), MAIN_FRAME_HEIGHT));
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        add(dataPanel);
        add(chartPanel);

        mediator = DataMediator.getInstance();
        mediator.setMainFrame(this);
        mediator.setFileManager(FileManager.getInstance());


        // тестовая прорисовка
        double[] values = new double[3];
        String[] names = new String[3];
        values[0] = 1;
        names[0] = "Item 1";

        values[1] = 2;
        names[1] = "Item 2";

        values[2] = -4;
        names[2] = "Item 3";

        Chart chart = new BarChart(values, names, "Название");

        chartPanel.add(chart, BorderLayout.CENTER);

        JButton buildChartButton = new JButton("Построить график");
        buildChartButton.setBackground(new Color(0x7C7CE3));
        chartPanel.add(buildChartButton, BorderLayout.SOUTH);

        JButton snapshotButton = new JButton("Сохранить в PNG");
        snapshotButton.setBackground(new Color(0xFF0000));
        chartPanel.add(snapshotButton, BorderLayout.SOUTH);

        savingDirectoryPathLabel = new JLabel();
        savingDirectoryPathLabel.setText("Путь до директории сохранения графиков: " + snapshotManager.getSnapshotsPath());
        chartPanel.add(savingDirectoryPathLabel);

        snapshotButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    snapshotManager.getSaveSnapshot(chart, snapshotManager.getSnapshotsPath() + "/" + LocalDateTime.now());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buildChartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chartSettingsFrame = new ChartSettingsFrame(mediator.sendDataframe());
            }
        });

    }
    private JMenu createFileMenu() {
        // Создание выпадающего меню
        JMenu file = new JMenu("Файл");
        // Пункт меню "Открыть" с изображением
        Image image = null;
        try {
            image = ImageIO.read(getClass().getResource("/res/open.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JMenuItem open = new JMenuItem("Открыть",
                new ImageIcon(image.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_DEFAULT)));
        // Пункт меню из команды с выходом из программы
        // Добавление к пункту меню изображения
        // Добавим в меню пункта open
        file.add(open);
        // Добавление разделителя
        file.addSeparator();

        try {
            image = ImageIO.read(getClass().getResource("/res/convert.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JMenuItem convert = new JMenuItem("Конвертировать изображение",
                new ImageIcon(image.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_DEFAULT)));
        file.add(convert);

        open.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION){

                    File file = fileChooser.getSelectedFile();
                    int dotIndex = file.getAbsolutePath().lastIndexOf('.');
                    String extension = (dotIndex == -1) ? "" : file.getAbsolutePath().substring(dotIndex + 1);
                    if (extension.equals("xlsx") || extension.equals("xls"))  {
                        mediator.getFileManager().loadDataExcel(file.getPath());
                    } else if (extension.equals("csv")) {
                        mediator.getFileManager().loadDataCSV(file.getPath());
                    } else {
                        System.out.println("ОШИБКА: Невозможно открыть файл");
                        return;
                    }
                    DefaultTableModel inputTableModel = mediator.sendTabelModel();
                    inputTable.setModel(inputTableModel);
                    contents = new Box(BoxLayout.Y_AXIS);
                    contents.add(new JScrollPane(inputTable));
                    tablePanel.add(contents);
                    tablePanel.repaint();
                    tablePanel.revalidate();

                    String[] keySetArray = new String[mediator.sendDataframe().getData().keySet().toArray().length + 1];
                    keySetArray[0] = "Название величины";
                    for (int i = 1; i < keySetArray.length; i++) {
                        keySetArray[i] = mediator.sendDataframe().getData().keySet().toArray()[i - 1].toString();

                    }

                    DefaultTableModel statTableModel = new DefaultTableModel(keySetArray, 0);
                    String[] statFunctions = new String[]{"count", "mean", "std", "min", "max", "1st quantile", "median", "3rd quantile"};
                    ArrayList<String[]> statData = new ArrayList<>();
                    statData.add(statFunctions);
                    for (String key : mediator.sendDataframe().getData().keySet()) {
                        String[] columnStatData = new String[statFunctions.length];
                        columnStatData[0] = String.valueOf(StatUtils.count(mediator.sendDataframe(), key));
                        columnStatData[1] = String.valueOf(StatUtils.mean(mediator.sendDataframe(), key));
                        columnStatData[2] = String.valueOf(StatUtils.std(mediator.sendDataframe(), key));
                        columnStatData[3] = String.valueOf(StatUtils.min(mediator.sendDataframe(), key));
                        columnStatData[4] = String.valueOf(StatUtils.max(mediator.sendDataframe(), key));
                        columnStatData[5] = String.valueOf(StatUtils.percentile(mediator.sendDataframe(), key, 0.25));
                        columnStatData[6] = String.valueOf(StatUtils.percentile(mediator.sendDataframe(), key, 0.5));
                        columnStatData[7] = String.valueOf(StatUtils.percentile(mediator.sendDataframe(), key, 0.75));
                        statData.add(columnStatData);
                        System.out.println(Arrays.toString(columnStatData));
                    }

                    String[] rowStatData = new String[statFunctions.length];
                    int index = 0;
                    for (int i = 0; i < rowStatData.length; i++) {
                        int j = 0;
                        for (int k = 0; k < statData.size(); k++) {
                            rowStatData[j] = statData.get(k)[index];
                            j++;
                        }
                        statTableModel.addRow(rowStatData);
                        index++;
                    }
                    statTable.setModel(statTableModel);
                    Box contents1 = new Box(BoxLayout.Y_AXIS);
                    contents1.add(new JScrollPane(statTable));
                    dataPanel.add(contents1);
                    dataPanel.repaint();
                    dataPanel.revalidate();

                }else{
                    System.out.println("Open command canceled");
                }
            }
        });

        convert.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser(snapshotManager.getSnapshotsPath());
                int option = fileChooser.showOpenDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    JFrame chooseFormatsFrame = new JFrame();
                    int frameWidth = (int)(0.5 * MAIN_FRAME_WIDTH);
                    int frameHeight = (int)(0.2 * MAIN_FRAME_HEIGHT);
                    chooseFormatsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    chooseFormatsFrame.setTitle("Выбор директории для сохранения графиков");
                    chooseFormatsFrame.setSize((int)(0.5 * MAIN_FRAME_WIDTH), (int)(0.2 * MAIN_FRAME_HEIGHT));
                    chooseFormatsFrame.setLocationRelativeTo(null);
                    chooseFormatsFrame.setResizable(true);
                    chooseFormatsFrame.setVisible(true);
                    chooseFormatsFrame.setLayout(new GridLayout(2, 0));
                    JLabel chooseFormatLabel = new JLabel("Выберите форматы для конвертации изображения");
                    chooseFormatsFrame.add(chooseFormatLabel);
                    JPanel boxesPanel = new JPanel();
                    boxesPanel.setLayout(new FlowLayout());

                    JCheckBox pngBox = new JCheckBox(".png");
                    JCheckBox jpegBox = new JCheckBox(".jpeg");
                    JCheckBox bmpBox = new JCheckBox(".bmp");
                    JCheckBox gifBox = new JCheckBox(".gif");
                    JCheckBox tiffBox = new JCheckBox(".tiff");

                    boxesPanel.add(pngBox);
                    boxesPanel.add(jpegBox);
                    boxesPanel.add(bmpBox);
                    boxesPanel.add(gifBox);
                    boxesPanel.add(tiffBox);

                    JButton okButton = new JButton("Ок");
                    okButton.setBackground(new Color(0x7C7CE3));
                    boxesPanel.add(okButton);

                    chooseFormatsFrame.add(boxesPanel);

                    ArrayList<String> selectedFormats = new ArrayList<>();

                    pngBox.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            if (!selectedFormats.contains("png")) selectedFormats.add("png");
                            else selectedFormats.remove("png");
                        }
                    });

                    jpegBox.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            if (!selectedFormats.contains("jpeg")) selectedFormats.add("jpeg");
                            else selectedFormats.remove("jpeg");
                        }
                    });

                    bmpBox.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            if (!selectedFormats.contains("bmp")) selectedFormats.add("bmp");
                            else selectedFormats.remove("bmp");
                        }
                    });

                    gifBox.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            if (!selectedFormats.contains("gif")) selectedFormats.add("gif");
                            else selectedFormats.remove("gif");
                        }
                    });

                    tiffBox.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            if (!selectedFormats.contains("tiff")) selectedFormats.add("tiff");
                            else selectedFormats.remove("tiff");
                        }
                    });

                    okButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            for (String format : selectedFormats) ImageConverter.convert(file.getAbsolutePath(), format);
                            chooseFormatsFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        }
                    });
                }
            }
        });

        return file;
    }


    private JMenu createSettingsMenu()
    {

        JMenu settings = new JMenu("Настройки");
        Image image = null;
        try {
            image = ImageIO.read(getClass().getResource("/res/roller.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JMenuItem display = new JMenuItem("Настроить внешний вид",
                new ImageIcon(image.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_DEFAULT)));

        try {
            image = ImageIO.read(getClass().getResource("/res/save.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JMenuItem settingShapshotPath = new JMenuItem("Выбрать папку для графиков",
                new ImageIcon(image.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_DEFAULT)));

        settings.add(display);
        settings.addSeparator();
        settings.add(settingShapshotPath);

        settingShapshotPath.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrame shapshotPathFrame = new JFrame();
                int frameWidth = (int)(0.5 * MAIN_FRAME_WIDTH);
                int frameHeight = (int)(0.2 * MAIN_FRAME_HEIGHT);
                shapshotPathFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                shapshotPathFrame.setTitle("Выбор директории для сохранения графиков");
                shapshotPathFrame.setSize((int)(0.5 * MAIN_FRAME_WIDTH), (int)(0.2 * MAIN_FRAME_HEIGHT));
                shapshotPathFrame.setLocationRelativeTo(null);
                shapshotPathFrame.setResizable(true);
                shapshotPathFrame.setVisible(true);
                shapshotPathFrame.setLayout(new FlowLayout());

                TextField directoryPathField = new TextField();
                directoryPathField.setPreferredSize(new Dimension((int)(0.7 * frameWidth), (int)(0.2 * frameHeight)));
                shapshotPathFrame.add(directoryPathField);

                JButton setDirectoryButton = new JButton("Открыть");
                setDirectoryButton.setBackground(new Color(0x7C7CE3));
                shapshotPathFrame.add(setDirectoryButton);

                JButton okButton = new JButton("Ок");
                okButton.setBackground(new Color(0xD9CFCF));
                shapshotPathFrame.add(okButton);

                final String[] directoryPath = new String[1];
                setDirectoryButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int option = fileChooser.showOpenDialog(frame);
                        if (option == JFileChooser.APPROVE_OPTION) {
                            directoryPath[0] = fileChooser.getSelectedFile().getPath();
                            directoryPathField.setText(directoryPath[0]);
                            if (directoryPath[0] == null) directoryPathField.setText(ChartSnapshotManager.DEFAULT_SNAPSHOTS_PATH);
                        }
                    }
                });

                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        snapshotManager.setSnapshotsPath(directoryPath[0]);
                        if (directoryPath[0] == null) snapshotManager.setSnapshotsPath(ChartSnapshotManager.DEFAULT_SNAPSHOTS_PATH);
                        System.out.println("Директория для сохранения графиков: " + snapshotManager.getSnapshotsPath());
                        savingDirectoryPathLabel.setText("Путь до директории сохранения графиков: " + snapshotManager.getSnapshotsPath());
                        shapshotPathFrame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

                    }
                });
            }
        });
        return settings;
    }

    public void create() {
        frame = new JFrame();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Визуализатор данных");
        setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createSettingsMenu());
        this.setJMenuBar(menuBar);
    }


    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.repaint();
        mainFrame.revalidate();
    }
}
