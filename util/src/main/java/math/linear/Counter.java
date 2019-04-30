package math.linear;

import java.util.function.Supplier;

public class Counter implements Supplier<Long> {
    long i = 0;

    @Override
    public Long get() {
        return i;
    }

    public Long increment() {
        i++;
        return i;
    }

    static public Counter getCounter() {
        return new Counter();
    }
}
