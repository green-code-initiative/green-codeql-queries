/**
 * @name WorkRequest without battery constraints
 * @description You can use the WorkManager library to perform work on an efficient schedule that considers whether specific conditions are met, such as power status. A worker can be scheduled to run, provided the device's battery isn't low for exemple. This can be checked by a call to WorkRequest.Builder#setConstraints() where the constraint object built involves Constraints.Builder#setRequiresBatteryNotLow(true) or, in its extremist form, Constraints.Builder#setRequiresCharging(true).
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-work-without-battery-constraints
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags android
 * @tags java
 */

import java

class WorkRequestBuildCall extends MethodCall {
  WorkRequestBuildCall() {
    this.getMethod().hasName("build") and
    this.getMethod().getDeclaringType().getASupertype*().hasName("Builder") and
    this.getMethod()
        .getDeclaringType()
        .getEnclosingType()
        .getASupertype*()
        .hasName("WorkRequest")
  }
}

class BatteryConstraintCall extends MethodCall {
  BatteryConstraintCall() {
    this.getMethod().getDeclaringType().hasName("Builder") and
    this.getMethod().getDeclaringType().getEnclosingType().hasName("Constraints") and
    (
      this.getMethod().hasName("setRequiresBatteryNotLow") or
      this.getMethod().hasName("setRequiresCharging")
    ) and
    this.getArgument(0).(BooleanLiteral).getBooleanValue() = true
  }
}

from WorkRequestBuildCall buildCall, Callable enclosing
where
  enclosing = buildCall.getEnclosingCallable() and
  not exists(BatteryConstraintCall batteryCall |
    batteryCall.getEnclosingCallable() = enclosing
  )
select buildCall,
  "WorkRequest built without battery constraints. Consider adding " +
    "setRequiresBatteryNotLow(true) or setRequiresCharging(true) to the Constraints " +
    "to defer work until the device has sufficient power."
