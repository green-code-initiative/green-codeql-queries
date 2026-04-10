/**
 * @name Extraneous Init
 * @description Avoid initialising anything unnecessarily in the Application class onCreate() method.
 *              Unnecessary initialisation in Application#onCreate() increases app start-up latency
 *              and wastes energy. Many libraries offer on-demand initialisation — call them only
 *              when needed.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-extraneous-init
 * @tags android
 * @tags java
 */

import java

from MethodCall mc
where
  mc.getEnclosingCallable().getName() = "onCreate" and
  mc.getEnclosingCallable().getDeclaringType().getAnAncestor().getName() = "Application"
select mc,
  "Avoid initialising '" + mc.getMethod().getName() +
  "()' in Application#onCreate(). Prefer on-demand initialisation to reduce start-up latency and save energy."