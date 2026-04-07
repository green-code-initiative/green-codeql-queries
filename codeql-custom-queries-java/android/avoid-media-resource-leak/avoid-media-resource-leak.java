class MediaRecorder {
    void start() {}
    void stop() {}
    void release() {}
}

class MediaPlayer {
    void start() {}
    void stop() {}
    void release() {}
}

class NoncompliantMediaLeak {

    // 🚫 Noncompliant - MediaRecorder field never released
    MediaRecorder leakedRecorder = new MediaRecorder(); // $ Alert

    // 🚫 Noncompliant - MediaPlayer field never released
    MediaPlayer leakedPlayer = new MediaPlayer(); // $ Alert

    void recordAudio() {
        // 🚫 Noncompliant - local MediaRecorder never released
        MediaRecorder recorder = new MediaRecorder(); // $ Alert
        recorder.start();
        recorder.stop();
        // missing recorder.release()
    }

    void playAudio() {
        // 🚫 Noncompliant - local MediaPlayer never released
        MediaPlayer player = new MediaPlayer(); // $ Alert
        player.start();
        player.stop();
        // missing player.release()
    }

    void recordAndPlay() {
        // 🚫 Noncompliant - both never released
        MediaRecorder r = new MediaRecorder(); // $ Alert
        MediaPlayer p = new MediaPlayer();     // $ Alert
        r.start();
        p.start();
    }
}

class CompliantMediaUsage {

    void recordAudio() {
        // ✅ Compliant - release() called
        MediaRecorder recorder = new MediaRecorder(); // OK
        recorder.start();
        recorder.stop();
        recorder.release();
    }

    void playAudio() {
        // ✅ Compliant - release() called
        MediaPlayer player = new MediaPlayer(); // OK
        player.start();
        player.stop();
        player.release();
    }

    void recordAndPlay() {
        // ✅ Compliant - both released
        MediaRecorder r = new MediaRecorder(); // OK
        MediaPlayer p = new MediaPlayer();     // OK
        r.start();
        p.start();
        r.release();
        p.release();
    }
}