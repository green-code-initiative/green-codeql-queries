/**
 * @name Prefer Bluetooth Low Energy over classic Bluetooth
 * @description Using classic Bluetooth (android.bluetooth) without Bluetooth Low Energy (android.bluetooth.le) leads to significantly higher power consumption. Use BLE APIs to reduce energy usage on both paired devices.
 * @kind problem
 * @problem.severity warning
 * @precision medium
 * @id java/android/prefer-bluetooth-low-energy
 * @link https://green-code-initiative.org/rules#id:GCI518
 * @tags android
 */

import java

from Variable v, RefType t
where
  v.getType() = t and
  (
    t.getName() = "BluetoothAdapter" or
    t.getName() = "BluetoothDevice" or
    t.getName() = "BluetoothSocket" or
    t.getName() = "BluetoothServerSocket" or
    t.getName() = "BluetoothProfile"
  )
select v,
  "Avoid classic Bluetooth ('" + t.getName() + "'). Use Bluetooth Low Energy APIs from android.bluetooth.le instead to significantly reduce power consumption."