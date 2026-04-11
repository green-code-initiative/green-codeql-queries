// Stubs
class SensorEventListener {}
class Sensor {}

class SensorManager {
    // 3-arg: no batching
    public boolean registerListener(SensorEventListener listener, Sensor sensor, int samplingPeriodUs) {
        return false;
    }

    // 4-arg: with batching via maxReportLatencyUs
    public boolean registerListener(SensorEventListener listener, Sensor sensor, int samplingPeriodUs, int maxReportLatencyUs) {
        return false;
    }
}

class MyListener extends SensorEventListener {}

// NON-COMPLIANT — 3-arg registerListener, no batching
class BadSensorUsage {
    void start(SensorManager sm, Sensor s) {
        MyListener listener = new MyListener();
        sm.registerListener(listener, s, 200000);
    }
}

// COMPLIANT — 4-arg registerListener with maxReportLatencyUs
class GoodSensorUsage {
    void start(SensorManager sm, Sensor s) {
        MyListener listener = new MyListener();
        sm.registerListener(listener, s, 200000, 1000000);
    }
}

public class  AvoidRegisterListenerWithoutMrlu {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
