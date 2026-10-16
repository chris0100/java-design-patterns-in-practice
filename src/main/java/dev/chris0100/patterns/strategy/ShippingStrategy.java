package dev.chris0100.patterns.strategy;

import java.math.BigDecimal;

public interface ShippingStrategy {
    String carrier();
    BigDecimal cost(double weightKg);
}
