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
        //Toast.makeText(context,"sup yo ??",Toast.LENGTH_LONG).show();
        Toast.makeText(context, " WAKE UP NOW ", Toast.LENGTH_SHORT).show();
       /* Uri alarmUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(alarmUri==null)
        {
            alarmUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
         ringtone=RingtoneManager.getRingtone(context,alarmUri);
        //long w=60000;
        ringtone.play();*/


      /* final long t=System.currentTimeMillis();
        if(System.currentTimeMillis()>t+1)
        {
            ringtone.stop();
        }*/
     /* for(long i=0;i<900000000;i++){}
        ringtone.stop();*/
       /* Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(30000);  //30000 is for 30 seconds, 1 sec =1000
                    if (ringtone.isPlaying())
                        ringtone.stop();   // for stopping the ringtone
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();*/
       Intent i=new Intent();
        i.setClassName("com.example.laxmi.button","com.example.laxmi.button.ToSnooze");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);






    }








}
