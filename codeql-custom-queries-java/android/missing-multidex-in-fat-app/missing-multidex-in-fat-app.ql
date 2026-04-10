/**
 * @name Missing multidex in fat app
 * @description When an app exceeds the limit of 65 536 method references, the configuration multidex must be enabled with multiDexEnabled true in the defaultConfig section of build.gradle. Fat apps rarely install on space-limited devices.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/missing-multidex-in-fat-app
 * @link https://github.com/cnumr/best-practices-mobile
 * @tags andoid
 * @tags java
 */

import java

class MultidexApp extends Class {
  MultidexApp() {
    this.getASupertype*().hasName("MultiDexApplication") and
    not this.hasName("MultiDexApplication")
  }
}

class MultidexInstallCall extends MethodCall {
  MultidexInstallCall() {
    exists(Method m | m = this.getMethod() |
      m.getDeclaringType().hasName("MultiDex") and m.hasName("install")
    )
  }
}

from Top t, string msg
where
  (
    t instanceof MultidexApp and
    msg = "Class " + t.(Class).getName() + " extends MultiDexApplication. This indicates multidex is enabled — consider reducing method count to avoid fat APKs that struggle on low-storage devices."
  )
  or
  (
    t instanceof MultidexInstallCall and
    msg = "Call to MultiDex.install() detected. This indicates multidex is enabled — consider reducing method count to avoid fat APKs that struggle on low-storage devices."
  )
select t, msg
