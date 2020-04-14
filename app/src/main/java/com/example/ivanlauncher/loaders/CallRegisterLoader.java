package com.example.ivanlauncher.loaders;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import com.example.ivanlauncher.ui.elements.CallLogDetail;
import com.example.ivanlauncher.ui.elements.RegisterMenuElementType;

import java.util.ArrayList;
import java.util.List;

import static com.example.ivanlauncher.ui.elements.RegisterMenuElementType.DAILED;
import static com.example.ivanlauncher.ui.elements.RegisterMenuElementType.MISSED;
import static com.example.ivanlauncher.ui.elements.RegisterMenuElementType.RECEIVED;

public class CallRegisterLoader {


    public static  List<CallLogDetail>  realodAllCallHistory(Context activity, RegisterMenuElementType requestedType) {

        ArrayList<CallLogDetail> callLog = new ArrayList<>();

        ContentResolver cr = activity.getContentResolver();

        Uri allCalls = Uri.parse("content://call_log/calls");//todo - optimize
        Cursor cur = activity.getContentResolver().query(allCalls, null, null, null, null);

        if (cur == null || cur.getCount() == 0) {
            if (cur != null) {
                cur.close();
            }
        }

        while (cur.moveToNext()) {
            String num= cur.getString(cur.getColumnIndex(CallLog.Calls.NUMBER));// for  number
            String name= cur.getString(cur.getColumnIndex(CallLog.Calls.CACHED_NAME));// for name
            int duration = Integer.parseInt(cur.getString(cur.getColumnIndex(CallLog.Calls.DURATION)));// for duration
            long date = Long.parseLong(cur.getString(cur.getColumnIndex(CallLog.Calls.DATE)));// for duration
            int type = Integer.parseInt(cur.getString(cur.getColumnIndex(CallLog.Calls.TYPE)));// for call type, Incoming or out going.

            CallLogDetail detail = new CallLogDetail(num ,name, duration, date,type);

            if(type == CallLog.Calls.INCOMING_TYPE && requestedType == RECEIVED){
                callLog.add(detail);
            } else if(type == CallLog.Calls.OUTGOING_TYPE&&requestedType == DAILED){
                callLog.add(detail);
            } else if(type == CallLog.Calls.MISSED_TYPE&&requestedType == MISSED){
                callLog.add(detail);
            }


        }
        cur.close();

        return callLog;
    }

}
