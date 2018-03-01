package com.example.laxmi.button;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import static android.R.id.content;

public class AlarmReceiver extends BroadcastReceiver  {

    //Ringtone ringtone;
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, " WAKE UP NOW ", Toast.LENGTH_SHORT).show();

       Intent i=new Intent();
        i.setClassName("com.example.laxmi.button","com.example.laxmi.button.ToSnooze");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);






    }








}
