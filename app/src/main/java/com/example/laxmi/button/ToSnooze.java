package com.example.laxmi.button;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import static com.example.laxmi.button.MainActivity.forselected;
import static com.example.laxmi.button.MainActivity.ringi;

public class ToSnooze extends AppCompatActivity {

    Ringtone ringtone;
    MediaPlayer mediaPlayer;
    String time;

   private boolean isBtConnected = false;

    private BluetoothAdapter mybluetooth=null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS="device_address";
    String HCname="";
    String HCaddress="";
    private ProgressDialog progress;
    BluetoothSocket btSocket=null;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mybluetooth=BluetoothAdapter.getDefaultAdapter();//get the default bluetooth adapter in the device
        if(mybluetooth==null)//check if the device has a bluetooth service
        {
            Toast.makeText(getApplicationContext(),"Bluetooth device not available",Toast.LENGTH_LONG).show();
            finish();
        }
        else if(!mybluetooth.isEnabled())//if present then check if it is on
        {
            mybluetooth.enable();//switch on the bluetooth without user interaction
            Toast.makeText(this,"Bluetooth enabled",Toast.LENGTH_LONG).show();
            // pairedDevises();
        }
        for(long i=0;i<1000000000;i++)
        {
            //for some reason to get the bonded devices a delay is required,this loop is to provide that delay xD
        }
        pairedDevices=mybluetooth.getBondedDevices();


        if(pairedDevices.size()>0)
        {
          //  Toast.makeText(this,"Getting paired devices",Toast.LENGTH_LONG).show();
            for(BluetoothDevice bt:pairedDevices)
            {
                String btname=bt.getName();
                String btadd=bt.getAddress();
                if(btname.equals("HC-05"))//get the address of the bluetooth module used.I used HC-05 :)
                {
                    HCname=btname;
                    HCaddress=btadd;
                    onBluetooth();

                }
            }

        }

        else
        {
            Toast.makeText(getApplicationContext(),"No paired devices",Toast.LENGTH_LONG).show();
            tocall();
        }
        //tocall();
        if(HCname.equals(""))
            tocall();

    }


   private void onBluetooth()
   {
       Toast.makeText(this,HCaddress,Toast.LENGTH_LONG).show();
       new ConnectBT().execute();
   }

    public void tocall()
    {

        int k=forselected();

        if(k==-999)//n
        {
            Uri ring = ringi();
            mediaPlayer=MediaPlayer.create(getApplicationContext(),ring);
            mediaPlayer.start();
        }
        else//n
        {
            int ring=k;
            mediaPlayer=MediaPlayer.create(getApplicationContext(),k);
            mediaPlayer.start();
        }


        if(btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TO".toString().getBytes());//sends intruction to arduino to switch on led via bluetooth
            }
            catch (IOException e)
            {
                Toast.makeText(ToSnooze.this,"error",Toast.LENGTH_LONG).show();
            }
        }

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


       mediaPlayer.pause();
        solve();
    }

    private void solve()
    {


        mediaPlayer.start();
        mediaPlayer.setLooping(true);





        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_to_snooze);
        TextView currenttime=(TextView)findViewById(R.id.currentime) ;
        currenttime.setText("Time:"+time);


        Random r1=new Random();
        int n1=r1.nextInt(10-1)+1;
        int n2=r1.nextInt(10-1)+1;
        int n3=r1.nextInt(10-1)+1;
        String problem=Integer.toString(n1)+"*"+Integer.toString(n2)+"+"+Integer.toString(n3)+"=";
        int sum=n1*n2+n3;

        TextView p=(TextView)findViewById(R.id.problem);
        p.setText("SOLVE:"+problem);


        final Button mybutton=(Button)findViewById(R.id.mybutton);
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

                    if(btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("TF".toString().getBytes());//sends instruction to arduino via bluetooth to switch off
                        }
                        catch (IOException e)
                        {
                            Toast.makeText(ToSnooze.this,"error",Toast.LENGTH_LONG).show();
                        }


                    }
                    mybluetooth.disable();//n
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


   private class ConnectBT extends AsyncTask<Void,Void,Void>//this class basically switches on the required bluetooth module
   {
       private boolean ConnectSuccess=true;

       @Override
       protected void onPreExecute()
       {
           // super.onPreExecute();
           progress=ProgressDialog.show(ToSnooze.this,"Connecting","Please wait");


       }

       @Override
       protected Void doInBackground(Void... devices)
       {
           try
           {
               if(btSocket==null||!isBtConnected)
               {
                   mybluetooth=BluetoothAdapter.getDefaultAdapter();
                   BluetoothDevice dispositivo=mybluetooth.getRemoteDevice(HCaddress);
                   btSocket=dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                   BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                   btSocket.connect();

               }

           }
           catch (IOException e)
           {
               ConnectSuccess=false;
           }
           return null;
       }

       @Override
       protected void onPostExecute(Void aVoid) {
           super.onPostExecute(aVoid);
           if(!ConnectSuccess)
           {
               Toast.makeText(ToSnooze.this,"Connection failed,sorry:(",Toast.LENGTH_LONG).show();
               mybluetooth.disable();
               btSocket=null;
               tocall();
               //finish();
           }
           else
           {
               Toast.makeText(ToSnooze.this,"Connected to "+HCname,Toast.LENGTH_LONG).show();
               isBtConnected=true;
               tocall();
           }
           progress.dismiss();
       }
   }
}
