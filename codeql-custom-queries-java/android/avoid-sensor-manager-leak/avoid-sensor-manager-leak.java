class SensorManager {
    void registerListener(Object listener, Object sensor, int delay) {
    }

    void unregisterListener(Object listener) {
    }
}

class Sensor {
    static final int TYPE_ACCELEROMETER = 1;
    static final int TYPE_GYROSCOPE = 4;
    static final int TYPE_LIGHT = 5;
}

// 🚫 Noncompliant - registerListener without unregisterListener
class NoncompliantSensorLeak {

    void startAccelerometer() {
        SensorManager sManager = new SensorManager();
        sManager.registerListener(null, new Sensor(), 1); // $ Alert
        // missing sManager.unregisterListener()
    }

    void startGyroscope() {
        SensorManager sManager = new SensorManager();
        sManager.registerListener(null, new Sensor(), 1); // $ Alert
        // missing sManager.unregisterListener()
    }

    void startMultipleSensors() {
        SensorManager sManager = new SensorManager();
        sManager.registerListener(null, new Sensor(), 1); // $ Alert
        sManager.registerListener(null, new Sensor(), 1); // $ Alert
        // missing sManager.unregisterListener()
    }
}

// ✅ Compliant - registerListener with matching unregisterListener
class CompliantSensorUsage {

    void startAccelerometer() {
        SensorManager sManager = new SensorManager();
        sManager.registerListener(null, new Sensor(), 1); // OK
        sManager.unregisterListener(null);
    }

    void startMultipleSensors() {
        SensorManager sManager = new SensorManager();
        sManager.registerListener(null, new Sensor(), 1); // OK
        sManager.registerListener(null, new Sensor(), 1); // OK
        sManager.unregisterListener(null);
    }
}
