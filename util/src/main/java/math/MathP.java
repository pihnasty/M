package math;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class MathP {

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


}
