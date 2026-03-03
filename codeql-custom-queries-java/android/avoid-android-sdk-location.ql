/**
 * @name Avoid Android SDK location, prefer fused location provider
 * @description Using android.location directly bypasses the fused location provider which combines GPS, Wi-Fi, and cell signals to maximize battery life. Use com.google.android.gms.location instead.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-android-sdk-location
 * @link https://green-code-initiative.org/rules#id:GCI517
 * @tags andoid
 * @tags java
 */

import java

import java

from RefType t, Variable v
where
  v.getType() = t and
  (
    t.getName() = "LocationManager" or
    t.getName() = "LocationListener" or
    t.getName() = "LocationRequest" or
    t.getName() = "Criteria" or
    t.getName() = "GpsStatus"
  ) and
  not t.getName() = "FusedLocationProviderClient" and
  not t.getName() = "LocationCallback"
select v,
  "Avoid using '" + t.getName() + "' from the Android SDK. Use the fused location provider from 'com.google.android.gms.location' instead to reduce battery consumption."