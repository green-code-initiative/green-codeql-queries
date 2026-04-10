/**
 * @name Missing battery and charging state monitoring
 * @description It's always good that an app has different behavior when device is connected/disconnected to a power station, or has different battery levels. One can monitor the changes in charging state with a broadcast receiver registered on the actions ACTION_POWER_CONNECTED and ACTION_POWER_DISCONNECTED, or monitor significant changes in battery level with a broadcast receiver registered on the actions BATTERY_LOW and BATTERY_OKAY.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/missing-charging-state-awareness
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags android
 * @tags java
 */

import java

class AndroidComponent extends Class {
  AndroidComponent() {
    exists(Class ancestor | ancestor = this.getASupertype*() |
      ancestor.hasName("Activity") or
      ancestor.hasName("Service") or
      ancestor.hasName("Application")
    ) and
    not this.hasName("Activity") and
    not this.hasName("Service") and
    not this.hasName("Application") and
    not this.hasName("Context")
  }
}

class BatteryActionFieldAccess extends FieldAccess {
  BatteryActionFieldAccess() {
    this.getField().hasName("ACTION_POWER_CONNECTED") or
    this.getField().hasName("ACTION_POWER_DISCONNECTED") or
    this.getField().hasName("ACTION_BATTERY_LOW") or
    this.getField().hasName("ACTION_BATTERY_OKAY")
  }
}

class BatteryActionStringLiteral extends StringLiteral {
  BatteryActionStringLiteral() {
    this.getValue() = "android.intent.action.ACTION_POWER_CONNECTED" or
    this.getValue() = "android.intent.action.ACTION_POWER_DISCONNECTED" or
    this.getValue() = "android.intent.action.BATTERY_LOW" or
    this.getValue() = "android.intent.action.BATTERY_OKAY"
  }
}

class BatteryManagerApiCall extends MethodCall {
  BatteryManagerApiCall() {
    this.getMethod().getDeclaringType().hasName("BatteryManager") and
    (
      this.getMethod().hasName("isCharging") or
      this.getMethod().hasName("getIntProperty")
    )
  }
}

from AndroidComponent component
where
  not exists(BatteryActionFieldAccess ref |
    ref.getEnclosingCallable().getDeclaringType() = component
  ) and
  not exists(BatteryActionStringLiteral lit |
    lit.getEnclosingCallable().getDeclaringType() = component
  ) and
  not exists(BatteryManagerApiCall call |
    call.getEnclosingCallable().getDeclaringType() = component
  )
select component,
  "Component " + component.getName() +
    " does not monitor charging state or battery level. Consider registering a receiver for " +
    "ACTION_POWER_CONNECTED/ACTION_POWER_DISCONNECTED or BATTERY_LOW/BATTERY_OKAY to adapt " +
    "behavior based on power availability."
