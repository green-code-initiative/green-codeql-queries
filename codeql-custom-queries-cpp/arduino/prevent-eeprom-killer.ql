/**
 * @name Prevent killing EEPROM through write cycles
 * @description Using 'write' instead of 'update' inside a loop causes physical wear
 *              on the EEPROM cells (limited to ~100k cycles).
 * @kind problem
 * @problem.severity warning
 * @id cpp/arduino/prevent-eeprom-killer
 * @tags efficiency
 * @tags sustainability
 * @tags arduino
 * @tags cpp
 */

import cpp

class EepromWriteCall extends FunctionCall {
  EepromWriteCall() {
    this.getTarget().getName() = "write" and
    // On utilise matches pour être plus flexible sur le nom de la classe (Stub ou Class)
    this.getTarget().(MemberFunction).getDeclaringType().getName().matches("EEPROM%")
  }
}

from EepromWriteCall call, Function f
where
  f = call.getEnclosingFunction() and
  (
    // Cas 1 : C'est dans la fonction principale loop
    f.getName() = "loop" or 
    // Cas 2 : C'est n'importe où à l'intérieur d'une boucle (for, while)
    exists(Loop l | l.getAChild*() = call.getEnclosingStmt())
  )
select call, "Don't use 'EEPROM.write' inside loop(). Use 'EEPROM.update' instead to prevent hardware degradation."