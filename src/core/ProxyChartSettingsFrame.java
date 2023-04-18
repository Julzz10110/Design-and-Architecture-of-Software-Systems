package core;

import data.DataFrame;

public class ProxyChartSettingsFrame implements ChartSettingsFrame {
    private DataFrame dataFrame;
    private RealChartSettingsFrame chartSettingsFrame;

    public ProxyChartSettingsFrame(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    @Override
    public void displayFrame() {
        if (chartSettingsFrame == null && dataFrame != null) {
            chartSettingsFrame = new RealChartSettingsFrame(dataFrame);
            chartSettingsFrame.displayFrame();
            System.out.println(ProxyChartSettingsFrame.class.getName() + " : Данные загружены, окно настройки графика запущено (chartSettingsFrame is null: "
                    + (chartSettingsFrame == null) + ").");
        } else {
            System.err.println(ProxyChartSettingsFrame.class.getName() + " : ОШИБКА: Данные не загружены, действие недоступно (chartSettingsFrame is null: "
                    + (chartSettingsFrame == null) + ").");
        }
    }
}
