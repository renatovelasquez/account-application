package dev.renvl.utils;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicDecimal {
    private final AtomicReference<BigDecimal> atomicDecimal;

    public AtomicDecimal(BigDecimal initialValue) {
        atomicDecimal = new AtomicReference<>(initialValue);
    }

    public BigDecimal get() {
        return atomicDecimal.get();
    }

    public void set(BigDecimal newValue) {
        atomicDecimal.set(newValue);
    }

    public BigDecimal add(BigDecimal delta) {
        BigDecimal oldValue, newValue;
        do {
            oldValue = atomicDecimal.get();
            newValue = oldValue.add(delta);
        } while (!atomicDecimal.compareAndSet(oldValue, newValue));
        return newValue;
    }

    public BigDecimal subtract(BigDecimal delta) {
        BigDecimal oldValue, newValue;
        do {
            oldValue = atomicDecimal.get();
            newValue = oldValue.subtract(delta);
        } while (!atomicDecimal.compareAndSet(oldValue, newValue));
        return newValue;
    }

}
