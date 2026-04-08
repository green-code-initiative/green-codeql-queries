// Stubs
function success(pos) { console.log(pos); }
function error(err) { console.warn(err); }

// ============================================================
// 🚫 Noncompliant - getCurrentPosition with enableHighAccuracy: true
// ============================================================

navigator.geolocation.getCurrentPosition(success, error, {
    enableHighAccuracy: true, // $ Alert
    timeout: 5000
});

// ============================================================
// 🚫 Noncompliant - watchPosition with enableHighAccuracy: true
// ============================================================

navigator.geolocation.watchPosition(success, error, {
    enableHighAccuracy: true // $ Alert
});

// ============================================================
// ✅ Compliant - getCurrentPosition with enableHighAccuracy: false
// ============================================================

navigator.geolocation.getCurrentPosition(success, error, {
    enableHighAccuracy: false, // OK
    timeout: 10000
});

// ============================================================
// ✅ Compliant - getCurrentPosition without enableHighAccuracy (defaults to false)
// ============================================================

navigator.geolocation.getCurrentPosition(success, error, {
    timeout: 5000 // OK
});

// ============================================================
// ✅ Compliant - getCurrentPosition without options
// ============================================================

navigator.geolocation.getCurrentPosition(success); // OK