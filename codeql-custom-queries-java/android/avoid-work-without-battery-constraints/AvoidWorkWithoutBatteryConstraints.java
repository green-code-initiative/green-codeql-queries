// Stubs
class Constraints {
    static class Builder {
        public Builder setRequiresBatteryNotLow(boolean required) { return this; }
        public Builder setRequiresCharging(boolean required) { return this; }
        public Builder setRequiresDeviceIdle(boolean required) { return this; }
        public Constraints build() { return null; }
    }
}

class Data {}

abstract class WorkRequest {
    abstract static class Builder {
        public Builder setConstraints(Constraints constraints) { return this; }
        public Builder setInputData(Data data) { return this; }
        public abstract WorkRequest build();
    }
}

class OneTimeWorkRequest extends WorkRequest {
    static class Builder extends WorkRequest.Builder {
        public Builder(Class<?> workerClass) {}
        @Override
        public Builder setConstraints(Constraints constraints) { return this; }
        @Override
        public Builder setInputData(Data data) { return this; }
        @Override
        public OneTimeWorkRequest build() { return null; }
    }
}

class PeriodicWorkRequest extends WorkRequest {
    static class Builder extends WorkRequest.Builder {
        public Builder(Class<?> workerClass, long interval, Object unit) {}
        @Override
        public Builder setConstraints(Constraints constraints) { return this; }
        @Override
        public Builder setInputData(Data data) { return this; }
        @Override
        public PeriodicWorkRequest build() { return null; }
    }
}

// NON-COMPLIANT — WorkRequest built with no constraints at all
class BadWorkerScheduler {
    void schedule() {
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(Object.class)
                .build();
    }
}

// NON-COMPLIANT — has constraints but no battery constraint
class AlsoBadWorkerScheduler {
    void schedule() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(true)
                .build();
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(Object.class)
                .setConstraints(constraints)
                .build();
    }
}

// NON-COMPLIANT — setRequiresBatteryNotLow is false
class FalseBatteryScheduler {
    void schedule() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(false)
                .build();
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(Object.class)
                .setConstraints(constraints)
                .build();
    }
}

// COMPLIANT — uses setRequiresBatteryNotLow(true)
class GoodWorkerScheduler {
    void schedule() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(Object.class)
                .setConstraints(constraints)
                .build();
    }
}

// COMPLIANT — uses setRequiresCharging(true)
class ChargingWorkerScheduler {
    void schedule() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(Object.class, 15L, null)
                .setConstraints(constraints)
                .build();
    }
}

public class AvoidWorkWithoutBatteryConstraints {
    public static void main(String[] args) {
        System.out.println("Compilation OK");
    }
}
