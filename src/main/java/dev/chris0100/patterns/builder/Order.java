package dev.chris0100.patterns.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Immutable order built step by step; invalid orders cannot exist. */
public final class Order {

    private final String customerId;
    private final List<String> items;
    private final String couponCode;   // optional
    private final String notes;        // optional
    private final boolean giftWrap;

    private Order(Builder b) {
        this.customerId = b.customerId;
        this.items = List.copyOf(b.items);
        this.couponCode = b.couponCode;
        this.notes = b.notes;
        this.giftWrap = b.giftWrap;
    }

    public static Builder builder(String customerId) {
        return new Builder(customerId);
    }

    public String customerId() { return customerId; }
    public List<String> items() { return items; }
    public String couponCode() { return couponCode; }
    public String notes() { return notes; }
    public boolean giftWrap() { return giftWrap; }

    public static final class Builder {
        private final String customerId;
        private final List<String> items = new ArrayList<>();
        private String couponCode;
        private String notes;
        private boolean giftWrap;

        private Builder(String customerId) {
            this.customerId = Objects.requireNonNull(customerId, "customerId");
        }

        public Builder item(String sku) { items.add(sku); return this; }
        public Builder coupon(String code) { this.couponCode = code; return this; }
        public Builder notes(String text) { this.notes = text; return this; }
        public Builder giftWrap(boolean value) { this.giftWrap = value; return this; }

        public Order build() {
            if (items.isEmpty()) {
                throw new IllegalStateException("an order needs at least one item");
            }
            return new Order(this);
        }
    }
}
