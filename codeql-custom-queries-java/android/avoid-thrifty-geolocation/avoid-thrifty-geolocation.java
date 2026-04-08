// LocationManager, Criteria already declared in avoid-android-sdk-location.java
// We need to extend stubs with missing methods — use proxy classes instead

class PendingIntent {}

class ThriftyLocationManager {
    void requestLocationUpdates(long minTime, float minDistance, ThriftyCriteria criteria, PendingIntent intent) {}
    String getBestProvider(ThriftyCriteria criteria, boolean enabledOnly) { return null; }
}

class ThriftyCriteria {
    static final int POWER_LOW    = 1;
    static final int POWER_MEDIUM = 2;
    static final int POWER_HIGH   = 3;

    void setPowerRequirement(int level) {}
}

// ============================================================
// 🚫 Noncompliant - POWER_HIGH used in setPowerRequirement
// ============================================================
class NoncompliantPowerHigh {

    void fetchLocationPowerHigh() {
        ThriftyCriteria criteria = new ThriftyCriteria();
        criteria.setPowerRequirement(ThriftyCriteria.POWER_HIGH); // $ Alert
        ThriftyLocationManager manager = new ThriftyLocationManager();
        manager.getBestProvider(criteria, true);
    }
}

// ============================================================
// 🚫 Noncompliant - POWER_MEDIUM used in setPowerRequirement
// ============================================================
class NoncompliantPowerMedium {

    void fetchLocationPowerMedium() {
        ThriftyCriteria criteria = new ThriftyCriteria();
        criteria.setPowerRequirement(ThriftyCriteria.POWER_MEDIUM); // $ Alert
        ThriftyLocationManager manager = new ThriftyLocationManager();
        manager.getBestProvider(criteria, true);
    }
}

// ============================================================
// 🚫 Noncompliant - minTime = 0 in requestLocationUpdates
// ============================================================
class NoncompliantMinTimeZero {

    void fetchLocationMinTimeZero() {
        ThriftyCriteria criteria = new ThriftyCriteria();
        criteria.setPowerRequirement(ThriftyCriteria.POWER_LOW);
        ThriftyLocationManager manager = new ThriftyLocationManager();
        manager.requestLocationUpdates(0L, 10.0f, criteria, new PendingIntent()); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - minDistance = 0 in requestLocationUpdates
// ============================================================
class NoncompliantMinDistanceZero {

    void fetchLocationMinDistanceZero() {
        ThriftyCriteria criteria = new ThriftyCriteria();
        criteria.setPowerRequirement(ThriftyCriteria.POWER_LOW);
        ThriftyLocationManager manager = new ThriftyLocationManager();
        manager.requestLocationUpdates(5000L, 0.0f, criteria, new PendingIntent()); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - both minTime and minDistance = 0
// ============================================================
class NoncompliantBothZero {

    void fetchLocationBothZero() {
        ThriftyCriteria criteria = new ThriftyCriteria();
        criteria.setPowerRequirement(ThriftyCriteria.POWER_LOW);
        ThriftyLocationManager manager = new ThriftyLocationManager();
        manager.requestLocationUpdates(0L, 0.0f, criteria, new PendingIntent()); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - POWER_HIGH + minTime = 0
// ============================================================
class NoncompliantPowerHighAndMinTimeZero {

    void fetchLocationAllBad() {
        ThriftyCriteria criteria = new ThriftyCriteria();
        criteria.setPowerRequirement(ThriftyCriteria.POWER_HIGH); // $ Alert
        ThriftyLocationManager manager = new ThriftyLocationManager();
        manager.requestLocationUpdates(0L, 0.0f, criteria, new PendingIntent()); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - POWER_MEDIUM + minDistance = 0
// ============================================================
class NoncompliantPowerMediumAndMinDistanceZero {

    void fetchLocationMediumAndBadDistance() {
        ThriftyCriteria criteria = new ThriftyCriteria();
        criteria.setPowerRequirement(ThriftyCriteria.POWER_MEDIUM); // $ Alert
        ThriftyLocationManager manager = new ThriftyLocationManager();
        manager.requestLocationUpdates(5000L, 0.0f, criteria, new PendingIntent()); // $ Alert
    }
}

// ============================================================
// ✅ Compliant - POWER_LOW + minTime > 0 + minDistance > 0
// ============================================================
class CompliantThriftyGeolocation {

    void fetchLocationOptimal() {
        ThriftyCriteria criteria = new ThriftyCriteria();
        criteria.setPowerRequirement(ThriftyCriteria.POWER_LOW); // OK
        ThriftyLocationManager manager = new ThriftyLocationManager();
        manager.requestLocationUpdates(5000L, 10.0f, criteria, new PendingIntent()); // OK
    }
}

// ============================================================
// ✅ Compliant - POWER_LOW + large minTime + large minDistance
// ============================================================
class CompliantThriftyGeolocationLargeValues {

    void fetchLocationConservative() {
        ThriftyCriteria criteria = new ThriftyCriteria();
        criteria.setPowerRequirement(ThriftyCriteria.POWER_LOW); // OK
        ThriftyLocationManager manager = new ThriftyLocationManager();
        manager.requestLocationUpdates(60000L, 100.0f, criteria, new PendingIntent()); // OK
    }
}

// ============================================================
// ✅ Compliant - getBestProvider only, no requestLocationUpdates
// ============================================================
class CompliantBestProviderOnly {

    void fetchBestProviderOnly() {
        ThriftyCriteria criteria = new ThriftyCriteria();
        criteria.setPowerRequirement(ThriftyCriteria.POWER_LOW); // OK
        ThriftyLocationManager manager = new ThriftyLocationManager();
        manager.getBestProvider(criteria, true); // OK
    }
}