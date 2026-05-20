/**
 * @name Avoid List.contains() inside a loop
 * @description Calling List.contains() inside a loop performs a linear O(n) scan on each
 *              iteration, resulting in O(n^2) complexity overall. Converting the List to a
 *              HashSet before the loop reduces lookups to O(1) and significantly reduces
 *              CPU usage and energy consumption.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/lang/avoid-list-contains-in-loop
 * @tags lang
 * @tags java
 */

import java

from MethodCall containsCall, LoopStmt loop
where
  containsCall.getMethod().getName() = "contains" and
  containsCall.getQualifier().getType().(RefType).getASupertype*().getSourceDeclaration().hasQualifiedName("java.util", "List") and
  containsCall.getEnclosingStmt().getParent*() = loop
select containsCall,
  "List.contains() is called inside a loop, resulting in O(n^2) complexity. Convert the List to a HashSet before the loop for O(1) lookups and reduced CPU usage."
