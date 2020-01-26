package com.example.sherryproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MessagesActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{


    TextToSpeech textToSpeech;
    RelativeLayout l1,l2;
    TextView l111,l222;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        l1=(RelativeLayout) findViewById(R.id.mLin1);
        l2=(RelativeLayout) findViewById(R.id.mLin2);
        l111=(TextView)findViewById(R.id.mLin11);
        l222=(TextView)findViewById(R.id.mLin22);



        l1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MessagesActivity.this,SendSmsActivity.class));
                return true;
            }
        });
        l2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MessagesActivity.this,InboxActivity22.class));
                return true;
            }
        });
        l111.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MessagesActivity.this,SendSmsActivity.class));
                return true;
            }
        });
        l222.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MessagesActivity.this,InboxActivity22.class));
                return true;
            }
        });

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            speakSend();
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakInbox();
            }
        });
        l111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakSend();
            }
        });
        l111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakInbox();
            }
        });


        textToSpeech = new TextToSpeech(this, this);
    }

    private void speakInbox() {

        String text = "You click on inbox activity button. Long click here to go to inbox activity";
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MessagesActivity.this,MainActivity.class));
        finish();
    }

    private void speakSend() {
        String text = "You click on send sms activity button. Long click here to go to send sms activity";
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

        String text = "You are on the message activity. Press any where to perform action.";
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
