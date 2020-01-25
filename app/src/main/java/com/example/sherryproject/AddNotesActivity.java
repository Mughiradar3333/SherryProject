package com.example.sherryproject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Locale;

public class AddNotesActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    TextToSpeech textToSpeech33;
    RelativeLayout rl_addnotes,rll_addnotes;
    EditText edit_notes;
    private static final int REQ_CODE_SPEECH_INPUT = 101;
    NoteDatabase controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        rl_addnotes=(RelativeLayout) findViewById(R.id.add_notes_rel);
        edit_notes=(EditText)findViewById(R.id.edit_add_notes);
        rll_addnotes=(RelativeLayout) findViewById(R.id.rel_2_addnotes);
        textToSpeech33 = new TextToSpeech(this, this);
        controller=new NoteDatabase(this,"",null,1);


        rl_addnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeak3333();
            }
        });

        rl_addnotes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startVoiceInput();
                return true;
            }
        });

        rll_addnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeak333333();

            }
        });

        rll_addnotes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (edit_notes.getText().toString().isEmpty()){
                    texttoSpeak44();
                }else {
                    controller.insert_notes(edit_notes.getText().toString().trim());
                    startActivity(new Intent(AddNotesActivity.this,MainActivity.class));
                    finish();
                }
                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddNotesActivity.this,MainActivity.class));
        finish();
    }

    private void texttoSpeak44() {
        String text = "Please Enter Something to save in database.";
        if ("".equals(text)) {
            text = "Please enter some text to speak.";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech33.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            textToSpeech33.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void texttoSpeak333333() {
        String text = "Are you sure to add the notes.If sure kindly long press here.";
        if ("".equals(text)) {
            text = "Please enter some text to speak.";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech33.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            textToSpeech33.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please Speak Something To Enter In The Database");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    private void texttoSpeak3333() {
        String text = "You clicked on write note place. Press long click here to add text through speak";
        if ("".equals(text)) {
            text = "Please enter some text to speak.";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech33.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            textToSpeech33.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech33.setLanguage(Locale.US);
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
        String text = "You Are In the Add Notes activity press anywhere to perform action";
        if ("".equals(text)) {
            text = "Please enter some text to speak.";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech33.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            textToSpeech33.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edit_notes.setText(result.get(0));
                }
                break;
            }

        }
    }


}
