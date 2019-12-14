package com.example.ivanlauncher;

import android.os.Build;
import android.telecom.Call;
import android.telecom.InCallService;

import androidx.annotation.RequiresApi;


public final class CallService extends InCallService {
    public void onCallAdded( Call call) {
//        Intrinsics.checkParameterIsNotNull(call, "call");
        OngoingCall.setCall(call);
//        CallActivity.Companion.start(this, call);
    }

    public void onCallRemoved( Call call) {
//        Intrinsics.checkParameterIsNotNull(call, "call");
        OngoingCall.setCall(null);
    }

}
