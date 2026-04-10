/**
 * @name Missing runtime memory check
 * @description Methods such as ActivityManager#isLowRamDevice() and ActivityManager#getMemoryClass() help you determine memory constraints at runtime. This information allows you to reduce memory usage. Compatibility with low-memory devices is good practice to help them last.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/missing-runtime-memory-check
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags andoid
 * @tags java
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
