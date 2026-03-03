// Stubs
class LocationManager
interface LocationListener
class LocationRequest
class Criteria
class GpsStatus
class FusedLocationProviderClient
class LocationCallback

// 🚫 Noncompliant - using Android SDK location classes
class NoncompliantLocation {

    val manager: LocationManager? = null    // $ Alert
    val listener: LocationListener? = null  // $ Alert
    val request: LocationRequest? = null    // $ Alert
    val criteria: Criteria? = null          // $ Alert
    val gpsStatus: GpsStatus? = null        // $ Alert

    fun getLocation() {
        val mgr: LocationManager? = null    // $ Alert
        val crit: Criteria? = null          // $ Alert
    }
}

// ✅ Compliant - using fused location provider
class CompliantLocation {

    val fusedClient: FusedLocationProviderClient? = null  // OK
    val callback: LocationCallback? = null                // OK
}