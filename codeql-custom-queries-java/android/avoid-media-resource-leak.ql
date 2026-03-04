/**
 * @name Avoid media resource leak by calling release() on MediaRecorder and MediaPlayer
 * @description Failing to call release() on MediaRecorder or MediaPlayer objects leads to unnecessary memory usage, codec instances being held, and continuous battery consumption on mobile devices.
 * @kind problem
 * @problem.severity warning
 * @precision medium
 * @id java/android/avoid-media-resource-leak
 * @tags android
 */

import java

from ClassInstanceExpr newExpr, RefType t
where
  newExpr.getConstructedType() = t and
  (
    t.getName() = "MediaRecorder" or
    t.getName() = "MediaPlayer"
  ) and
  not exists(MethodCall release |
    release.getMethod().getName() = "release" and
    release.getQualifier().(VarAccess).getVariable().getAnAccess() = newExpr.getParent*()
  )
select newExpr,
  "Call release() on '" + t.getName() + "' when it is no longer needed to free memory, codec instances, and avoid continuous battery consumption."