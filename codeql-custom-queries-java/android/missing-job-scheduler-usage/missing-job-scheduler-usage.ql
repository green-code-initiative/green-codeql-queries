/**
 * @name Missing JobScheduler usage
 * @description The Android 5.0 Lollipop (API 21) release introduces a job scheduler API via the JobScheduler class. Compared to a custom Sync Adapter or the alarm manager, the Job Scheduler supports batch scheduling of jobs. The Android system can combine jobs so that battery consumption is reduced. It means that at least one job scheduler exists through a call to the method JobScheduler#shedule(JobInfo job).
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/missing-job-scheduler-usage
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
      ancestor.hasName("Application") or
      ancestor.hasName("BroadcastReceiver")
    ) and
    not this.hasName("Activity") and
    not this.hasName("Service") and
    not this.hasName("Application") and
    not this.hasName("BroadcastReceiver") and
    not this.hasName("Context")
  }
}

class AlarmManagerCall extends MethodCall {
  AlarmManagerCall() {
    exists(Method m | m = this.getMethod() |
      m.getDeclaringType().hasName("AlarmManager") and
      (
        m.hasName("set") or
        m.hasName("setRepeating") or
        m.hasName("setExact") or
        m.hasName("setInexactRepeating")
      )
    )
  }
}

class JobSchedulerCall extends MethodCall {
  JobSchedulerCall() {
    this.getMethod().getDeclaringType().hasName("JobScheduler") and
    this.getMethod().hasName("schedule")
  }
}

from AndroidComponent component, AlarmManagerCall alarmCall
where
  alarmCall.getEnclosingCallable().getDeclaringType() = component and
  not exists(JobSchedulerCall jobCall |
    jobCall.getEnclosingCallable().getDeclaringType() = component
  )
select alarmCall,
  "Component " + component.getName() +
    " uses AlarmManager instead of JobScheduler.schedule(). JobScheduler batches jobs to reduce battery consumption."
