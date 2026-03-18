/**
 * @name Prevent killing EEPROM through write cycles
 * @description Using 'write' instead of 'update' inside a loop causes physical wear
 * on the EEPROM cells (limited to ~100k cycles).
 * @kind problem
 * @problem.severity warning
 * @id cpp/arduino/prevent-eeprom-killer
 * @tags efficiency
 * sustainability
 * arduino
 */

import cpp

/**
 * Represents a call to the 'write' method on an EEPROM object.
 */
class EepromWriteCall extends FunctionCall {
  EepromWriteCall() {
    // 1. The function being called is named "write"
    this.getTarget().getName() = "write" and
    // 2. It belongs to a class named "EEPROMClass"
    this.getTarget().(MemberFunction).getDeclaringType().getName() = "EEPROMClass"
  }
}

from EepromWriteCall call, Function loop
where
  // Target the Arduino loop() function
  loop.getName() = "loop" and
  call.getEnclosingFunction() = loop and

  // Guard check: ensures the call isn't wrapped in a conditional
  not exists(IfStmt s | s.getAChild*() = call.getEnclosingStmt())

select call, "Don't use 'EEPROM.write' inside loop(). Use 'EEPROM.update' instead to prevent hardware degradation."