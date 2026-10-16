package dev.chris0100.patterns.adapter;

import java.math.BigDecimal;

/** Translates our interface to the legacy SDK. If the vendor changes, only this class changes. */
public final class LegacyPayAdapter implements PaymentGateway {

    private final LegacyPaySdk sdk;

    public LegacyPayAdapter(LegacyPaySdk sdk) {
        this.sdk = sdk;
    }

    @Override
    public PaymentResult charge(String customerId, BigDecimal amount) {
        int cents = amount.movePointRight(2).intValueExact();
        StringBuilder txId = new StringBuilder();
        int code = sdk.doTransaction(customerId, cents, txId);
        return new PaymentResult(code == LegacyPaySdk.OK, code == LegacyPaySdk.OK ? txId.toString() : null);
    }
}
