package com.example.mushroomtechnologymobile;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);
        if(status.isEmpty()) {
            status="No Internet Connection";
            test(status,context);
        }
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
    public void test(final String data, final Context ctx){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
        alertDialog.setMessage(data);
        alertDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
