package dev.renvl.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtomicDecimalTest {

    @Test
    public void testAdd() {
        BigDecimal initialValue = BigDecimal.TEN;
        BigDecimal delta = BigDecimal.ONE;
        AtomicDecimal atomicDecimal = new AtomicDecimal(initialValue);
        BigDecimal result = atomicDecimal.add(delta);
        assertEquals(initialValue.add(delta), result);
    }

    @Test
    public void testSubtract() {
        BigDecimal initialValue = BigDecimal.TEN;
        BigDecimal delta = BigDecimal.ONE;
        AtomicDecimal atomicDecimal = new AtomicDecimal(initialValue);
        BigDecimal result = atomicDecimal.subtract(delta);
        assertEquals(initialValue.subtract(delta), result);
    }
}
