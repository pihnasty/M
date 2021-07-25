package math;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class MathP {

    /**
     * The method creates the default Counter with the first initial value equals 0 and step equals i...
     * @param i The step of the Counter.
     * @return The default Counter.
     */
    static public Counter getCounter(int i) {
        return new Counter (i);
    }

    static public Counter getCounter(int initialCount, int step) {
        return new Counter (initialCount,step);
    }

    public static class Counter implements Supplier {
        private int counter = 0;
        private int counterCommon = -1;
        private int i = 0;

        public Counter() {
        }
        public Counter(int i) {
            this.i = i;
        }

        public Counter(int initialCount, int i) {
            this.counter=initialCount;
            this.i = i;
        }
        @Override
        public Integer get() {

            return (counterCommon++ % i)==0 ? counter++ : counter;
        }
    }

    public static double integration (double startValue, double finishValue, Function<Double, Double > function, double dt) {
        double sum = 0;
        for (double value = startValue; value<=finishValue; value+=dt) {
            sum += function.apply(value);
        }
        return sum * dt;
    }

    public static double inverseFunction (Function<Double, Double > function, double tau) {



        return 0.0;
    }

    public static <T> List<List<T>>  initArrayList(T value,int i1size, int i2size) {
        List<List<T>> list = new ArrayList<>();
            for(int i1=0; i1<i1size; i1++) {
                List<T> row = new ArrayList<>();
                for(int i2=0; i2<i2size;i2++) {
                    row.add(value);
                }
                list.add(row);
            }
        return list;
    }

    public static <T extends Comparable<T>> int binarySearch(List<T> list, T value, int low, int high) {
        if (high <= low) {
            return low;
        }
        int mid = low + (high - low) / 2;
        int cmp = value.compareTo(list.get(mid));

        if (cmp == 0) {
            return mid;
        }

        if (cmp < 0) {
            return binarySearch(list, value, low, mid - 1);
        } else {
            return binarySearch(list, value, mid + 1, high);
        }
    }

    public static double zValue (double cumulativeProbabilityValue, TreeMap<Double, Double> treeMapCash, double delta) {
        if(treeMapCash.isEmpty()) {
            double LaplaceFunction = 0.0;
            treeMapCash.put(0.5+LaplaceFunction, 0.0);

            for(double x=delta; x<5.0; x+= delta ) {
                LaplaceFunction += 1.0 / Math.sqrt(2.0*Math.PI)*Math.exp(-0.5*x*x)*delta;
                treeMapCash.put(0.5+LaplaceFunction, x);
                treeMapCash.put(0.5-LaplaceFunction, - x);
            }
        }
        return treeMapCash.higherEntry(cumulativeProbabilityValue).getValue();
    }

    public static double getCumulativeProbabilityValue (double number, double numberMax) {
        double step = 1.0 / numberMax;
        return step * number - step / 2.0;  // 100.0*99.0;
    }

}
