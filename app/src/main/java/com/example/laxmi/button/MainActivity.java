package com.example.laxmi.button;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import static android.R.attr.data;
import static android.R.attr.timePickerDialogTheme;

public class MainActivity extends AppCompatActivity {
    static final int DIALOG_ID=0;
    int hour;
    int minute;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    String listitem[];
    boolean checkedItems[];
     ArrayList<Integer> userItem=new ArrayList<>();
    String ringtoneitems[];
    static int selected=0;
    String title[];
    Uri address[];
    MediaPlayer mediaPlayer;
    static Uri ringadd;
    static Uri tonePath;
     long time;

    //ArrayList<Integer> ringItem=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        int h=new java.sql.Time(System.currentTimeMillis()).getHours();
        RelativeLayout lay=(RelativeLayout)findViewById(R.id.lay);
        if(h>=3&&h<=4)
        {
            lay.setBackground(getResources().getDrawable(R.mipmap.ear));
            toolbar.setBackground(getResources().getDrawable(R.mipmap.ear2));
        }
        else if(h>=5&&h<=7)
        {
            lay.setBackground(getResources().getDrawable(R.mipmap.h));
            toolbar.setBackground(getResources().getDrawable(R.mipmap.h2));
        }
        else if(h>=8&&h<=11)
        {
            lay.setBackground(getResources().getDrawable(R.mipmap.mor));
            toolbar.setBackground(getResources().getDrawable(R.mipmap.mor2));
        }
        else if(h>=12&&h<=15)
        {
            lay.setBackground(getResources().getDrawable(R.mipmap.mor));
            toolbar.setBackground(getResources().getDrawable(R.mipmap.mor2));
        }
        else if(h>=16&&h<=17)
        {
            lay.setBackground(getResources().getDrawable(R.mipmap.evening));
            toolbar.setBackground(getResources().getDrawable(R.mipmap.evening2));
        }
        else if(h>=18&&h<=19)
        {
            lay.setBackground(getResources().getDrawable(R.mipmap.dusk));
            toolbar.setBackground(getResources().getDrawable(R.mipmap.dusk2));
        }
        else
        {
            lay.setBackground(getResources().getDrawable(R.mipmap.nig));
            toolbar.setBackground(getResources().getDrawable(R.mipmap.nig2));


        }











        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
       // String print[]={  "     Time","     Repeat","     Ringtone","     How to turn the alarm off"};
        String print[]={  "Time","Repeat","Ringtone","Turn Off Method"};
        int images[]={R.drawable.clocks,R.drawable.repeat,R.drawable.ring1,R.drawable.maths};//n

        GridView myListView=(GridView)findViewById(R.id.myListView);
        CustomerAdapter myAdapter=new CustomerAdapter(this,print,images);//n


        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                      switch(position)
                      {
                          case 0:
                          { showDialog(DIALOG_ID);

                          }
                              break;
                          case 1:
                          {
                              RepeatSelect();

                          }
                          break;
                          case 2:
                          {
                              RingTone();
                          }
                          break;

                          case 3:
                             // okayset();



                      }






                    }
                }


        );





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==DIALOG_ID)
            return new TimePickerDialog(MainActivity.this, timePickerListerner,hour,minute,false);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener timePickerListerner=new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            hour = hourOfDay;
            int duph = hour;
            minute = minutes;
            int dupm = minute;
            String amppm = "";
            String minstr = "";
            if (hour > 12) {
                amppm = "PM";
                duph = duph - 12;
            } else
                amppm = "AM";
            if (minute < 10)
                minstr = "0" + Integer.valueOf(dupm);
            else
                minstr = Integer.valueOf(dupm) + "";
            String diaplay = "Alarm set to :" + Integer.valueOf(duph) + ":" + minstr + " " + amppm;


            Toast.makeText(MainActivity.this, diaplay, Toast.LENGTH_LONG).show();

            // long time;//lc
            if (Build.VERSION.SDK_INT < 23 || Build.VERSION.SDK_INT >= 23) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                int h = new Time(System.currentTimeMillis()).getHours();
                int m = new Time(System.currentTimeMillis()).getMinutes();
                // Toast.makeText(getApplicationContext(),""+h+"  "+m+"  "+hour,Toast.LENGTH_LONG).show();
                if (hour == h)
                    h = 0;
                else if (h == 0 && hour <= 12)
                    h = hour;
                else if (h == 0 && hour > 12)
                    h = 12 + hour;
                else if (hour == 0 && h > 12)
                    h = 24 - h;
                else if (hour == 0 && h < 12)
                    h = 24 - h;
                else if (hour - 12 == h || h - 12 == hour)
                    h = 12;
                else if (h >= 12 && hour < 12)
                    h = 24 - h + hour;
                else if (h < 12 && hour > 12)
                    h = 12 - h + hour;
                else if (h > 12 && hour > 12) {
                    if (hour > h)
                        h = hour - h;
                    else
                        h = 24 - hour;
                } else if (h < 12 && hour < 12) {
                    if (hour > h)
                        h = hour - h;
                    else
                        h = 24 - hour;
                }


                m = 60 - m + minutes;
                if (m > 60) {
                    // h++;
                    m = m - 60;
                } else if (m < 60) {
                    h--;

                } else if (m == 60)
                    m = 0;
                String hmi = "alarm will ring in " + Integer.toString(h) + " hours and " + Integer.toString(m) + " minutes";
                Toast.makeText(getApplicationContext(), hmi, Toast.LENGTH_LONG).show();

                //Intent intent=new Intent(MainActivity.this,AlarmReceiver.class);//lcccc

                //pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,0);//lccc
                time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                if (System.currentTimeMillis() > time) {
                    if (Calendar.AM_PM == 0)
                        time = time + (1000 * 60 * 60 * 12);
                    else
                        time = time + (1000 * 60 * 60 * 24);
                }

                /// alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,pendingIntent);//lccc
                // for(int i=0;i<60000000;i++){}

                //  alarmManager.cancel(pendingIntent);


            }


        }
    };






    public void RepeatSelect()
    {


        listitem=getResources().getStringArray(R.array.Repeat);
        checkedItems=new boolean[listitem.length];
        AlertDialog.Builder mybuilder=new AlertDialog.Builder(MainActivity.this);
        mybuilder.setTitle(R.string.R);
        //mybuilder.set
        mybuilder.setMultiChoiceItems(listitem, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if(isChecked){
                    if(!userItem.contains(position))
                    {
                        userItem.add(position);
                    }
                    else
                    {
                        userItem.remove(position);

                    }
                }
            }
        });
        mybuilder.setCancelable(false);
        mybuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item="";
                for(int i=0;i<userItem.size();i++)
                {
                    item=item+listitem[userItem.get(i)]+" ";
                }
                Toast.makeText(MainActivity.this,getString(R.string.RepeatOn)+item,Toast.LENGTH_LONG).show();

            }
        });

        mybuilder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        mybuilder.setNeutralButton(R.string.clear, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               for (int i=0;i<checkedItems.length;i++)
               {
                   checkedItems[i]=false;
                   userItem.clear();
                   Toast.makeText(MainActivity.this, R.string.cleared,Toast.LENGTH_LONG).show();

               }
            }
        });

        AlertDialog mDia=mybuilder.create();
        mDia.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == 3){
            Uri toneUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            //RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION, toneUri);
            if(toneUri!=null){
                tonePath = toneUri;
                //Log.i("tonepath", tonePath);
            }
        }
    }

    public  void RingTone()
    {
        final int[] check = {0};

        int arr[]={R.raw.ring1,R.raw.ring2,R.raw.ring3,R.raw.ring};


        ringtoneitems=getResources().getStringArray(R.array.Ringtones);
        AlertDialog.Builder mybuilder=new AlertDialog.Builder(MainActivity.this);
        mybuilder.setTitle("RingTones");
        mybuilder.setSingleChoiceItems(ringtoneitems, check[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
              if(which==4)
              {
                  Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                  intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
                  intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "CHOOSE NOTIFICATION SOUND");
                  intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri)null);
                  startActivityForResult(intent, 3);




              }




            }
        });

        mybuilder.setCancelable(false);
        mybuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selected= ((AlertDialog)dialog).getListView().getCheckedItemPosition();
               // Toast.makeText(MainActivity.this,"the ringtone selected is: "+selected+1,Toast.LENGTH_LONG).show();
                //dialog.
                //random typing cause i updated to the wrong branch XD
            }
        });

        mybuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog mDia=mybuilder.create();
        mDia.show();

       




    }
    public static int forselected()
    {

        if(selected==0)
           return R.raw.ring1;
        else if(selected==1)
            return R.raw.ring2;
        else if(selected==2)
            return R.raw.ring3;
        else if(selected==3)
            return R.raw.ring;
        else
            return -999;



    }
    public static Uri ringi()
    {
        return tonePath ;
    }

    public void okayset(View view)
    {
        //Button ok=(Button)findViewById(R.id.ok);
        Toast.makeText(MainActivity.this,String.valueOf(time),Toast.LENGTH_LONG).show();
        if(userItem.size()==0)
        {
            Intent intent=new Intent(MainActivity.this,AlarmReceiver.class);
            pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,pendingIntent);
        }
        else
        {
            Intent intent=new Intent(MainActivity.this,AlarmReceiver.class);
            pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        }

    }






    public void cancel(View view)
    {
        //Button cancel=(Button)findViewById(R.id.Cancel);
        alarmManager.cancel(pendingIntent);
    }





}
