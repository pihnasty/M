package math;

import java.util.function.Supplier;

public class MathP {

    static public Counter getCounter(int i) {
        return new Counter (i);
    }

    static public Counter getCounter(int initialCount, int i) {
        return new Counter (initialCount,i);
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

}
