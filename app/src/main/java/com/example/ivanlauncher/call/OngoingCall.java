package com.example.ivanlauncher.call;

import android.telecom.Call;

import com.example.ivanlauncher.MainActivityObserver;

public final class OngoingCall {

    public static Call call;


//    static CallActivity callActivity;

    static Call.Callback callback = new Call.Callback() {
        public void onStateChanged(Call call, int newState) {
//            if (callActivity != null)
//                callActivity.updateUi(newState);

            MainActivityObserver.updateCall(newState);
        }
    };

    public final static void setCall(Call call) {
        if (call != null) {
            call.unregisterCallback(callback);
            call.registerCallback(callback);
//            if (callActivity != null)
//                callActivity.updateUi(call.getState());
            MainActivityObserver.updateCall(call.getState());
        } else {
//            if (callActivity != null)
//                callActivity.kill_activity();

            MainActivityObserver.closeCall();

        }

        OngoingCall.call = call;
    }

    public static final void answer() {
        call.answer(0);
    }

    public static final void hangup() {
        if(call != null)
        call.disconnect();
    }

}