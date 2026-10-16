package dev.chris0100.patterns.factory;

/** Creates the right channel; callers only know the Notifier interface. */
public final class NotifierFactory {

    public enum Channel { EMAIL, SMS, PUSH }

    private NotifierFactory() {
    }

    public static Notifier create(Channel channel) {
        return switch (channel) {
            case EMAIL -> (to, msg) -> "email to " + to + ": " + msg;
            case SMS -> (to, msg) -> "sms to " + to + ": " + truncate(msg, 160);
            case PUSH -> (to, msg) -> "push to device " + to + ": " + truncate(msg, 100);
        };
    }

    private static String truncate(String text, int max) {
        return text.length() <= max ? text : text.substring(0, max);
    }
}
