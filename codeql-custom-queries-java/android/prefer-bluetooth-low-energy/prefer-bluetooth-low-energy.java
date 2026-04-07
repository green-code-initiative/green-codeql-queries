class BluetoothAdapter {
    public static BluetoothAdapter getDefaultAdapter() { return null; }
    public BluetoothDevice getRemoteDevice(String address) { return null; }
    public void startDiscovery() {}
    public void cancelDiscovery() {}
}

class BluetoothDevice {
    public BluetoothSocket createRfcommSocketToServiceRecord(java.util.UUID uuid) { return null; }
}

class BluetoothSocket {
    public void connect() {}
    public void close() {}
}

class BluetoothServerSocket {
    public BluetoothSocket accept() { return null; }
    public void close() {}
}

interface BluetoothProfile {
    int getConnectionState(BluetoothDevice device);
}

class NoncompliantBluetooth {

    // 🚫 Noncompliant - BluetoothAdapter field
    BluetoothAdapter adapter = null; // $ Alert

    // 🚫 Noncompliant - BluetoothDevice field
    BluetoothDevice device = null; // $ Alert

    // 🚫 Noncompliant - BluetoothSocket field
    BluetoothSocket socket = null; // $ Alert

    // 🚫 Noncompliant - BluetoothServerSocket field
    BluetoothServerSocket serverSocket = null; // $ Alert

    // 🚫 Noncompliant - BluetoothProfile field
    BluetoothProfile profile = null; // $ Alert

    void scanDevices() {
        // 🚫 Noncompliant - local BluetoothAdapter
        BluetoothAdapter localAdapter = BluetoothAdapter.getDefaultAdapter(); // $ Alert
        localAdapter.startDiscovery();
    }

    void connectToDevice(String address) {
        // 🚫 Noncompliant - local BluetoothDevice
        BluetoothAdapter a = BluetoothAdapter.getDefaultAdapter(); // $ Alert
        BluetoothDevice remoteDevice = a.getRemoteDevice(address); // $ Alert
    }

    void openSocket() throws Exception {
        // 🚫 Noncompliant - local BluetoothSocket
        BluetoothSocket s = null; // $ Alert
        s.connect();
    }

    void listenForConnections() throws Exception {
        // 🚫 Noncompliant - local BluetoothServerSocket
        BluetoothServerSocket ss = null; // $ Alert
        ss.accept();
    }
}

class CompliantBluetooth {
    // ✅ Compliant - no classic Bluetooth usage
    void doNothing() {}
}