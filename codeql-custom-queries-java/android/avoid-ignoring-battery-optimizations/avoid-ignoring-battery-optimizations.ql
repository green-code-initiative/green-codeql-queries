/**
 * @name Avoid Ignoring Battery Optimizations
 * @description An app holding the Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS ask the user to allow it to ignore battery optimizations (that is, put them on the whitelist of apps). Most applications should not use this; there are many facilities provided by the platform for applications to operate correctly in the various power saving modes.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-ignoring-battery-optimizations
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags android
 * @tags java
 */

import java

/**
 * Holds if the given expression is part of a field declaration inside
 * a known stub/constants class (`Manifest.permission` or `Settings`),
 * either as the initializer or as the declared field access itself.
 */
predicate isStubDeclarationSite(Expr e) {
  exists(Field f |
    (
      f.getDeclaringType().hasName("permission") and
      f.getDeclaringType().getEnclosingType().hasName("Manifest")
    )
    or
    f.getDeclaringType().hasName("Settings")
  |
    f.getInitializer() = e
    or
    e.(FieldAccess).getField() = f and
    f.getInitializer().getParent*() = e.getParent*()
  )
}

/**
 * A field access to `Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS`,
 * excluding the stub declaration site.
 */
class IgnoreBatteryOptFieldAccess extends FieldAccess {
  IgnoreBatteryOptFieldAccess() {
    this.getField().hasName("REQUEST_IGNORE_BATTERY_OPTIMIZATIONS") and
    this.getField().getDeclaringType().hasName("permission") and
    this.getField().getDeclaringType().getEnclosingType().hasName("Manifest") and
    not this.getEnclosingCallable().getDeclaringType().hasName("permission")
  }
}

/**
 * A string literal `"android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS"`,
 * excluding the one used as the initializer of the `Manifest.permission` constant.
 */
class IgnoreBatteryOptStringLiteral extends StringLiteral {
  IgnoreBatteryOptStringLiteral() {
    this.getValue() = "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" and
    not isStubDeclarationSite(this)
  }
}

/**
 * A field access to `Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS`,
 * excluding the stub declaration site.
 */
class IgnoreBatteryOptIntentFieldAccess extends FieldAccess {
  IgnoreBatteryOptIntentFieldAccess() {
    this.getField().hasName("ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS") and
    this.getField().getDeclaringType().hasName("Settings") and
    not this.getEnclosingCallable().getDeclaringType().hasName("Settings")
  }
}

/**
 * A string literal `"android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS"`,
 * excluding the one used as the initializer of the `Settings` constant.
 */
class IgnoreBatteryOptIntentStringLiteral extends StringLiteral {
  IgnoreBatteryOptIntentStringLiteral() {
    this.getValue() = "android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" and
    not isStubDeclarationSite(this)
  }
}

from Expr usage
where
  usage instanceof IgnoreBatteryOptFieldAccess
  or
  usage instanceof IgnoreBatteryOptStringLiteral
  or
  usage instanceof IgnoreBatteryOptIntentFieldAccess
  or
  usage instanceof IgnoreBatteryOptIntentStringLiteral
select usage,
  "Usage of REQUEST_IGNORE_BATTERY_OPTIMIZATIONS detected. " +
    "This asks the user to whitelist the app from battery optimizations, which most " +
    "applications should not need. The platform provides many facilities for apps to " +
    "operate correctly in the various power saving modes. Consider removing this permission " +
    "and using standard platform mechanisms instead."
