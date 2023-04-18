package core;

import charts.Chart;
import charts.ChartFactory;
import data.DataFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static core.MainFrame.getCanvasPanel;

public class RealChartSettingsFrame extends JFrame implements ChartSettingsFrame {
    private static final int SETTINGS_FRAME_WIDTH = (int) (0.5 * MainFrame.MAIN_FRAME_WIDTH);
    private static final int SETTINGS_FRAME_HEIGHT = (int) (0.5 * MainFrame.MAIN_FRAME_HEIGHT);

    private JPanel settingsPanel;
    private JPanel concretePanel;
    private JLabel titleLabel;
    private JTextField titleTextField;
    private ButtonGroup group;
    private JRadioButton xyRadioButton;
    private JRadioButton barRadioButton;
    private JRadioButton pieRadioButton;
    private JRadioButton pie3DRadioButton;
    private JLabel argumentsLabel;
    private JLabel valuesLabel;
    private JList argumentsList;
    private JList valuesList;

    private ScrollPane argumentsScrollPane;
    private ScrollPane valuesScrollPane;
    private JCheckBox legendCheckBox;

    private JButton okButton;
    private Chart chart;

    private DataFrame dataFrame;


    public RealChartSettingsFrame(DataFrame dataFrame) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Настройки графика");
        setSize(SETTINGS_FRAME_WIDTH, SETTINGS_FRAME_HEIGHT);

        this.dataFrame = dataFrame;

        settingsPanel = new JPanel();

        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        add(settingsPanel);

        titleLabel = new JLabel("Введите название графика: ");
        titleTextField = new JTextField();
        titleTextField.setPreferredSize(new Dimension((int) (0.6 * SETTINGS_FRAME_WIDTH), (int) (0.1 * SETTINGS_FRAME_HEIGHT)));
        xyRadioButton = new JRadioButton("График XY");
        xyRadioButton.setMnemonic(Chart.ChartType.XY_CHART);
        barRadioButton = new JRadioButton("Столбчатая диаграмма");
        barRadioButton.setMnemonic(Chart.ChartType.BAR_CHART);
        pieRadioButton = new JRadioButton("Круговая диаграмма");
        pieRadioButton.setMnemonic(Chart.ChartType.PIE_CHART);
        pie3DRadioButton = new JRadioButton("Круговая диаграмма 3D");
        pie3DRadioButton.setMnemonic(Chart.ChartType.PIE_CHART_3D);
        group = new ButtonGroup();
        group.add(xyRadioButton);
        group.add(barRadioButton);
        group.add(pieRadioButton);
        group.add(pie3DRadioButton);
        okButton = new JButton("Ок");

        settingsPanel.add(titleLabel);
        settingsPanel.add(titleTextField);
        settingsPanel.add(xyRadioButton);
        settingsPanel.add(barRadioButton);
        settingsPanel.add(pieRadioButton);
        settingsPanel.add(pie3DRadioButton);

        concretePanel = new JPanel();
        concretePanel.setPreferredSize(new Dimension((int) (0.8 * SETTINGS_FRAME_WIDTH), (int) (0.6 * SETTINGS_FRAME_HEIGHT)));
        concretePanel.setLayout(new GridLayout(1, 2));
        argumentsLabel = new JLabel();
        argumentsList = new JList<>(dataFrame.getData().keySet().toArray(new String[0]));
        valuesLabel = new JLabel();
        valuesList = new JList<>(dataFrame.getData().keySet().toArray(new String[0]));
        argumentsScrollPane = new ScrollPane();
        argumentsScrollPane.add(argumentsList);
        valuesScrollPane = new ScrollPane();
        valuesScrollPane.add(valuesList);
        concretePanel.add(argumentsLabel);
        concretePanel.add(argumentsScrollPane);
        concretePanel.add(valuesLabel);
        concretePanel.add(valuesScrollPane);
        settingsPanel.add(concretePanel);

        legendCheckBox = new JCheckBox("Добавить легенду");
        okButton = new JButton("Ок");

        settingsPanel.add(legendCheckBox);
        settingsPanel.add(okButton);

        xyRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showXYChartSettings();
            }
        });

        barRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showBarChartSettings();
            }
        });

        pieRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPieChartSettings();
            }
        });

        pie3DRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPieChartSettings();
            }
        });

        legendCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chart = ChartFactory.createChart(
                        group.getSelection().getMnemonic(),
                        titleTextField.getText(),
                        dataFrame,
                        argumentsList.getSelectedValue().toString(),
                        valuesList.getSelectedValue().toString());
                MainFrame.setChart(chart);
                MainFrame.getCanvasPanel().removeAll();
                MainFrame.getCanvasPanel().add(chart.getChartPanel());
                MainFrame.getCanvasPanel().revalidate();
                getCanvasPanel().repaint();
                dispose();
                //System.out.println(group.getSelection().getMnemonic());
                //JFrame testFrame = new JFrame();
                //testFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                //testFrame.add(chart.getChartPanel());
                //testFrame.setSize(SETTINGS_FRAME_WIDTH, SETTINGS_FRAME_HEIGHT);
                //testFrame.setLocationRelativeTo(null);
                //testFrame.setVisible(true);
            }
        });
    }


    private void showXYChartSettings() {
        argumentsLabel.setText("Значения по оси X:");
        valuesLabel.setText("Значения по оси Y:");
    }

    private void showBarChartSettings() {
        argumentsLabel.setText("Сравниваемые категории:");
        valuesLabel.setText("Измеряемая величина:");
    }

    private void showPieChartSettings() {
        argumentsLabel.setText("Сравниваемые категории:");
        valuesLabel.setText("Измеряемая величина:");
    }

    @Override
    public void displayFrame() {
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
