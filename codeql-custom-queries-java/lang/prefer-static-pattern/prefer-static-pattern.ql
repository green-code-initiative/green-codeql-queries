/**
 * @name Prefer static final Pattern over in-method compile
 * @description Calling Pattern.compile() inside a method or constructor recompiles the regular
 *              expression on every invocation, wasting CPU cycles. Declare the Pattern as a
 *              static final field so it is compiled only once at class loading time.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/lang/prefer-static-pattern
 * @tags lang
 * @tags java
 */

import java

from MethodCall compileCall
where
  compileCall.getMethod().getName() = "compile" and
  compileCall.getMethod().getDeclaringType().getName() = "Pattern" and
  not compileCall.getEnclosingCallable() instanceof StaticInitializer and
  not exists(Field f | f.isStatic() and f.getInitializer() = compileCall)
select compileCall,
  "Pattern.compile() is called outside a static context and will recompile the regex on every invocation. Declare the Pattern as a 'static final' field to compile it only once."
