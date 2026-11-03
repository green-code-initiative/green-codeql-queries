/**
 * @name Manual array copy
 * @description Detects loops that manually copy array elements instead of using System.arraycopy.
 * @kind problem
 * @problem.severity recommendation
 * @precision high
 * @id java/lang/manual-array-copy
 * @tags lang
 */

import java

predicate isArrayElementAssignment(Assignment assign, Expr src, Expr dest) {
  (
    assign.getDest().(ArrayAccess).getArray() = dest and
    assign.getSource().(ArrayAccess).getArray() = src
  )
  or
  exists(EnhancedForStmt forEach, Variable iterVar |
    iterVar = forEach.getVariable().getVariable() and
    assign.getDest().(ArrayAccess).getArray() = dest and
    assign.getSource().(VarAccess).getVariable() = iterVar and
    src = forEach.getExpr()
  )
}

from LoopStmt loop, Assignment assign, Expr src, Expr dest
where
  assign.getEnclosingStmt().getParent*() = loop and
  isArrayElementAssignment(assign, src, dest) and
  not src = dest
select loop, "Use System.arraycopy to copy arrays instead of a manual loop."
