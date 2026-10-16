package dev.chris0100.patterns;

import dev.chris0100.patterns.adapter.LegacyPayAdapter;
import dev.chris0100.patterns.adapter.LegacyPaySdk;
import dev.chris0100.patterns.adapter.PaymentGateway;
import dev.chris0100.patterns.builder.Order;
import dev.chris0100.patterns.factory.NotifierFactory;
import dev.chris0100.patterns.observer.EventBus;
import dev.chris0100.patterns.observer.OrderPlaced;
import dev.chris0100.patterns.strategy.Carriers;
import dev.chris0100.patterns.strategy.ShippingCalculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatternsTest {

    @Test
    void strategyPicksTheCarrierWithoutIfElse() {
        ShippingCalculator calc = new ShippingCalculator(List.of(
                new Carriers.Standard(), new Carriers.Express(), new Carriers.FreeOverThreshold(2)));

        assertEquals(new BigDecimal("8.60"), calc.quote("standard", 3));
        assertEquals(new BigDecimal("22.50"), calc.quote("express", 3));
        assertEquals(new BigDecimal("0.00"), calc.quote("promo", 1.5));
        assertThrows(IllegalArgumentException.class, () -> calc.quote("drone", 1));
    }

    @Test
    void builderCreatesValidImmutableOrders() {
        Order order = Order.builder("c-1").item("sku-1").item("sku-2").coupon("WELCOME").giftWrap(true).build();

        assertEquals(2, order.items().size());
        assertEquals("WELCOME", order.couponCode());
        assertThrows(UnsupportedOperationException.class, () -> order.items().add("hack"));
        assertThrows(IllegalStateException.class, () -> Order.builder("c-1").build());
    }

    @Test
    void factoryReturnsTheRightChannel() {
        String sms = NotifierFactory.create(NotifierFactory.Channel.SMS).send("+57300", "x".repeat(300));
        assertTrue(sms.startsWith("sms to +57300"));
        assertEquals("sms to +57300: ".length() + 160, sms.length());
    }

    @Test
    void adapterTranslatesTheLegacySdk() {
        PaymentGateway gateway = new LegacyPayAdapter(new LegacyPaySdk());

        PaymentGateway.PaymentResult ok = gateway.charge("acc-9", new BigDecimal("12.34"));
        assertTrue(ok.approved());
        assertEquals("TX-acc-9-1234", ok.reference());
        assertFalse(gateway.charge("acc-9", new BigDecimal("0.00")).approved());
    }

    @Test
    void observerNotifiesAllListenersEvenIfOneFails() {
        EventBus<OrderPlaced> bus = new EventBus<>();
        List<String> log = new ArrayList<>();
        bus.subscribe(e -> log.add("email:" + e.orderId()));
        bus.subscribe(e -> { throw new IllegalStateException("analytics down"); });
        bus.subscribe(e -> log.add("stock:" + e.orderId()));

        int failures = bus.publish(new OrderPlaced("o-1", "c-1"));

        assertEquals(1, failures);
        assertEquals(List.of("email:o-1", "stock:o-1"), log);
    }
}
