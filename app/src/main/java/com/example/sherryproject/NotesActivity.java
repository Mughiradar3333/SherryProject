package com.example.sherryproject;

import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Locale;

public class NotesActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    LinearLayout lin1,lin2;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        lin1=(LinearLayout)findViewById(R.id.lin1_notes);
        lin2=(LinearLayout)findViewById(R.id.lin2_notes);
        textToSpeech = new TextToSpeech(this, this);



        lin2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(NotesActivity.this,ReadNotesActivity.class));
                finish();
                return true;
            }
        });


        lin1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(NotesActivity.this,AddNotesActivity.class));
                finish();
                return true;
            }
        });

        lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             speak1();
            }
        });
        lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak2();
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NotesActivity.this,MainActivity.class));
        finish();
    }

    private void speak2() {

        String text = "You Click on go to read notes activity. Press long click here to read notes";
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

    private void speak1() {
        String text = "You Click on go to add notes activity. Press long click here to Add new notes";
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
        String text = "Sheharyar Rafiq You are in the notes activity";
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
