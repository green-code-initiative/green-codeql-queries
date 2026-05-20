/**
 * @name Remove useless ToString() calls on string
 * @description Detects parameterless calls to `ToString()` on `string` where the result is unused (expression statement).
 * @kind problem
 * @problem.severity warning
 * @id csharp/gci2508-remove-useless-tostring
 * @tags performance
 */

import csharp

from MethodCall call
where
	call.getTarget().getName() = "ToString" and
	call.getNumberOfArguments() = 0 and
	call.getTarget().getDeclaringType().hasFullyQualifiedName("System", "String")
select call, "Found parameterless ToString() on System.String"
