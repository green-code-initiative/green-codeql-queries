/**
 * @name Missing runtime memory check
 * @description The application uses memory-intensive operations without calling
 *              ActivityManager.isLowRamDevice() or ActivityManager.getMemoryClass()
 *              to adapt behavior to low-memory devices.
 * @kind problem
 * @problem.severity warning
 * @precision medium
 * @id java/android/missing-memory-check
 * @tags efficiency
 *       android
 *       eco-design
 */

import java

class AndroidComponent extends Class {
  AndroidComponent() {
    exists(Class ancestor | ancestor = this.getASupertype*() |
      ancestor.hasName("Activity") or
      ancestor.hasName("Service") or
      ancestor.hasName("Application")
    ) and
    not this.hasName("Activity") and
    not this.hasName("Service") and
    not this.hasName("Application") and
    not this.hasName("Context")
  }
}

class MemoryCheckCall extends MethodCall {
  MemoryCheckCall() {
    exists(Method m | m = this.getMethod() |
      m.getDeclaringType().hasName("ActivityManager") and
      (m.hasName("isLowRamDevice") or m.hasName("getMemoryClass"))
    )
  }
}

class MemoryIntensiveCall extends MethodCall {
  MemoryIntensiveCall() {
    exists(Method m | m = this.getMethod() |
      m.getDeclaringType().hasName("BitmapFactory") and m.getName().matches("decode%")
      or
      m.getDeclaringType().hasName("Bitmap") and (m.hasName("createBitmap") or m.hasName("copy"))
      or
      m.getDeclaringType().hasName("LruCache") and (m.hasName("resize") or m.hasName("put"))
    )
  }
}

from AndroidComponent component, MemoryIntensiveCall intensiveCall
where
  intensiveCall.getEnclosingCallable().getDeclaringType() = component and
  not exists(MemoryCheckCall check |
    check.getEnclosingCallable().getDeclaringType() = component
  )
select intensiveCall,
  "Memory-intensive call without any ActivityManager.isLowRamDevice() or getMemoryClass() check in component " +
    component.getName() + ". Consider adapting memory usage for low-RAM devices."
