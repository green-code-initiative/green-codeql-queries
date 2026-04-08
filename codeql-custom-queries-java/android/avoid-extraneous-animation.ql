/**
 * @name Avoid Extraneous Animation
 * @description Avoiding extraneous animations in the UI is a good practice for saving battery power.
 *              This can be checked either in the Java code when an object is instance of Animator (sub)class,
 *              or simply through the presence of xml files in the res/animator/ resource directory.
 * @kind problem
 * @problem.severity warning
 * @id java/android/avoid-extraneous-animation
 * @tags android
 */

import java

from ClassInstanceExpr new
where
  new.getConstructedType().getQualifiedName() = "Animator" or
  new.getConstructedType().getASupertype*().getQualifiedName() = "Animator"
select new, "Avoid extraneous animation calls that may drain the battery."
