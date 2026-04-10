/**
 * @name SensorManager registerListener without maxReportLatencyUs
 * @description With SensorManager#registerListener(SensorEventListener, Sensor, int) the events are delivered as soon as possible. Instead, SensorManager#registerListener(SensorEventListener, Sensor, int, int maxReportLatencyUs) allows events to stay temporarily in the hardware FIFO (queue) before being delivered. The events can be stored in the hardware FIFO up to maxReportLatencyUs microseconds. Once one of the events in the FIFO needs to be reported, all of the events in the FIFO are reported sequentially. Setting maxReportLatencyUs to a positive value allows to reduce the number of interrupts the AP (Application Processor) receives, hence reducing power consumption, as the AP can switch to a lower power state while the sensor is capturing the data.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-registerlistener-without-mrlu
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags android
 * @tags java
 */

import java

from MethodCall call
where
  call.getMethod().getDeclaringType().hasName("SensorManager") and
  call.getMethod().hasName("registerListener") and
  call.getNumArgument() = 3
select call,
  "SensorManager.registerListener() called without maxReportLatencyUs. " +
    "Use the 4-argument overload with a positive maxReportLatencyUs to enable sensor batching and reduce power consumption."
