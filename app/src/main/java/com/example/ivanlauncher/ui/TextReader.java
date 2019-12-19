package com.example.ivanlauncher.ui;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TextReader {

    private static TextToSpeech textToSpeech;


    private static final int NOT_INITALED_STATUS = -100;
    static int initStatus = NOT_INITALED_STATUS;


    public static void init(final Context context) {
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                setStatus(status);
            }
        });
    }

    private static void setStatus(int initStatus) {
        TextReader.initStatus = initStatus;
    }

    public static void read(String str) {
        if (!isInitialized()) {
            return;
        }
        read(str, null);
    }

    public static void read(String str, Locale l) {
        if (!isInitialized()) {
            return;
        }

        int speechStatus = textToSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, null, null);

        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }

    }

    public static boolean isInitialized() {
        return initStatus != NOT_INITALED_STATUS;
    }


    public static void shutdown() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

}
