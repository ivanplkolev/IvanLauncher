package com.example.ivanlauncher;

import android.telecom.Call;

public final class OngoingCall {

    private static Call call;


    static CallActivity callActivity;

    static Call.Callback callback = new Call.Callback() {
        public void onStateChanged(Call call, int newState) {
            callActivity.updateUi(newState);
        }
    };

    public final static void setCall( Call call) {
        if (call != null) {
            call.unregisterCallback(callback);
            call.registerCallback(callback);
            callActivity.updateUi(call.getState());
        }

        OngoingCall.call = call;
    }

    public static final void answer() {
        call.answer(0);
    }

    public static final void hangup() {
        call.disconnect();
    }

}