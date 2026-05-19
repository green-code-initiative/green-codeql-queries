/**
 * @name Avoid saveAndFlush inside loops
 * @description Calling saveAndFlush inside loops forces a flush on every iteration.
 * @kind problem
 * @problem.severity warning
 * @id java/hibernate/save-and-flush-in-loop
 * @tags performance
 */

import java

class Loop extends Stmt {
  Loop() {
    this instanceof ForStmt or
    this instanceof WhileStmt or
    this instanceof DoStmt or
    this instanceof EnhancedForStmt
  }
}

class SaveAndFlushCall extends MethodCall {
  SaveAndFlushCall() {
    this.getMethod().hasName("saveAndFlush")
  }
}

from Loop loop, SaveAndFlushCall call
where
  call.getEnclosingStmt().getParent*() = loop
select call,
  "saveAndFlush() inside a loop forces database synchronization on every iteration and disables batching."