package com.example.sherryproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.ColorSpace;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ReadNotesActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    TextToSpeech textToSpeech;
    NoteDatabase controller;
    static String lvvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_notes);

        controller=new NoteDatabase(this,"",null,1);

        final ArrayList<HashMap<String, String>> userList = controller.GetUsers();
        final ListView lv = (ListView) findViewById(R.id.list_read_notes);
        ListAdapter adapter = new SimpleAdapter(ReadNotesActivity.this, userList, R.layout.read_notes_item,new String[]{"ID","DATA"}, new int[]{R.id.txt_id,R.id.txt_data});
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvvv=String.valueOf(lv.getItemAtPosition(position));
                Toast.makeText(ReadNotesActivity.this,lvvv.toString(), Toast.LENGTH_SHORT).show();
                textToSpeech33();

            }
        });


        textToSpeech = new TextToSpeech(this, this);
    }

    private void textToSpeech33() {

        String text = lvvv;
        if ("".equals(text)) {
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
        startActivity(new Intent(ReadNotesActivity.this,MainActivity.class));
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
        String text = "You Are In the Read Notes activity press anywhere to read notes.";
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
