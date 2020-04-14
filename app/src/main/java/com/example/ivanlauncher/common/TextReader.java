package com.example.ivanlauncher.common;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TextReader {

    private static TextToSpeech textToSpeech;


    private static final int NOT_INITALED_STATUS = -100;
    static int initStatus = NOT_INITALED_STATUS;


    public static void init(final Context context) {
        textToSpeech = new TextToSpeech(context, status -> setStatus(status));
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

    private static void read(String str, Locale l) {
        if (!isInitialized()) {
            return;
        }

        int speechStatus = textToSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, null, null);

        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }


        //todo not all invokations should be blocking !!!! -- may be add it another method and check it only for not priority texts
//        while(textToSpeech.isSpeaking()){
//            try {
//                Thread.sleep(10);//or 5
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

    }


    public static  boolean isReading(){
        return textToSpeech.isSpeaking();
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
