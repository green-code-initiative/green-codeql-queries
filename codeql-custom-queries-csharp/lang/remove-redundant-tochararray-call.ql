/**
 * @name Remove redundant 'ToCharArray' call
 * @description A foreach loop can iterate directly over a string.
 *              Calling 'ToCharArray()' first allocates a useless array.
 * @kind problem
 * @problem.severity warning
 * @id csharp/gc2333-remove-redundant-to-char-array-call
 * @tags performance
 */

import csharp

from ForeachStmt foreach, MethodCall call
where
  foreach.getIterableExpr() = call and
  call.getTarget().getName() = "ToCharArray" and
  call.getNumberOfArguments() = 0 and
  call.getTarget().getDeclaringType().hasFullyQualifiedName("System", "String")
select call,
  "The 'ToCharArray' call is redundant: foreach can iterate directly over a string."