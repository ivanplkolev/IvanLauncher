package com.example.ivanlauncher;

import android.content.Intent;
import android.os.Build;
import android.telecom.Call;
import android.telecom.InCallService;

import androidx.annotation.RequiresApi;


public final class CallService extends InCallService {
    public void onCallAdded( Call call) {
//        Intrinsics.checkParameterIsNotNull(call, "call");
        OngoingCall.setCall(call);

        Intent i = new Intent(this, CallActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("number", call.getDetails().getHandle().getSchemeSpecificPart());

        startActivity(i);
    }

    public void onCallRemoved( Call call) {
//        Intrinsics.checkParameterIsNotNull(call, "call");
        OngoingCall.setCall(null);
    }

}
