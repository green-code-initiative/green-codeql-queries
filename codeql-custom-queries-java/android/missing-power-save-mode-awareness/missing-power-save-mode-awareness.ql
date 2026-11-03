/**
 * @name Missing power save mode awareness
 * @description Taking into account when the device is entering or exiting the power save mode is higly desirable for the battery life. It implies the existence of a broadcast receiver registered on the action ACTION_POWER_SAVE_MODE_CHANGED, or programmaticaly with a call to PowerManager#isPowerSaveMode().
 * @kind problem
 * @problem.severity recommendation
 * @precision high
 * @id java/android/missing-power-save-mode-awareness
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

class IsPowerSaveModeCall extends MethodCall {
  IsPowerSaveModeCall() {
    this.getMethod().getDeclaringType().hasName("PowerManager") and
    this.getMethod().hasName("isPowerSaveMode")
  }
}

class PowerSaveModeChangedRef extends FieldAccess {
  PowerSaveModeChangedRef() {
    this.getField().hasName("ACTION_POWER_SAVE_MODE_CHANGED")
  }
}

class PowerSaveModeStringLiteral extends StringLiteral {
  PowerSaveModeStringLiteral() {
    this.getValue() = "android.os.action.POWER_SAVE_MODE_CHANGED"
  }
}

from AndroidComponent component
where
  not exists(IsPowerSaveModeCall call |
    call.getEnclosingCallable().getDeclaringType() = component
  ) and
  not exists(PowerSaveModeChangedRef ref |
    ref.getEnclosingCallable().getDeclaringType() = component
  ) and
  not exists(PowerSaveModeStringLiteral lit |
    lit.getEnclosingCallable().getDeclaringType() = component
  )
select component,
  "Component " + component.getName() +
    " does not react to power save mode. Consider calling PowerManager.isPowerSaveMode() " +
    "or registering a receiver for ACTION_POWER_SAVE_MODE_CHANGED to adapt behavior when " +
    "the device is in power save mode."
