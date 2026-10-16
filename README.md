# Java Design Patterns in Practice

Design patterns with backend-style examples (orders, payments, notifications) instead of shapes and animals.

| Pattern | Package | Problem it solves here |
|---------|---------|------------------------|
| Strategy | `strategy` | Shipping cost changes by carrier without `if/else` chains |
| Builder | `builder` | Immutable `Order` with many optional fields and validation |
| Factory | `factory` | Pick the notification channel (email, SMS, push) at runtime |
| Adapter | `adapter` | Use a legacy payment SDK behind our own `PaymentGateway` interface |
| Observer | `observer` | Publish `OrderPlaced` to several listeners without coupling them |

## Run

```bash
mvn test
```

## Interview notes

- **Strategy** follows Open/Closed: a new carrier = a new class, no change to the calculator. In Spring, inject all strategies as `Map<String, ShippingStrategy>`.
- **Builder** keeps objects immutable and valid; validation lives in `build()`.
- **Factory** hides the `new` and the decision logic; callers depend on the interface.
- **Adapter** protects the domain from third-party APIs. If the vendor changes, only the adapter changes.
- **Observer** decouples producers from consumers. Spring's `ApplicationEventPublisher` and `@EventListener` are the same idea; across services it becomes SNS/Kafka.
