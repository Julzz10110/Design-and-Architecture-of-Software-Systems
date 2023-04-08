package data;

import java.util.ArrayList;
import java.util.Arrays;

public class StatUtils {

    public static int count(DataFrame df, String key) {
        int count = 0;
        for (Object obj : df.getData().get(key)) {
            if(obj != null) count++;
        }
            return count;
    }

    public static double sum(DataFrame df, String key) {
        if (df.getData().get(key).size() == 0) return Double.NaN;
        double sum = 0;
        for (Object obj : df.getData().get(key)) {
            try {
                if (obj != null && !Double.isNaN((Double) obj)) {
                    sum += (double) obj;
                }
            } catch (ClassCastException ex) {
                return Double.NaN;
            }
        }
        return sum;
    }


    public static double mean(DataFrame df, String key) {
        if (df.getData().get(key).size() == 0) return Double.NaN;
        try {
            return sum(df, key) / count(df, key);
        } catch (ClassCastException ex) {
        return Double.NaN;
    }
    }

    public static double var(DataFrame df, String key) {
        if (df.getData().get(key).size() == 0) return Double.NaN;
        try {
            double avg = mean(df, key);
            double sum = 0.0;
            for (int i = 0; i < df.getData().get(key).size(); i++) {
                sum += ((double) df.getData().get(key).get(i) - avg) * ((double) df.getData().get(key).get(i) - avg);
            }
            return sum / (df.getData().get(key).size() - 1);
        } catch (ClassCastException ex) {
            return Double.NaN;
        }
    }

    public static double std(DataFrame df, String key) {
        if (df.getData().get(key).size() == 0) return Double.NaN;
        try {
            return Math.sqrt(var(df, key));
        } catch (ClassCastException ex) {
            return Double.NaN;
        }
    }


    public static double min(DataFrame df, String key) {
        if (df.getData().get(key).size() == 0) return Double.NaN;
        double min = Double.MAX_VALUE;
        try {
            for (Object obj : df.getData().get(key)) {
                if(obj != null && !Double.isNaN((Double) obj)) {
                    if ((double) obj < min) min = (double) obj;
                }
            }
            if (min != Double.MAX_VALUE) return min;
            else return Double.NaN;

        } catch (ClassCastException ex) {
            return Double.NaN;
        }
    }

    public static double max(DataFrame df, String key) {
        if (df.getData().get(key).size() == 0) return Double.NaN;
        double max = Double.MIN_VALUE;
        try {
            for (Object obj : df.getData().get(key)) {
                if (obj != null && !Double.isNaN((Double) obj)) {
                    if ((double) obj > max) max = (double) obj;
                }
            }
            if (max != Double.MIN_VALUE) return max;
            else return Double.NaN;
        } catch (ClassCastException ex) {
            return Double.NaN;
        }
    }


    public static double percentile(DataFrame df, String key, double percent)
    {
        int count;
        ArrayList<Object> data = df.getData().get(key);
        double[] normalizedData = new double[df.getData().get(key).size()];
        try {
            for (int i = 0; i < normalizedData.length; i++) normalizedData[i] = (double)data.get(i);
            Arrays.sort(normalizedData);
            int index = (int) (percent * normalizedData.length);

            return normalizedData[index];
        } catch (ClassCastException ex) {
            return Double.NaN;
        }
        }

}
