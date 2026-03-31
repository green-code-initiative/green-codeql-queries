// Stub classes used to avoid redeclaration conflicts

class ThriftySensor {
    public static final int TYPE_ROTATION_VECTOR = 1;
    public static final int TYPE_GYROSCOPE = 2;
    public static final int TYPE_GYROSCOPE_UNCALIBRATED = 3;
    public static final int TYPE_GEOMAGNETIC_ROTATION_VECTOR = 4;
}

class ThriftySensorManager {
    public ThriftySensor getDefaultSensor(int type) {
        return new ThriftySensor();
    }
}

// ============================================================
// 🚫 Noncompliant - getDefaultSensor with TYPE_ROTATION_VECTOR
// ============================================================
class ThriftySensorNoncompliantRotationVector {

    void getRotationVector() {
        ThriftySensorManager sm = new ThriftySensorManager();
        sm.getDefaultSensor(ThriftySensor.TYPE_ROTATION_VECTOR); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - getDefaultSensor with TYPE_GYROSCOPE
// ============================================================
class ThriftySensorNoncompliantGyroscope {

    void getGyroscope() {
        ThriftySensorManager sm = new ThriftySensorManager();
        sm.getDefaultSensor(ThriftySensor.TYPE_GYROSCOPE); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - getDefaultSensor with TYPE_GYROSCOPE_UNCALIBRATED
// ============================================================
class ThriftySensorNoncompliantGyroscopeUncalibrated {

    void getGyroscopeUncalibrated() {
        ThriftySensorManager sm = new ThriftySensorManager();
        sm.getDefaultSensor(ThriftySensor.TYPE_GYROSCOPE_UNCALIBRATED); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - all three violations in one method
// ============================================================
class ThriftySensorNoncompliantAllThreeViolations {

    void getAllBadSensors() {
        ThriftySensorManager sm = new ThriftySensorManager();
        sm.getDefaultSensor(ThriftySensor.TYPE_ROTATION_VECTOR); // $ Alert
        sm.getDefaultSensor(ThriftySensor.TYPE_GYROSCOPE); // $ Alert
        sm.getDefaultSensor(ThriftySensor.TYPE_GYROSCOPE_UNCALIBRATED); // $ Alert
    }
}

// ============================================================
// ✅ Compliant - getDefaultSensor with TYPE_GEOMAGNETIC_ROTATION_VECTOR
// ============================================================
class ThriftySensorCompliantGeomagneticRotationVector {

    void getGeomagneticRotationVector() {
        ThriftySensorManager sm = new ThriftySensorManager();
        sm.getDefaultSensor(ThriftySensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR); // OK
    }
}