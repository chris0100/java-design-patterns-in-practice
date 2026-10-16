package dev.chris0100.patterns.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Carriers {

    private Carriers() {
    }

    public static final class Standard implements ShippingStrategy {
        public String carrier() { return "standard"; }
        public BigDecimal cost(double kg) { return money(5 + 1.2 * kg); }
    }

    public static final class Express implements ShippingStrategy {
        public String carrier() { return "express"; }
        public BigDecimal cost(double kg) { return money(15 + 2.5 * kg); }
    }

    public static final class FreeOverThreshold implements ShippingStrategy {
        private final double freeUntilKg;
        public FreeOverThreshold(double freeUntilKg) { this.freeUntilKg = freeUntilKg; }
        public String carrier() { return "promo"; }
        public BigDecimal cost(double kg) { return kg <= freeUntilKg ? money(0) : money(3 * (kg - freeUntilKg)); }
    }

    static BigDecimal money(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}
