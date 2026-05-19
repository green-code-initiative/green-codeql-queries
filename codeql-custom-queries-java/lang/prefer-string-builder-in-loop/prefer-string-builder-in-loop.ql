/**
 * @name Prefer StringBuilder for String concatenation in loops
 * @description Using the += operator on a String inside a loop creates a new String object on each
 *              iteration, causing O(n²) memory allocations and increased garbage collector pressure.
 *              Use StringBuilder.append() instead to reduce object allocations and energy consumption.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/lang/prefer-string-builder-in-loop
 * @tags lang
 * @tags java
 */

import java

from AssignAddExpr concat, LoopStmt loop
where
  concat.getDest().getType().getName() = "String" and
  loop.getBody().getAChild*() = concat.getEnclosingStmt()
select concat,
  "String concatenation with '+=' inside a loop creates a new String object on each iteration. Use StringBuilder.append() instead to avoid O(n²) memory allocations."
