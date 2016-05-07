package io.github.codemumbler.fittimer;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

public class TextToSpeechWrapper {

    private final TextToSpeech textToSpeech;

    public TextToSpeechWrapper(final SessionRunner.Callback callback, final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    callback.execute();
                    textToSpeech.setSpeechRate(0.85f);
                }
            });
        } else {
            textToSpeech = null;
        }
    }

    public void speak(final String textToSay) {
        Thread speechThread = new Thread(new SpeechRunner(textToSpeech, textToSay));
        speechThread.start();
    }

    public void shutdown() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.shutdown();
        }
    }

    private static class SpeechRunner implements Runnable {

        private final TextToSpeech textToSpeech;
        private final String textToSay;

        private SpeechRunner(final TextToSpeech textToSpeech, final String textToSay) {
            this.textToSpeech = textToSpeech;
            this.textToSay = textToSay;
        }

        @Override
        public void run() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(textToSay, TextToSpeech.QUEUE_FLUSH, null, "Pose Name");
            }
        }
    }
}
