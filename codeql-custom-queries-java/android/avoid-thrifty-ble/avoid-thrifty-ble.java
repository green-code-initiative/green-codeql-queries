// BluetoothAdapter, BluetoothDevice already declared in prefer-bluetooth-low-energy.java
// Proxy classes used to avoid redeclaration conflicts

class BluetoothLeScanner {
    void startScan(Object filters, ThriftyScanSettings settings, Object callback) {}
}

class ThriftyScanSettings {
    static final int SCAN_MODE_LOW_POWER    = 0;
    static final int SCAN_MODE_BALANCED     = 1;
    static final int SCAN_MODE_LOW_LATENCY  = 2;
    static final int SCAN_MODE_OPPORTUNISTIC = -1;
}

class ThriftyScanSettingsBuilder {
    ThriftyScanSettingsBuilder setScanMode(int mode) { return this; }
    ThriftyScanSettings build() { return new ThriftyScanSettings(); }
}

class BluetoothGatt {
    static final int CONNECTION_PRIORITY_BALANCED  = 0;
    static final int CONNECTION_PRIORITY_HIGH       = 1;
    static final int CONNECTION_PRIORITY_LOW_POWER  = 2;

    boolean requestConnectionPriority(int connectionPriority) { return true; }
}

class AdvertiseSettingsBuilder {
    static final int ADVERTISE_MODE_LOW_POWER   = 0;
    static final int ADVERTISE_MODE_BALANCED    = 1;
    static final int ADVERTISE_MODE_LOW_LATENCY = 2;

    AdvertiseSettingsBuilder setAdvertiseMode(int advertiseMode) { return this; }
    Object build() { return null; }
}

// ============================================================
// 🚫 Noncompliant - setScanMode with SCAN_MODE_LOW_LATENCY
// ============================================================
class NoncompliantScanModeLowLatency {

    void scanWithLowLatency() {
        ThriftyScanSettingsBuilder builder = new ThriftyScanSettingsBuilder();
        builder.setScanMode(ThriftyScanSettings.SCAN_MODE_LOW_LATENCY); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - setScanMode with SCAN_MODE_BALANCED
// ============================================================
class NoncompliantScanModeBalanced {

    void scanWithBalanced() {
        ThriftyScanSettingsBuilder builder = new ThriftyScanSettingsBuilder();
        builder.setScanMode(ThriftyScanSettings.SCAN_MODE_BALANCED); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - setScanMode with SCAN_MODE_OPPORTUNISTIC
// ============================================================
class NoncompliantScanModeOpportunistic {

    void scanWithOpportunistic() {
        ThriftyScanSettingsBuilder builder = new ThriftyScanSettingsBuilder();
        builder.setScanMode(ThriftyScanSettings.SCAN_MODE_OPPORTUNISTIC); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - requestConnectionPriority with CONNECTION_PRIORITY_HIGH
// ============================================================
class NoncompliantConnectionPriorityHigh {

    void connectWithHighPriority() {
        BluetoothGatt gatt = new BluetoothGatt();
        gatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - requestConnectionPriority with CONNECTION_PRIORITY_BALANCED
// ============================================================
class NoncompliantConnectionPriorityBalanced {

    void connectWithBalancedPriority() {
        BluetoothGatt gatt = new BluetoothGatt();
        gatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_BALANCED); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - setAdvertiseMode with ADVERTISE_MODE_LOW_LATENCY
// ============================================================
class NoncompliantAdvertiseModeLowLatency {

    void advertiseWithLowLatency() {
        AdvertiseSettingsBuilder builder = new AdvertiseSettingsBuilder();
        builder.setAdvertiseMode(AdvertiseSettingsBuilder.ADVERTISE_MODE_LOW_LATENCY); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - setAdvertiseMode with ADVERTISE_MODE_BALANCED
// ============================================================
class NoncompliantAdvertiseModeBalanced {

    void advertiseWithBalanced() {
        AdvertiseSettingsBuilder builder = new AdvertiseSettingsBuilder();
        builder.setAdvertiseMode(AdvertiseSettingsBuilder.ADVERTISE_MODE_BALANCED); // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - all three violations in one method
// ============================================================
class NoncompliantAllThreeViolations {

    void doEverythingWrong() {
        ThriftyScanSettingsBuilder scanBuilder = new ThriftyScanSettingsBuilder();
        scanBuilder.setScanMode(ThriftyScanSettings.SCAN_MODE_LOW_LATENCY); // $ Alert

        BluetoothGatt gatt = new BluetoothGatt();
        gatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH); // $ Alert

        AdvertiseSettingsBuilder advBuilder = new AdvertiseSettingsBuilder();
        advBuilder.setAdvertiseMode(AdvertiseSettingsBuilder.ADVERTISE_MODE_LOW_LATENCY); // $ Alert
    }
}

// ============================================================
// ✅ Compliant - setScanMode with SCAN_MODE_LOW_POWER
// ============================================================
class CompliantScanModeLowPower {

    void scanWithLowPower() {
        ThriftyScanSettingsBuilder builder = new ThriftyScanSettingsBuilder();
        builder.setScanMode(ThriftyScanSettings.SCAN_MODE_LOW_POWER); // OK
    }
}

// ============================================================
// ✅ Compliant - requestConnectionPriority with CONNECTION_PRIORITY_LOW_POWER
// ============================================================
class CompliantConnectionPriorityLowPower {

    void connectWithLowPower() {
        BluetoothGatt gatt = new BluetoothGatt();
        gatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_LOW_POWER); // OK
    }
}

// ============================================================
// ✅ Compliant - setAdvertiseMode with ADVERTISE_MODE_LOW_POWER
// ============================================================
class CompliantAdvertiseModeLowPower {

    void advertiseWithLowPower() {
        AdvertiseSettingsBuilder builder = new AdvertiseSettingsBuilder();
        builder.setAdvertiseMode(AdvertiseSettingsBuilder.ADVERTISE_MODE_LOW_POWER); // OK
    }
}

// ============================================================
// ✅ Compliant - all three correct in one method
// ============================================================
class CompliantAllThreeCorrect {

    void doEverythingRight() {
        ThriftyScanSettingsBuilder scanBuilder = new ThriftyScanSettingsBuilder();
        scanBuilder.setScanMode(ThriftyScanSettings.SCAN_MODE_LOW_POWER); // OK

        BluetoothGatt gatt = new BluetoothGatt();
        gatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_LOW_POWER); // OK

        AdvertiseSettingsBuilder advBuilder = new AdvertiseSettingsBuilder();
        advBuilder.setAdvertiseMode(AdvertiseSettingsBuilder.ADVERTISE_MODE_LOW_POWER); // OK
    }
}