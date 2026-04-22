/**
 * @name GLSurfaceView uses RENDERMODE_CONTINUOUSLY
 * @description For developers wishing to display OpenGL rendering, when choosing the rendering mode with GLSurfaceView#setRenderMode(int renderMode), using RENDERMODE_WHEN_DIRTY instead of RENDERMODE_CONTINUOUSLY (By default) can improve battery life and overall system performance by allowing the GPU and CPU to idle when the view does not need to be updated.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-rendermode-continuously
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags android
 * @tags java
 */

import java

class SetRenderModeContinuouslyCall extends MethodCall {
  SetRenderModeContinuouslyCall() {
    this.getMethod().hasName("setRenderMode") and
    this.getMethod().getDeclaringType*().hasQualifiedName("", "GLSurfaceView") and
    this.getMethod().getNumberOfParameters() = 1 and
    (
      // Direct field access: GLSurfaceView.RENDERMODE_CONTINUOUSLY
      exists(FieldAccess fa |
        fa = this.getArgument(0) and
        fa.getField().hasName("RENDERMODE_CONTINUOUSLY") and
        fa.getField().getDeclaringType().hasQualifiedName("", "GLSurfaceView")
      )
      or
      // Raw integer literal 1
      this.getArgument(0).(IntegerLiteral).getIntValue() = 1
    )
  }
}

from SetRenderModeContinuouslyCall call
select call,
  "GLSurfaceView.setRenderMode is called with RENDERMODE_CONTINUOUSLY. " +
    "This causes the renderer to continuously produce frames, draining the battery. " +
    "Consider using RENDERMODE_WHEN_DIRTY instead, which only renders when the " +
    "surface is created or when requestRender() is called, allowing the GPU and CPU " +
    "to idle and improving battery life."
