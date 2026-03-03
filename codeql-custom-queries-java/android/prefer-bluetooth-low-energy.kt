// Stubs - classic Bluetooth
class BluetoothAdapter
class BluetoothDevice
class BluetoothSocket
class BluetoothServerSocket
class BluetoothProfile

// Stubs - Bluetooth Low Energy
class BluetoothLeScanner
class BluetoothLeAdvertiser
class ScanCallback
class ScanResult
class AdvertiseCallback

// 🚫 Noncompliant - using classic Bluetooth only
class NoncompliantBluetooth {

    val adapter: BluetoothAdapter? = null      // $ Alert
    val device: BluetoothDevice? = null        // $ Alert
    val socket: BluetoothSocket? = null        // $ Alert

    fun connect() {
        val serverSocket: BluetoothServerSocket? = null  // $ Alert
        val profile: BluetoothProfile? = null            // $ Alert
    }
}

// ✅ Compliant - using Bluetooth Low Energy
class CompliantBluetooth {

    val scanner: BluetoothLeScanner? = null       // OK
    val advertiser: BluetoothLeAdvertiser? = null // OK
    val callback: ScanCallback? = null            // OK
    val result: ScanResult? = null                // OK
}