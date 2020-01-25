package com.example.sherryproject;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SendSmsActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    TextToSpeech textToSpeech;
    RelativeLayout lin1,lin2,lin3;
    EditText ed1,ed2;
    TextView tv3;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int REQ_CODE_SPEECH_INPUT2 = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);



        lin1=(RelativeLayout) findViewById(R.id.Lin_number);
        lin2=(RelativeLayout) findViewById(R.id.Lin_msg);
        lin3=(RelativeLayout) findViewById(R.id.Lin_send);

        ed1=(EditText)findViewById(R.id.edt_number);
        ed2=(EditText)findViewById(R.id.edt_msg);
        tv3=(TextView) findViewById(R.id.edt_Send);


        textToSpeech = new TextToSpeech(this, this);

        lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeaklin1();
            }
        });
        lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeaklin2();
            }
        });
        lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeaklin3();
            }
        });
        ed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeaklin1();
            }
        });
        ed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeaklin2();
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeaklin3();
            }
        });




        lin1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startVoiceInput1();
                return true;
            }
        });
        lin2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startVoiceInput2();
                return true;
            }
        });
        lin3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int permisioncheck= ContextCompat.checkSelfPermission(SendSmsActivity.this, Manifest.permission.SEND_SMS);

                if (permisioncheck== PackageManager.PERMISSION_GRANTED){

                    try{
                        SmsManager smgr = SmsManager.getDefault();
                        smgr.sendTextMessage(ed1.getText().toString(),null,ed2.getText().toString(),null,null);
                        Toast.makeText(SendSmsActivity.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                        ed1.setText("");
                        ed2.setText("");
                    }
                    catch (Exception e){
                        Toast.makeText(SendSmsActivity.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    ActivityCompat.requestPermissions(SendSmsActivity.this,new String[]{Manifest.permission.SEND_SMS},0);
                }
                return true;
            }
        });
        ed1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startVoiceInput1();
                return true;
            }
        });
        ed2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        tv3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int permisioncheck= ContextCompat.checkSelfPermission(SendSmsActivity.this, Manifest.permission.SEND_SMS);

                if (permisioncheck== PackageManager.PERMISSION_GRANTED){

                    try{
                        SmsManager smgr = SmsManager.getDefault();
                        smgr.sendTextMessage(ed1.getText().toString(),null,ed2.getText().toString(),null,null);
                        Toast.makeText(SendSmsActivity.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                        ed1.setText("");
                        ed2.setText("");
                    }
                    catch (Exception e){
                        Toast.makeText(SendSmsActivity.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    ActivityCompat.requestPermissions(SendSmsActivity.this,new String[]{Manifest.permission.SEND_SMS},0);
                }

                return true;
            }
        });

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SendSmsActivity.this,MainActivity.class));
        finish();
    }

    private void startVoiceInput2() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech Number to make call");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT2);
        } catch (ActivityNotFoundException a) {

        }
    }



    private void startVoiceInput1() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech Number to make call");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    ed1.setText(result.get(0));
                }
                break;
            }

        }
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT2: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    ed2.setText(result.get(0));
                }
                break;
            }

        }



    }

    private void texttoSpeaklin1() {
        String text = "You click on add number place . Long click here to speak number.";
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


    private void texttoSpeaklin2() {
        String text = "You click on add message place . Long click here to speak and type message.";
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


    private void texttoSpeaklin3() {

        String text = "You click on send  sms place . Long click here to send sms.";
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
        String text = "Click anywhere to start Send sms feature";
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode){
            case 0:
                if (grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    try{
                        SmsManager smgr = SmsManager.getDefault();
                        smgr.sendTextMessage(ed1.getText().toString(),null,ed2.getText().toString(),null,null);
                        Toast.makeText(SendSmsActivity.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                        ed1.setText("");
                        ed2.setText("");

                    }
                    catch (Exception e){
                        Toast.makeText(SendSmsActivity.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(this, "You Dont Have Required Permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    }
