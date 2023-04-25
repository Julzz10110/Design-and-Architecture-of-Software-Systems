package visual.flyweight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlyweightExample {

    private static int FRAME_HEIGHT = 400;
    private static int FRAME_WIDTH = 500;

    private static int PANEL_HEIGHT = FRAME_HEIGHT;
    private static int PANEL_WIDTH = FRAME_WIDTH - 100;

    private static int PiXELS_DRAWN = 1000;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        NoisePanel noisePanel = new NoisePanel() {
            @Override
            public void paint(Graphics g) {
                Graphics graphics = frame.getGraphics();
                super.paint(graphics);
            }
        };
        noisePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        frame.add(noisePanel);

        JButton button = new JButton("OK");
        frame.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int grayCount = 0;
                int blackCount = 0;
                for (int i = 0; i < PiXELS_DRAWN; i++) {
                    noisePanel.setPixel(NoisePanel.random(0, PANEL_WIDTH), NoisePanel.random(0, PANEL_HEIGHT), "gray", Color.GRAY);
                    grayCount++;
                    noisePanel.setPixel(NoisePanel.random(0, PANEL_WIDTH), NoisePanel.random(0, PANEL_HEIGHT), "black", Color.BLACK);
                    blackCount++;
                    noisePanel.repaint();
                    noisePanel.revalidate();
                }
                System.out.println("Проверка на концептуальную идентичность двух объектов: \n"
                        + "один и тот же объект?: " + (noisePanel.getPixels().get(0) == noisePanel.getPixels().get(2)) + "\n"
                        + "одинаковый тип объектов?: " + (noisePanel.getPixels().get(0).getType() == noisePanel.getPixels().get(2).getType()));
                System.out.println("Отрисовано " + grayCount + " серых и " + blackCount + " черных объектов.");
            }
        });

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);


    }

}
