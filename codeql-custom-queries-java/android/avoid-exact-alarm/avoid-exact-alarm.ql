/**
 * @name Prefer inexact alarms over exact alarms for battery efficiency
 * @description Applications are strongly discouraged from using exact alarms unnecessarily as they reduce the OS's ability to minimize battery use (i.e. Doze Mode). For most apps prior to API 19, setInexactRepeating() is preferable over setRepeating(). When you use this method, Android synchronizes multiple inexact repeating alarms and fires them at the same time, thus reducing the battery drain. Similarly, setExact() and setExactAndAllowWhileIdle() can significantly impact the power use of the device when idle, so they should be used with care. High-frequency alarms are also bad for battery life but this is already checked by Android lint (ShortAlarm built-in check).
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-exact-alarm
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags android
 * @tags java
 */

import java

class ExactAlarmCall extends MethodCall {
  string methodName;

  ExactAlarmCall() {
    this.getMethod().getDeclaringType*().hasQualifiedName("", "AlarmManager") and
    methodName = this.getMethod().getName() and
    methodName in ["setRepeating", "setExact", "setExactAndAllowWhileIdle"]
  }

  string getAlarmMethodName() { result = methodName }
}

string suggestion(ExactAlarmCall call) {
  call.getAlarmMethodName() = "setRepeating" and
  result =
    "AlarmManager.setRepeating() prevents the OS from batching alarms and " +
      "reduces battery efficiency. Consider using setInexactRepeating() instead, " +
      "which allows Android to synchronize repeating alarms and fire them together, " +
      "minimizing wake-ups and saving battery."
  or
  call.getAlarmMethodName() = "setExact" and
  result =
    "AlarmManager.setExact() demands exact timing and prevents Doze Mode " +
      "optimizations, significantly impacting battery life. Unless exact timing " +
      "is strictly required, consider using set() which allows the OS to defer " +
      "the alarm to reduce wake-ups."
  or
  call.getAlarmMethodName() = "setExactAndAllowWhileIdle" and
  result =
    "AlarmManager.setExactAndAllowWhileIdle() fires at exact times even during " +
      "Doze Mode, causing significant battery drain. Unless exact timing during " +
      "idle is strictly required, consider using setAndAllowWhileIdle() which " +
      "allows the OS to defer the alarm to reduce power consumption."
}

from ExactAlarmCall call, string msg
where msg = suggestion(call)
select call, msg
