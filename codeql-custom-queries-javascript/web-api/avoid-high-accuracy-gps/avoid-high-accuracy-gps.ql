/**
 * @name Avoid high accuracy GPS geolocation
 * @description Using 'enableHighAccuracy: true' activates hardware GPS chips, significantly increasing power consumption and battery drain on mobile devices.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id js/web-api/avoid-high-accuracy-gps
 * @tags web-api
 *       client
 */

import javascript

from MethodCallExpr call, ObjectExpr options, Property prop
where
  (
    call.getMethodName() = "getCurrentPosition" or
    call.getMethodName() = "watchPosition"
  ) and
  call.getReceiver().(PropAccess).getPropertyName() = "geolocation" and
  options = call.getArgument(2).getUnderlyingValue() and
  prop = options.getPropertyByName("enableHighAccuracy") and
  prop.getInit().(BooleanLiteral).getValue() = "true"
select prop, "Avoid 'enableHighAccuracy: true' in " + call.getMethodName() + "() to reduce battery drain and energy consumption. Use 'enableHighAccuracy: false' (default) unless meter-level precision is strictly required for the core user experience."