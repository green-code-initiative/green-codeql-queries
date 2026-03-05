/**
 * @name Avoid SensorManager listener leak
 * @description Calling SensorManager.registerListener() without a matching unregisterListener() causes the sensor to keep processing data during idle states, draining battery within hours.
 * @kind problem
 * @problem.severity warning
 * @precision medium
 * @id java/android/avoid-sensor-manager-leak
 * @link https://green-code-initiative.org/rules#id:GCI514
 * @tags android
 */

import java

from MethodCall register
where
  register.getMethod().getName() = "registerListener" and
  register.getQualifier().(VarAccess).getType().(RefType).getName() = "SensorManager" and
  not exists(MethodCall unregister |
    unregister.getMethod().getName() = "unregisterListener" and
    unregister.getEnclosingCallable() = register.getEnclosingCallable()
  )
select register,
  "Call unregisterListener() to match this registerListener() on 'SensorManager' to avoid unnecessary battery drain."