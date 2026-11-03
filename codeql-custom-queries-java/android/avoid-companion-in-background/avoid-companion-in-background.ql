/**
 * @name Avoid companion in background 
 * @description A negative effect on the device's battery is when an app is paired with a companion device (over Bluetooth, BLE, or Wi-Fi) and that it has been excluded from battery optimizations (run in the background) using the declaration Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND. Consider removing this permission and using scoped companion device profiles instead.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-companion-in-background
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags android
 * @tags java
 */

import java

class CompanionRunInBackgroundFieldAccess extends FieldAccess {
  CompanionRunInBackgroundFieldAccess() {
    this.getField().hasName("REQUEST_COMPANION_RUN_IN_BACKGROUND") and
    this.getField().getDeclaringType().hasName("permission") and
    this.getField().getDeclaringType().getEnclosingType().hasName("Manifest")
  }
}

class CompanionRunInBackgroundStringLiteral extends StringLiteral {
  CompanionRunInBackgroundStringLiteral() {
    this.getValue() = "android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND"
  }
}

from Expr usage
where
  usage instanceof CompanionRunInBackgroundFieldAccess
  or
  (
    usage instanceof CompanionRunInBackgroundStringLiteral and
    not exists(Field f |
      f.getInitializer() = usage and
      f.hasName("REQUEST_COMPANION_RUN_IN_BACKGROUND") and
      f.getDeclaringType().hasName("permission") and
      f.getDeclaringType().getEnclosingType().hasName("Manifest")
    )
  )
select usage,
  "Usage of REQUEST_COMPANION_RUN_IN_BACKGROUND permission detected. " +
    "Excluding a companion device app from battery optimizations allows it to run in the " +
    "background indefinitely, which has a negative effect on the device's battery. " +
    "Consider removing this permission or restricting background execution."
