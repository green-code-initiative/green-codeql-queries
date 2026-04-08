/**
 * @name Avoid Thrifty Motion Sensor
 * @description When using SensorManager#getDefaultSensor(int type), always prefer
 *              TYPE_GEOMAGNETIC_ROTATION_VECTOR over TYPE_ROTATION_VECTOR.
 *              The geomagnetic rotation vector uses a magnetometer instead of a gyroscope,
 *              consuming less power at the cost of slightly more noise outdoors.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-thrifty-motion-sensor
 * @link https://green-code-initiative.org/rules#id:GCI521
 * @tags android
 * @tags java
 */

import java

from MethodCall mc
where
  mc.getMethod().getName() = "getDefaultSensor" and
  exists(FieldRead fr |
    fr = mc.getArgument(0) and
    (
      fr.getField().getName() = "TYPE_ROTATION_VECTOR" or
      fr.getField().getName() = "TYPE_GYROSCOPE" or
      fr.getField().getName() = "TYPE_GYROSCOPE_UNCALIBRATED"
    )
  )
select mc,
  "Prefer Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR over '" +
  mc.getArgument(0).(FieldRead).getField().getName() +
  "' in getDefaultSensor(). The geomagnetic rotation vector uses less power as it relies on a magnetometer instead of a gyroscope."