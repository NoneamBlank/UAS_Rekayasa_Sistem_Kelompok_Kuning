package com.example.kyancafe.foodorder;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (isOnline(context)){

                Toast.makeText(context, "Internet Connected", Toast.LENGTH_SHORT).show();
            }
            else {
                final Dialog dialog = new Dialog(context, android.R.style.Theme_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.connection_dialog);
                dialog.show();
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public boolean isOnline(Context context){
        try{
            ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo!=null && networkInfo.isConnected());
        } catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }
}
