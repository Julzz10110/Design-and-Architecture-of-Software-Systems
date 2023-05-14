package core.design;

import core.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIDesignerFrame extends JFrame {
    private static final int DESIGNER_FRAME_WIDTH = (int) (0.5 * MainFrame.MAIN_FRAME_WIDTH);
    private static final int DESIGNER_FRAME_HEIGHT = (int) (0.4 * MainFrame.MAIN_FRAME_HEIGHT);
    private MainFrame designedFrame;
    private JPanel mainPanel;
    private JPanel settingsPanel;
    private JLabel backgroundColorLabel;
    private JLabel textColorLabel;
    private JLabel textFontLabel;

    private JButton backgroundColorButton;
    private JButton textColorButton;
    private JButton textFontButton;
    private JButton okButton;

    public GUIDesignerFrame(MainFrame frame) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(MainFrame.getResourceBundle().getString("design_settings"));
        setSize(DESIGNER_FRAME_WIDTH, DESIGNER_FRAME_HEIGHT);
        mainPanel = new JPanel();
        setLayout(new FlowLayout());
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(3, 2));
        settingsPanel.setSize(DESIGNER_FRAME_WIDTH, (int) (0.9 * DESIGNER_FRAME_HEIGHT));
        mainPanel.add(settingsPanel);
        designedFrame = frame;

        backgroundColorLabel = new JLabel(MainFrame.getResourceBundle().getString("background_color") + "rgb("
                + designedFrame.getBackgroundColor().getRed() + ", "
                + designedFrame.getBackgroundColor().getGreen() + ", "
                + designedFrame.getBackgroundColor().getBlue() + ')');
        textColorLabel = new JLabel(MainFrame.getResourceBundle().getString("text_color") + "rgb("
                + designedFrame.getTextColor().getRed() + ", "
                + designedFrame.getTextColor().getGreen() + ", "
                + designedFrame.getTextColor().getBlue() + ')');
        textFontLabel = new JLabel(MainFrame.getResourceBundle().getString("font") + designedFrame.getTextFont().getFontName());

        backgroundColorButton = new JButton(MainFrame.getResourceBundle().getString("change"));
        textColorButton = new JButton(MainFrame.getResourceBundle().getString("change"));
        textFontButton = new JButton(MainFrame.getResourceBundle().getString("change"));
        okButton = new JButton(MainFrame.getResourceBundle().getString("ok"));

        settingsPanel.add(backgroundColorLabel);
        settingsPanel.add(Box.createHorizontalStrut(1));
        settingsPanel.add(backgroundColorButton);
        settingsPanel.add(new JSeparator(SwingConstants.VERTICAL));

        settingsPanel.add(textColorLabel);
        settingsPanel.add(Box.createHorizontalStrut(1));
        settingsPanel.add(textColorButton);
        settingsPanel.add(new JSeparator(SwingConstants.VERTICAL));

        settingsPanel.add(textFontLabel);
        settingsPanel.add(Box.createHorizontalStrut(1));
        settingsPanel.add(textFontButton);
        settingsPanel.add(new JSeparator(SwingConstants.VERTICAL));

        mainPanel.add(okButton);
        setContentPane(mainPanel);
        updateDesign();
        backgroundColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JColorChooser colorChooser = new JColorChooser();
                Color initialColor = designedFrame.getBackgroundColor();
                Color backgroundColor = JColorChooser.showDialog(null, MainFrame.getResourceBundle().getString("background_color").substring(0,
                        MainFrame.getResourceBundle().getString("background_color").length() - 2), initialColor);
                if (backgroundColor != null) designedFrame.setBackgroundColor(backgroundColor);
                designedFrame.updateDesign();
                backgroundColorLabel.setText(MainFrame.getResourceBundle().getString("background_color") + "rgb("
                        + designedFrame.getBackgroundColor().getRed() + ", "
                        + designedFrame.getBackgroundColor().getGreen() + ", "
                        + designedFrame.getBackgroundColor().getBlue() + ')');
                updateDesign();
                //designedFrame.setFocusable(false);
                //designedFrame.getContentPane().setBackground();
                //designedFrame.updateDesign();
            }
        });

        textColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //JColorChooser colorChooser = new JColorChooser();
                Color initialColor = designedFrame.getTextColor();
                Color textColor = JColorChooser.showDialog(null, MainFrame.getResourceBundle().getString("text_color").substring(0,
                        MainFrame.getResourceBundle().getString("text_color").length() - 2), initialColor);
                if (textColor != null) designedFrame.setTextColor(textColor);
                designedFrame.updateDesign();
                textColorLabel.setText(MainFrame.getResourceBundle().getString("background_color") + "rgb("
                        + designedFrame.getTextColor().getRed() + ", "
                        + designedFrame.getTextColor().getGreen() + ", "
                        + designedFrame.getTextColor().getBlue() + ')');
                updateDesign();
                //designedFrame.updateDesign();
            }
        });

        textFontButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FontChooser fontChooser = new FontChooser();
                int result = fontChooser.showDialog(null);
                if (result == FontChooser.OK_OPTION) {
                    Font font = fontChooser.getSelectedFont();
                    designedFrame.setTextFont(font);
                    textFontLabel.setText(MainFrame.getResourceBundle().getString("font") + designedFrame.getTextFont().getFontName());
                    updateDesign();
                    designedFrame.updateDesign();
                }
                //designedFrame.updateDesign();
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);


    }

    private void updateDesign() {
        backgroundColorLabel.setForeground(MainFrame.getInstance().getTextColor());
        textColorLabel.setForeground(MainFrame.getInstance().getTextColor());
        textFontLabel.setForeground(MainFrame.getInstance().getTextColor());

        backgroundColorLabel.setFont(MainFrame.getInstance().getTextFont());
        textColorLabel.setFont(MainFrame.getInstance().getTextFont());
        textFontLabel.setFont(MainFrame.getInstance().getTextFont());

        backgroundColorButton.setFont(MainFrame.getInstance().getTextFont());
        textColorButton.setFont(MainFrame.getInstance().getTextFont());
        textFontButton.setFont(MainFrame.getInstance().getTextFont());
        okButton.setFont(MainFrame.getInstance().getTextFont());

        mainPanel.setBackground(MainFrame.getInstance().getBackgroundColor());
        settingsPanel.setBackground(MainFrame.getInstance().getBackgroundColor());
    }


}
