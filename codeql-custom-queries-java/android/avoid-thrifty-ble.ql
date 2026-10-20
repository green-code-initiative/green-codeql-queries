/**
 * @name Avoid Thrifty BLE
 * @description Bluetooth Low Energy operations should minimize battery consumption.
 *              BluetoothLeScanner#startScan() should use SCAN_MODE_LOW_POWER.
 *              BluetoothGatt#requestConnectionPriority() should use CONNECTION_PRIORITY_LOW_POWER.
 *              AdvertiseSettings.Builder#setAdvertiseMode() should use ADVERTISE_MODE_LOW_POWER.
 *              Using higher power modes drains the battery unnecessarily.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id java/android/avoid-thrifty-ble
 * @link https://green-code-initiative.org/rules#id:GCI525
 * @link https://green-code-initiative.org/rules#id:GCI526
 * @tags android
 * @tags java
 */

import java

from MethodCall mc
where
  (
    // Cas 1 : requestConnectionPriority appelé sans CONNECTION_PRIORITY_LOW_POWER
    mc.getMethod().getName() = "requestConnectionPriority" and
    exists(FieldRead fr |
      fr = mc.getArgument(0) and
      (
        fr.getField().getName() = "CONNECTION_PRIORITY_HIGH" or
        fr.getField().getName() = "CONNECTION_PRIORITY_BALANCED"
      )
    )
  )
  or
  (
    // Cas 2 : setAdvertiseMode appelé sans ADVERTISE_MODE_LOW_POWER
    mc.getMethod().getName() = "setAdvertiseMode" and
    exists(FieldRead fr |
      fr = mc.getArgument(0) and
      (
        fr.getField().getName() = "ADVERTISE_MODE_LOW_LATENCY" or
        fr.getField().getName() = "ADVERTISE_MODE_BALANCED"
      )
    )
  )
  or
  (
    // Cas 3 : startScan appelé avec ScanSettings dont le mode n'est pas SCAN_MODE_LOW_POWER
    mc.getMethod().getName() = "setScanMode" and
    exists(FieldRead fr |
      fr = mc.getArgument(0) and
      (
        fr.getField().getName() = "SCAN_MODE_LOW_LATENCY" or
        fr.getField().getName() = "SCAN_MODE_BALANCED" or
        fr.getField().getName() = "SCAN_MODE_OPPORTUNISTIC"
      )
    )
  )
select mc,
  "Thrifty BLE violation: use SCAN_MODE_LOW_POWER for BLE scans, CONNECTION_PRIORITY_LOW_POWER for GATT connections, and ADVERTISE_MODE_LOW_POWER for advertising to preserve battery life."