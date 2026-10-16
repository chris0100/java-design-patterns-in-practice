package dev.chris0100.patterns.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/** Minimal in-process publisher. A failing listener does not stop the others. */
public final class EventBus<E> {

    private final List<Consumer<E>> listeners = new CopyOnWriteArrayList<>();

    public void subscribe(Consumer<E> listener) {
        listeners.add(listener);
    }

    public int publish(E event) {
        int failures = 0;
        for (Consumer<E> listener : listeners) {
            try {
                listener.accept(event);
            } catch (RuntimeException ex) {
                failures++; // in real code: log with correlation id
            }
        }
        return failures;
    }
}
