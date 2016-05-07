package io.github.codemumbler.fittimer.model;

import android.media.AudioManager;
import android.media.ToneGenerator;

public class SoundMaker {

    public static void startTone(int tone) {
        Thread soundRunner = new Thread(new SoundMakerRunnable(tone));
        soundRunner.start();
    }

    private static class SoundMakerRunnable implements Runnable {

        private int sound;

        public SoundMakerRunnable(int tone) {
            this.sound = tone;
        }

        @Override
        public void run() {
            ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            if (toneGenerator.startTone(this.sound)) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                toneGenerator.stopTone();
            }
            toneGenerator.release();
        }
    }
}
