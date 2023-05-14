package core;

import charts.Chart;
import charts.ChartFactory;
import charts.XYChart;
import data.DataFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
    private boolean hasLegend;
    private JButton okButton;
    private Chart chart = new XYChart("", false);

    private DataFrame dataFrame;


    public RealChartSettingsFrame(DataFrame dataFrame) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(MainFrame.getResourceBundle().getString("chart_settings"));
        setSize(SETTINGS_FRAME_WIDTH, SETTINGS_FRAME_HEIGHT);
        setBackground(MainFrame.getCanvasPanel().getBackground());

        this.dataFrame = dataFrame;

        settingsPanel = new JPanel();

        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        add(settingsPanel);

        titleLabel = new JLabel(MainFrame.getResourceBundle().getString("enter_chart_name"));
        titleTextField = new JTextField();
        titleTextField.setPreferredSize(new Dimension((int) (0.6 * SETTINGS_FRAME_WIDTH), (int) (0.1 * SETTINGS_FRAME_HEIGHT)));
        xyRadioButton = new JRadioButton(MainFrame.getResourceBundle().getString("xy_chart"));
        xyRadioButton.setMnemonic(Chart.ChartType.XY_CHART);
        barRadioButton = new JRadioButton(MainFrame.getResourceBundle().getString("bar_chart"));
        barRadioButton.setMnemonic(Chart.ChartType.BAR_CHART);
        pieRadioButton = new JRadioButton(MainFrame.getResourceBundle().getString("pie_chart"));
        pieRadioButton.setMnemonic(Chart.ChartType.PIE_CHART);
        pie3DRadioButton = new JRadioButton(MainFrame.getResourceBundle().getString("pie_chart_3d"));
        pie3DRadioButton.setMnemonic(Chart.ChartType.PIE_CHART_3D);
        group = new ButtonGroup();
        group.add(xyRadioButton);
        group.add(barRadioButton);
        group.add(pieRadioButton);
        group.add(pie3DRadioButton);
        okButton = new JButton(MainFrame.getResourceBundle().getString("ok"));

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

        legendCheckBox = new JCheckBox(MainFrame.getResourceBundle().getString("add_legend"));
        okButton = new JButton(MainFrame.getResourceBundle().getString("ok"));

        settingsPanel.add(legendCheckBox);
        settingsPanel.add(okButton);
        updateDesign();

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

        legendCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                hasLegend = e.getStateChange() == 1;
                Chart.setLegendIncluded(e.getStateChange() == 1);
            }
        });

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChartFactory.clearChartConfiguration();
                chart = ChartFactory.createChart(
                        group.getSelection().getMnemonic(),
                        titleTextField.getText(),
                        dataFrame,
                        argumentsList.getSelectedValue().toString(),
                        valuesList.getSelectedValue().toString(),
                        hasLegend);
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
        argumentsLabel.setText(MainFrame.getResourceBundle().getString("x_values"));
        valuesLabel.setText(MainFrame.getResourceBundle().getString("y_values"));
    }

    private void showBarChartSettings() {
        argumentsLabel.setText(MainFrame.getResourceBundle().getString("compared_categories"));
        valuesLabel.setText(MainFrame.getResourceBundle().getString("target_value"));
    }

    private void showPieChartSettings() {
        argumentsLabel.setText(MainFrame.getResourceBundle().getString("compared_categories"));
        valuesLabel.setText(MainFrame.getResourceBundle().getString("target_value"));
    }

    @Override
    public void displayFrame() {
        MainFrame.getSnapshotManager().setEnabled(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void updateDesign() {
        titleLabel.setForeground(MainFrame.getInstance().getTextColor());
        ;
        argumentsLabel.setForeground(MainFrame.getInstance().getTextColor());
        valuesLabel.setForeground(MainFrame.getInstance().getTextColor());
        xyRadioButton.setForeground(MainFrame.getInstance().getTextColor());
        barRadioButton.setForeground(MainFrame.getInstance().getTextColor());
        pieRadioButton.setForeground(MainFrame.getInstance().getTextColor());
        pie3DRadioButton.setForeground(MainFrame.getInstance().getTextColor());
        legendCheckBox.setForeground(MainFrame.getInstance().getTextColor());


        titleLabel.setFont(MainFrame.getInstance().getTextFont());
        ;
        argumentsLabel.setFont(MainFrame.getInstance().getTextFont());
        valuesLabel.setFont(MainFrame.getInstance().getTextFont());
        xyRadioButton.setFont(MainFrame.getInstance().getTextFont());
        barRadioButton.setFont(MainFrame.getInstance().getTextFont());
        pieRadioButton.setFont(MainFrame.getInstance().getTextFont());
        pie3DRadioButton.setFont(MainFrame.getInstance().getTextFont());
        legendCheckBox.setFont(MainFrame.getInstance().getTextFont());
        okButton.setFont(MainFrame.getInstance().getTextFont());

        xyRadioButton.setBackground(MainFrame.getInstance().getBackgroundColor());
        barRadioButton.setBackground(MainFrame.getInstance().getBackgroundColor());
        pieRadioButton.setBackground(MainFrame.getInstance().getBackgroundColor());
        pie3DRadioButton.setBackground(MainFrame.getInstance().getBackgroundColor());
        legendCheckBox.setBackground(MainFrame.getInstance().getBackgroundColor());
        settingsPanel.setBackground(MainFrame.getInstance().getBackgroundColor());
        concretePanel.setBackground(MainFrame.getInstance().getBackgroundColor());
    }
}
