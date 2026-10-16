package dev.chris0100.patterns.adapter;

import java.math.BigDecimal;

/** Our own interface. The domain only knows this. */
public interface PaymentGateway {
    PaymentResult charge(String customerId, BigDecimal amount);

    record PaymentResult(boolean approved, String reference) {
    }
}
