// Stubs
class MediaRecorder {
    fun release() {}
    fun start() {}
    fun stop() {}
}

class MediaPlayer {
    fun release() {}
    fun start() {}
    fun stop() {}
}

// 🚫 Noncompliant - no release() called
class NoncompliantMediaLeak {

    fun recordAudio() {
        val recorder = MediaRecorder() // $ Alert
        recorder.start()
        recorder.stop()
        // missing recorder.release()
    }

    fun playAudio() {
        val player = MediaPlayer() // $ Alert
        player.start()
        player.stop()
        // missing player.release()
    }

    val leakedRecorder = MediaRecorder() // $ Alert
    val leakedPlayer = MediaPlayer()     // $ Alert
}

// ✅ Compliant - release() is called
class CompliantMediaUsage {

    fun recordAudio() {
        val recorder = MediaRecorder() // OK
        recorder.start()
        recorder.stop()
        recorder.release()
    }

    fun playAudio() {
        val player = MediaPlayer() // OK
        player.start()
        player.stop()
        player.release()
    }
}