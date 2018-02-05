package com.example.laxmi.button;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Handler;

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
            lay.setBackground(getResources().getDrawable(R.mipmap.noon));
            toolbar.setBackground(getResources().getDrawable(R.mipmap.noon2));
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
        String print[]={  "     Time","     Repeat","     Ringtone","     How to turn the alarm off"};
        ListAdapter myAdapter=new CustomerAdapter(this,print);
        


        ListView myListView=(ListView)findViewById(R.id.myListView);

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
            return new TimePickerDialog(MainActivity.this,timePickerListerner,hour,minute,false);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener timePickerListerner=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            hour=hourOfDay;
            int duph=hour;
            minute=minutes;
            int dupm=minute;
            String amppm="";
            String minstr="";
            if(hour>12)
            {
                amppm="PM";
                duph=duph-12;
            }
            else
                amppm="AM";
            if(minute<10)
                minstr="0"+Integer.valueOf(dupm);
            else
                minstr=Integer.valueOf(dupm)+"";
            String diaplay="Alarm set to :"+Integer.valueOf(duph)+":"+minstr+" "+amppm;


            Toast.makeText(MainActivity.this,diaplay,Toast.LENGTH_LONG).show();

            long time;
            if(Build.VERSION.SDK_INT < 23)
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE,minute);
               // Toast.makeText(MainActivity.this,mytime.getCurrentHour()+" "+mytime.getCurrentMinute(),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this,AlarmReceiver.class);

                pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
                time=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
                if(System.currentTimeMillis()>time)
                {
                    if(Calendar.AM_PM ==0)
                        time = time + (1000*60*60*12);
                    else
                        time = time + (1000*60*60*24);
                }
               // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time,/*7*24*60*60**/10000,pendingIntent);
                //alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,/*7*24*60*60**/10000,pendingIntent);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,pendingIntent);
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

    public  void RingTone()
    {
        final int[] check = {0};

        int arr[]={R.raw.ring1,R.raw.ring2,R.raw.ring3,R.raw.ring};


        ringtoneitems=getResources().getStringArray(R.array.Ringtones);
        AlertDialog.Builder mybuilder=new AlertDialog.Builder(MainActivity.this);
        mybuilder.setTitle("RingTones");
        mybuilder.setSingleChoiceItems(ringtoneitems, check[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this," "+which,Toast.LENGTH_SHORT).show();
                //final MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),arr[which]);
              //  mediaPlayer.start();
              //  for(int j=0;j<90000000;j++);
               // if(mediaPlayer.)

               // mediaPlayer.stop();



               // check[0]=((AlertDialog)dialog).getListView().getCheckedItemPosition();
               // Toast.makeText(MainActivity.this,"the ringtoneeee selected is:"+check[0],Toast.LENGTH_LONG).show();




            }
        });
        mybuilder.setCancelable(false);
        mybuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selected= ((AlertDialog)dialog).getListView().getCheckedItemPosition();
               Toast.makeText(MainActivity.this,"the ringtone selected is:"+selected,Toast.LENGTH_LONG).show();
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
        else
            return R.raw.ring;



    }


}
