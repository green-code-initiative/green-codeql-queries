/**
 * @name Vibration-free
 * @description Using the vibrator service consumes battery power through a dedicated
 *              hardware component (motor or actuator). Its usage must be discouraged
 *              since its added value is not clear. Avoid getSystemService() calls with
 *              VIBRATOR_SERVICE (API 26) or VIBRATOR_MANAGER_SERVICE (API 31).
 * @kind problem
 * @problem.severity warning
 * @id java/android/avoid-vibration
 * @link https://green-code-initiative.org/rules#id:GCI528
 * @tags android
 * @tags java
 */

import java

from MethodCall mc
where
  mc.getMethod().getName() = "getSystemService" and
  exists(FieldRead fr |
    fr = mc.getArgument(0) and
    (
      fr.getField().getName() = "VIBRATOR_SERVICE" or
      fr.getField().getName() = "VIBRATOR_MANAGER_SERVICE"
    )
  )
select mc,
  "Avoid using the vibrator service ('" + mc.getArgument(0).(FieldRead).getField().getName() +
  "'). The vibration hardware component consumes battery power and its added value is not clear."