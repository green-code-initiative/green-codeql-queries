/**
 * @name Avoid saveAndFlush inside loops
 * @description In Spring data JPA, using saveAndFlush() inside a loop forces database synchronization on every iteration and disables batching. Consider using save() instead and flushing after the loop.
 * @kind problem
 * @problem.severity warning
 * @id java/spring/save-and-flush-in-loop
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
  call.getEnclosingStmt().getParent*() = loop and
  (
    call.getMethod().getDeclaringType().getASupertype*().hasQualifiedName(
      "org.springframework.data.jpa.repository",
      "JpaRepository"
    )
  )
select call,
  "saveAndFlush() inside a loop forces database synchronization on every iteration and disables batching."