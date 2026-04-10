/**
 * @name Internet In The Loop
 * @description Opening and closing internet connections continuously is extremely battery-inefficient.
 *              Obtaining a new HttpURLConnection by calling URL#openConnection() within a loop
 *              control structure (while, for, do-while, for-each) is a bad practice that leads
 *              to uncontrolled energy leakage and prevents the use of push notifications.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/internet-in-loop
 * @link https://green-code-initiative.org/rules#id:GCI502
 * @tags android
 * @tags java
 */

import java

from MethodCall openCall, LoopStmt loop
where
  openCall.getMethod().getName() = "openConnection" and
  loop.getBody().getAChild*() = openCall.getEnclosingStmt()
select openCall,
  "URL#openConnection() is called inside a loop. Opening HTTP connections in a loop is battery-inefficient. Consider using push notifications instead of polling."