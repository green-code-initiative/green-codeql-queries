/**
 * @name Remove useless ToString() calls on string
 * @description Detects parameterless calls to `ToString()` on `string` where the result is unused (expression statement).
 * @kind problem
 * @problem.severity warning
 * @id csharp/gci2508-remove-useless-tostring
 * @tags performance
 */

import csharp

from Invocation call, Method method, Expr recv
where
  // target method named ToString with no parameters
  call.getMethod() = method and
  method.getName() = "ToString" and
  method.getNumberOfParameters() = 0 and

  // declared on System.String (simple name check; adapt as needed for full qualification)
  method.getDeclaringType().getName() = "String" and

  // receiver exists and the invocation is used as an expression statement (i.e. result discarded)
  call.getTarget() = method and
  call.getReceiver() = recv and
  call.getParent() instanceof ExpressionStatement

select call, "A ToString() call on a string is unnecessary when its result is discarded."
