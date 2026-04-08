class LocationManager {}
interface LocationListener {}
class LocationRequest {}
class Criteria {}
class GpsStatus {}
class FusedLocationProviderClient {}
class LocationCallback {}

// 🚫 Noncompliant - using Android SDK location classes
class NoncompliantLocation {

    LocationManager manager = null;   // $ Alert
    LocationListener listener = null; // $ Alert
    LocationRequest request = null;   // $ Alert
    Criteria criteria = null;         // $ Alert
    GpsStatus gpsStatus = null;       // $ Alert

    void getLocation() {
        LocationManager mgr = null;   // $ Alert
        Criteria crit = null;         // $ Alert
    }
}

// ✅ Compliant - using fused location provider
class CompliantLocation {

    FusedLocationProviderClient fusedClient = null; // OK
    LocationCallback callback = null;               // OK
}