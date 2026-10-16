package dev.chris0100.patterns.strategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Context: picks the strategy by name. Adding a carrier never changes this class. */
public final class ShippingCalculator {

    private final Map<String, ShippingStrategy> strategies;

    public ShippingCalculator(List<ShippingStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toUnmodifiableMap(ShippingStrategy::carrier, Function.identity()));
    }

    public BigDecimal quote(String carrier, double weightKg) {
        ShippingStrategy strategy = strategies.get(carrier);
        if (strategy == null) {
            throw new IllegalArgumentException("unknown carrier: " + carrier);
        }
        return strategy.cost(weightKg);
    }
}
