/**
 * @name require to close all eventListener
 * @description DOM elements that have 'addEventListener' calls should also have corresponding 'removeEventListener' calls to prevent memory leaks and resource accumulation. Failing to remove event listeners can lead to memory bloat, especially in single-page applications where elements are dynamically created and destroyed.
 * @kind problem
 * @problem.severity warning
 * @precision medium
 * @id js/web-api/missing-remove-event-listener
 * @tags web-api
 * @tags memory-leak
 * @tags event-management
 * @tags js
 */

import javascript

from MethodCallExpr addCall
where
  addCall.getMethodName() = "addEventListener" and
  not exists(MethodCallExpr removeCall |
    removeCall.getMethodName() = "removeEventListener" and
    removeCall.getReceiver().toString() = addCall.getReceiver().toString() and
    removeCall.getArgument(0).toString() = addCall.getArgument(0).toString() and
    removeCall.getArgument(1).toString() = addCall.getArgument(1).toString()
  )
select addCall, "Event listener added with 'addEventListener' but no corresponding 'removeEventListener' found. This may cause memory leaks. Always pair 'addEventListener' with 'removeEventListener' when the listener is no longer needed."
