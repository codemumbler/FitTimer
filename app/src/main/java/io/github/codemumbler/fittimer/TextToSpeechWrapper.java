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
                }
            });
        } else {
            textToSpeech = null;
        }
    }

    public void speak(String textToSay) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak((CharSequence) textToSay, TextToSpeech.QUEUE_FLUSH, null, "Pose Name");
        }
    }

    public void shutdown() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.shutdown();
        }
    }
}
