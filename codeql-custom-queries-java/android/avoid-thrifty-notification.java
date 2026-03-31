// Stubs for Android classes used by the test

class ThriftyNotificationCompat {
    static class Builder {
        Builder() {}
        Builder setVibrate(long[] pattern) { return this; }
        Builder setSound(Object sound) { return this; }
        Object build() { return null; }
    }
}

class ThriftyNotificationChannel {
    ThriftyNotificationChannel(String id, String name, int importance) {}
    void setVibrationPattern(long[] pattern) {}
}

// ============================================================
// 🚫 Noncompliant - setVibrate on NotificationCompat.Builder
// ============================================================
class ThriftyNotificationNoncompliantSetVibrate {

    void buildNoisyNotification() {
        new ThriftyNotificationCompat.Builder().setVibrate(new long[] {0, 100, 200}); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - setSound on NotificationCompat.Builder
// ============================================================
class ThriftyNotificationNoncompliantSetSound {

    void buildLoudNotification() {
        new ThriftyNotificationCompat.Builder().setSound(null); // $ Alert
    }
}

// ============================================================
// ✅ Compliant - default NotificationCompat.Builder without extra sound/vibration
// ============================================================
class ThriftyNotificationCompliantDefaultNotification {

    void buildSilentNotification() {
        new ThriftyNotificationCompat.Builder().build(); // OK
    }
}

// ============================================================
// ✅ Compliant - use NotificationChannel#setVibrationPattern() on API 31+
 // ============================================================
class ThriftyNotificationCompliantChannelVibration {

    void buildChannelNotification() {
        ThriftyNotificationChannel channel = new ThriftyNotificationChannel("id", "name", 0);
        channel.setVibrationPattern(new long[] {0, 100, 200}); // OK
        new ThriftyNotificationCompat.Builder().build(); // OK
    }
}