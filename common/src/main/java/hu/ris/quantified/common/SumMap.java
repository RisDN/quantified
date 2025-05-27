package hu.ris.quantified.common;

import java.util.Map;

public class SumMap {

    public static <K, V extends Number> int sumValues(Map<K, V> map) {
        int sum = 0;
        for (V value : map.values()) {
            sum += value.doubleValue();
        }
        return sum;
    }

}
