package com.example.ivanlauncher.call;

import android.content.Intent;
import android.telecom.Call;
import android.telecom.InCallService;

import com.example.ivanlauncher.R;
import com.example.ivanlauncher.contacts.ContactsLoader;
import com.example.ivanlauncher.ui.TextReader;


public final class CallService extends InCallService {
    public void onCallAdded(Call call) {
//        Intrinsics.checkParameterIsNotNull(call, "call");
        OngoingCall.setCall(call);

        Intent i = new Intent(this, CallActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String number = call.getDetails().getHandle().getSchemeSpecificPart();
        i.putExtra("number", ContactsLoader.getContact(number) );

        startActivity(i);
    }

    public void onCallRemoved(Call call) {
//        Intrinsics.checkParameterIsNotNull(call, "call");
        TextReader.read(getApplicationContext().getString(R.string.closedConnection));
        OngoingCall.setCall(null);
    }


}
