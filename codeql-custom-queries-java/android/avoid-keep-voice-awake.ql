/**
 * @name Avoid keep voice awake
 * @description During a voice interaction session, VoiceInteractionSession#setKeepAwake(boolean keepAwake) allows to decide whether it will keep the device awake while it is running a voice activity. By default, the system holds a wake lock for it while in this state, so that it can work even if the screen is off. Setting this to false removes that wake lock, allowing the CPU to go to sleep and hence does not let this continue to drain the battery.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-keep-voice-awake
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags android
 * @tags java
 */

import java

class KeepAwakeTrueCall extends MethodCall {
  KeepAwakeTrueCall() {
    this.getMethod().hasName("setKeepAwake") and
    this.getMethod().getDeclaringType().hasQualifiedName("", "VoiceInteractionSession") and
    this.getMethod().getNumberOfParameters() = 1 and
    this.getMethod().getParameterType(0).hasName("boolean") and
    this.getArgument(0).(BooleanLiteral).getBooleanValue() = true
  }
}

from KeepAwakeTrueCall call
select call,
  "VoiceInteractionSession.setKeepAwake(true) holds a wake lock that keeps the device " +
    "awake while the voice interaction session is running, draining the battery even when " +
    "the screen is off. Consider calling setKeepAwake(false) to release the wake lock and " +
    "allow the CPU to sleep."
