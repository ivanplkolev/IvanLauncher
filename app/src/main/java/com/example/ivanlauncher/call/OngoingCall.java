package com.example.ivanlauncher.call;

import android.telecom.Call;

public final class OngoingCall {

    public static Call call;


    static CallActivity callActivity;

    static Call.Callback callback = new Call.Callback() {
        public void onStateChanged(Call call, int newState) {
            if (callActivity != null)
                callActivity.updateUi(newState);
        }
    };

    public final static void setCall(Call call) {
        if (call != null) {
            call.unregisterCallback(callback);
            call.registerCallback(callback);
            if (callActivity != null)
                callActivity.updateUi(call.getState());
        } else {
            if (callActivity != null)
                callActivity.kill_activity();
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