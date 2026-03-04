package android.view

class WindowManager {
    class LayoutParams {
        companion object {
            const val FLAG_KEEP_SCREEN_ON = 128
        }
    }
}

class AvoidKeepScreenOn {
    fun success() {
        val x = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON // $ Alert
    }
}