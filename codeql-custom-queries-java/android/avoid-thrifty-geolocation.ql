/**
 * @name Avoid Thrifty Geolocation
 * @description Location awareness should minimize battery consumption.
 *              Criteria#setPowerRequirement() should use POWER_LOW instead of POWER_HIGH or POWER_MEDIUM.
 *              LocationManager#requestLocationUpdates() must be called with minTime > 0 and minDistance > 0
 *              to avoid continuous location polling which drains the battery.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-thrifty-geolocation
 * @link https://green-code-initiative.org/rules#id:GCI523
 * @link https://green-code-initiative.org/rules#id:GCI524
 * @link https://green-code-initiative.org/rules#id:GCI527
 * @tags android
 * @tags java
 */

import java

from MethodCall mc
where
  (
    // Cas 1 : setPowerRequirement appelé avec POWER_HIGH ou POWER_MEDIUM
    mc.getMethod().getName() = "setPowerRequirement" and
    exists(FieldRead fr |
      fr = mc.getArgument(0) and
      (
        fr.getField().getName() = "POWER_HIGH" or
        fr.getField().getName() = "POWER_MEDIUM"
      )
    )
  )
  or
  (
    // Cas 2 : requestLocationUpdates appelé avec minTime = 0
    mc.getMethod().getName() = "requestLocationUpdates" and
    mc.getArgument(0).(LongLiteral).getValue() = "0"
  )
  or
  (
    // Cas 3 : requestLocationUpdates appelé avec minDistance = 0
    mc.getMethod().getName() = "requestLocationUpdates" and
    mc.getArgument(1).(FloatLiteral).getValue() = "0.0"
  )
select mc,
  "Thrifty geolocation violation: ensure setPowerRequirement() uses POWER_LOW, and requestLocationUpdates() is called with minTime > 0 and minDistance > 0 to preserve battery life."