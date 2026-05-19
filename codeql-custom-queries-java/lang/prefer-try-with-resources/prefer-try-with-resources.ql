/**
 * @name Prefer try-with-resources for AutoCloseable objects
 * @description AutoCloseable resources (streams, connections, readers, writers) declared outside a
 *              try-with-resources statement may not be closed on all execution paths, causing
 *              resource leaks that increase memory pressure and energy consumption.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/lang/prefer-try-with-resources
 * @tags lang
 * @tags java
 */

import java

from ClassInstanceExpr newExpr, LocalVariableDeclExpr decl
where
  newExpr.getConstructedType().getASupertype*().getName() = "AutoCloseable" and
  decl.getInit() = newExpr and
  not decl.getEnclosingStmt().getParent() instanceof TryStmt
select newExpr,
  newExpr.getConstructedType().getName() +
    " implements AutoCloseable and should be opened in a try-with-resources statement to guarantee it is always closed."
