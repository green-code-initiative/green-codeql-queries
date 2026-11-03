class LocationManager {}
interface LocationListener {}
class LocationRequest {}
class Criteria {}
class GpsStatus {}
class FusedLocationProviderClient {}
class LocationCallback {}

// ============================================================
// 🚫 Noncompliant - utilisation des classes Android location legacy
// ============================================================
class NoncompliantLocationFields {

    LocationManager manager   = null; // $ Alert
    LocationListener listener = null; // $ Alert
    LocationRequest request   = null; // $ Alert
    Criteria criteria         = null; // $ Alert
    GpsStatus gpsStatus       = null; // $ Alert
}

// ============================================================
// 🚫 Noncompliant - variables locales legacy
// ============================================================
class NoncompliantLocationLocals {

    void getLocation() {
        LocationManager mgr  = null; // $ Alert
        Criteria crit        = null; // $ Alert
    }
}

// ============================================================
// 🚫 Noncompliant - tous les types legacy dans une seule méthode
// ============================================================
class NoncompliantLocationAllTypes {

    void allLegacyTypes() {
        LocationManager manager   = null; // $ Alert
        LocationListener listener = null; // $ Alert
        LocationRequest request   = null; // $ Alert
        Criteria criteria         = null; // $ Alert
        GpsStatus gpsStatus       = null; // $ Alert
    }
}

// ============================================================
// ✅ Compliant - utilisation du Fused Location Provider
// ============================================================
class CompliantLocation {

    FusedLocationProviderClient fusedClient = null; // OK
    LocationCallback callback               = null; // OK
}