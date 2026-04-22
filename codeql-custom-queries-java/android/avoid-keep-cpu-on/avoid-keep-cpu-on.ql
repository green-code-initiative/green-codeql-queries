/**
 * @name Keeping the screen on should be avoided
 * @description To avoid draining the battery, an Android device that is left idle quickly falls asleep. Hence, keeping the screen on should be avoided, unless it is absolutely necessary. If so, developers typically use a Power Manager system service feature called wake locks by invoking PowerManager.WakeLock#newWakeLock(int levelAndFlags, String tag), along with the specific permission WAKE_LOCK in their manifest. 
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-keeping-cpu-on
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags android
 * @tags java
 */

import java

predicate isScreenWakeLockLevel(Expr e) {
  exists(FieldAccess fa | fa = e |
    fa.getField().getDeclaringType().hasQualifiedName("", "PowerManager") and
    fa.getField().hasName(["SCREEN_DIM_WAKE_LOCK", "SCREEN_BRIGHT_WAKE_LOCK", "FULL_WAKE_LOCK"])
  )
  or
  e.(IntegerLiteral).getIntValue() in [6, 10, 26]
}

class ScreenWakeLockCall extends MethodCall {
  ScreenWakeLockCall() {
    this.getMethod().hasName("newWakeLock") and
    this.getMethod().getDeclaringType*().hasQualifiedName("", "PowerManager") and
    this.getMethod().getNumberOfParameters() = 2 and
    isScreenWakeLockLevel(this.getArgument(0))
  }
}

class WindowKeepScreenOnCall extends MethodCall {
  WindowKeepScreenOnCall() {
    this.getMethod().hasName("addFlags") and
    this.getMethod().getDeclaringType*().hasQualifiedName("", "Window") and
    this.getMethod().getNumberOfParameters() = 1 and
    (
      exists(FieldAccess fa | fa = this.getArgument(0) |
        fa.getField().hasName("FLAG_KEEP_SCREEN_ON")
      )
      or
      this.getArgument(0).(IntegerLiteral).getIntValue() = 128
    )
  }
}

class ViewKeepScreenOnCall extends MethodCall {
  ViewKeepScreenOnCall() {
    this.getMethod().hasName("setKeepScreenOn") and
    this.getMethod().getDeclaringType*().hasQualifiedName("", "View") and
    this.getMethod().getNumberOfParameters() = 1 and
    this.getArgument(0).(BooleanLiteral).getBooleanValue() = true
  }
}

from MethodCall call, string msg
where
  (
    call instanceof ScreenWakeLockCall and
    msg =
      "PowerManager.newWakeLock() is called with a screen-affecting wake lock level " +
        "(SCREEN_DIM_WAKE_LOCK, SCREEN_BRIGHT_WAKE_LOCK, or FULL_WAKE_LOCK). " +
        "This keeps the screen on and significantly drains battery. " +
        "If only CPU is needed, use PARTIAL_WAKE_LOCK instead."
  )
  or
  (
    call instanceof WindowKeepScreenOnCall and
    msg =
      "Window.addFlags() is called with FLAG_KEEP_SCREEN_ON, which prevents the " +
        "screen from turning off and drains the battery. Avoid keeping the screen " +
        "on unless absolutely necessary."
  )
  or
  (
    call instanceof ViewKeepScreenOnCall and
    msg =
      "View.setKeepScreenOn(true) prevents the screen from turning off and drains " +
        "the battery. Avoid keeping the screen on unless absolutely necessary."
  )
select call, msg
