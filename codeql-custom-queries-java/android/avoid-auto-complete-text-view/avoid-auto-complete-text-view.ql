/**
 * @name Avoid AutoCompleteTextView
 * @description Using AutoCompleteTextView can trigger numerous requests to a remote server
 *              as the user types, increasing network usage and energy consumption.
 *              Prefer a simple EditText with a static hint/placeholder instead.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-auto-complete-text-view
 * @tags android
 * @tags java
 */

import java

from ClassInstanceExpr cie
where
  cie.getConstructedType().hasQualifiedName("android.widget", "AutoCompleteTextView")
  or
  cie.getConstructedType().hasQualifiedName("android.widget", "MultiAutoCompleteTextView")
select cie,
  "Avoid using '" + cie.getConstructedType().getName() +
  "'. It can trigger numerous remote server requests as the user types. " +
  "Prefer a simple EditText with a static hint to guide the user."