package com.example.ivanlauncher.call;

import android.content.Intent;
import android.telecom.Call;
import android.telecom.InCallService;

import com.example.ivanlauncher.R;
import com.example.ivanlauncher.contacts.ContactsProvider;
import com.example.ivanlauncher.ui.TextReader;
import com.example.ivanlauncher.ui.elements.Contact;

import java.util.ArrayList;
import java.util.List;


public final class CallService extends InCallService {
    public void onCallAdded(Call call) {
//        Intrinsics.checkParameterIsNotNull(call, "call");
        OngoingCall.setCall(call);

        Intent i = new Intent(this, CallActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String number = call.getDetails().getHandle().getSchemeSpecificPart();
        i.putExtra("number", getContact(number) );

        startActivity(i);
    }

    public void onCallRemoved(Call call) {
//        Intrinsics.checkParameterIsNotNull(call, "call");
        TextReader.read(getApplicationContext().getString(R.string.closedConnection));
        OngoingCall.setCall(null);
    }


    private String getContact(String number) {
        List<Contact> contactList =new ArrayList<>( ContactsProvider.getContacts(getApplicationContext()));
        if (contactList.size() == 0) {
            return number;
        }
        if (number.length() > 8) {
            number = number.substring(number.length() - 8);
        }

        for (Contact contact : contactList) {
            if (contact.getPhoneNumber().replaceAll(" ","").endsWith(number)) {
                return contact.getName();
            }
        }

        return number;
    }

}
