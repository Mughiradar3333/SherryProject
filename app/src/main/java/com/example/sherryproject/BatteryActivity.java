package com.example.sherryproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

public class BatteryActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {


    //Changing
    TextView textview;
    RelativeLayout fullLayout;
    IntentFilter intentfilter;
    int deviceStatus;
    String currentBatteryStatus="Battery Info";
    int batteryLevel;




    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        textToSpeech = new TextToSpeech(this, this);


        fullLayout=(RelativeLayout)findViewById(R.id.batter_rela);
        textview = (TextView)findViewById(R.id.textViewBatteryStatus);


        intentfilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);



        fullLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BatteryActivity.this.registerReceiver(broadcastreceiver,intentfilter);
                return true;
            }
        });

    }


    private BroadcastReceiver broadcastreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            deviceStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryLevel=(int)(((float)level / (float)scale) * 100.0f);

            if(deviceStatus == BatteryManager.BATTERY_STATUS_CHARGING){

                textview.setText(currentBatteryStatus+" = Charging at "+batteryLevel+" %");
                String text =textview.getText().toString();
                if ("".equals(text)) {
                    text = "Please enter some text to speak.";
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                else {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            if(deviceStatus == BatteryManager.BATTERY_STATUS_DISCHARGING){

                textview.setText(currentBatteryStatus+" = Discharging at "+batteryLevel+" %");
                String text =textview.getText().toString();
                if ("".equals(text)) {
                    text = "Please enter some text to speak.";
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                else {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            if (deviceStatus == BatteryManager.BATTERY_STATUS_FULL){

                textview.setText(currentBatteryStatus+"= Battery Full at "+batteryLevel+" %");
                String text =textview.getText().toString();
                if ("".equals(text)) {
                    text = "Please enter some text to speak.";
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                else {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            if(deviceStatus == BatteryManager.BATTERY_STATUS_UNKNOWN){

                textview.setText(currentBatteryStatus+" = Unknown at "+batteryLevel+" %");
                String text =textview.getText().toString();
                if ("".equals(text)) {
                    text = "Please enter some text to speak.";
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                else {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }


            if (deviceStatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING){

                textview.setText(currentBatteryStatus+" = Not Charging at "+batteryLevel+" %");
                String text =textview.getText().toString();
                if ("".equals(text)) {
                    text = "Please enter some text to speak.";
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                else {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }

        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BatteryActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("error", "This Language is not supported");
            } else {
                texttoSpeak();
            }
        } else {
            Log.e("error", "Failed to Initialize");
        }
    }


    private void texttoSpeak() {
        String text = "You are in the Battery activity. Press long click anywhere to get battery information.";
        if ("".equals(text)) {
            text = "Please enter some text to speak.";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
