package com.example.laxmi.button;

import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static com.example.laxmi.button.MainActivity.forselected;

public class ToSnooze extends AppCompatActivity {

    Ringtone ringtone;
    MediaPlayer mediaPlayer;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_to_snooze);
        tocall();

    }

    public void tocall()
    {
      //  Toast.makeText(this,"tocall",Toast.LENGTH_LONG).show();
      //  Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        int k=forselected();
        Toast.makeText(getApplicationContext()," "+k,Toast.LENGTH_SHORT).show();
         mediaPlayer=MediaPlayer.create(getApplicationContext(),k);
        mediaPlayer.start();

        int hour=new java.sql.Time(System.currentTimeMillis()).getHours();
        int min=new java.sql.Time(System.currentTimeMillis()).getMinutes();
        String amppm="";
        String minstr="";
        if(hour>12)
        {
            amppm="PM";
            hour=hour-12;
        }
        else
            amppm="AM";
        if(min<10)
            minstr="0"+Integer.valueOf(min);
        else
            minstr=Integer.valueOf(min)+"";

        time=Integer.valueOf(hour)+":"+minstr+" "+amppm;

       /* if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(this, alarmUri);
        ringtone.stop();*/
      // mediaPlayer.prepare();
       mediaPlayer.pause();
        solve();
    }

    private void solve()
    {

        //Toast.makeText(this,"solve",Toast.LENGTH_LONG).show();
       // ringtone.play();
        mediaPlayer.start();
        mediaPlayer.setLooping(true);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_to_snooze);
        TextView currenttime=(TextView)findViewById(R.id.currentime) ;
        currenttime.setText("Time:"+time);


        Random r1=new Random();
        int n1=r1.nextInt(99-10)+10;
        int n2=r1.nextInt(99-10)+10;
        int n3=r1.nextInt(99-10)+10;
        String problem=Integer.toString(n1)+"*"+Integer.toString(n2)+"+"+Integer.toString(n3)+"=";
        int sum=n1*n2+n3;

        TextView p=(TextView)findViewById(R.id.problem);
        p.setText("SOLVE:"+problem);


        Button mybutton=(Button)findViewById(R.id.mybutton);
        final EditText input=(EditText)findViewById(R.id.input);
        //  if(input.is)
        //  final Editable str= input.getText();
        final String ans=Integer.toString(sum);



        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String str= input.getText().toString();
                if(str.equals(ans))
                {
                    //ringtone.stop();
                    mediaPlayer.stop();
                    finish();
                }
                else
                {
                    Toast.makeText(ToSnooze.this,"wrong answer ,try again",Toast.LENGTH_LONG).show();
                   // ringtone.stop();

                    mediaPlayer.pause();

                    tocall();
                }





                //  finish();
            }
        });
    }
}
