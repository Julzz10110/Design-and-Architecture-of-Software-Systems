package core;

import data.DataFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChartSettingsFrame extends JFrame {
    private static final int SETTINGS_FRAME_WIDTH = (int) (0.5 * MainFrame.MAIN_FRAME_WIDTH);
    private static final int SETTINGS_FRAME_HEIGHT = (int) (0.5 * MainFrame.MAIN_FRAME_HEIGHT);

    private JPanel settingsPanel;
    private JPanel concretePanel;
    private JLabel titleLabel;
    private JTextField titleTextField;
    private ButtonGroup group;
    private JRadioButton xyRadioButton;
    private JRadioButton barRadioButton;
    private JLabel argumentsLabel;
    private JLabel valuesLabel;
    private JList argumentsList;
    private  JList valuesList;

    private ScrollPane argumentsScrollPane;
    private ScrollPane valuesScrollPane;
    private JCheckBox legendCheckBox;

    private JButton okButton;

    private DataFrame dataFrame;


    public ChartSettingsFrame(DataFrame dataFrame) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Настройки графика");
        setSize(SETTINGS_FRAME_WIDTH, SETTINGS_FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        this.dataFrame = dataFrame;

        settingsPanel = new JPanel();

        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        add(settingsPanel);

        titleLabel = new JLabel("Введите название графика: ");
        titleTextField = new JTextField();
        titleTextField.setPreferredSize(new Dimension((int) (0.6 * SETTINGS_FRAME_WIDTH), (int) (0.1 * SETTINGS_FRAME_HEIGHT)));
        xyRadioButton = new JRadioButton("График XY");
        barRadioButton = new JRadioButton("Столбчатая диаграмма");
        group = new ButtonGroup();
        group.add(xyRadioButton);
        group.add(barRadioButton);
        okButton = new JButton("Ок");

        settingsPanel.add(titleLabel);
        settingsPanel.add(titleTextField);
        settingsPanel.add(xyRadioButton);
        settingsPanel.add(barRadioButton);

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

        legendCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
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

}
