/**
 * @name Avoid Keep CPU On
 * @description To avoid draining the battery, an Android device that is left idle quickly falls asleep. Hence, keeping the screen on should be avoided, unless it is absolutely necessary. If so, developers typically use a Power Manager system service feature called wake locks by invoking PowerManager.WakeLock#newWakeLock(int levelAndFlags, String tag), along with the specific permission WAKE_LOCK in their manifest. 
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-keep-cpu-on
 * @link https://green-code-initiative.org/rules#id:GCI507
 * @tags android
 * @tags java
 */

import java

/** Detect any call to PowerManager.newWakeLock */
class WakeLockCall extends MethodCall {
  WakeLockCall() {
    this.getMethod().hasName("newWakeLock") and
    this.getMethod().getDeclaringType*().hasQualifiedName("", "PowerManager") and
    this.getMethod().getNumberOfParameters() = 2
  }
}

from WakeLockCall call
select call, "PowerManager.newWakeLock() acquires a wake lock, keeping the CPU on. This should be avoided unless absolutely necessary."