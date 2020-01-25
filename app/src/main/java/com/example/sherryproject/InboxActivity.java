package com.example.sherryproject;

import android.Manifest;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InboxActivity extends ListActivity implements TextToSpeech.OnInitListener {


    TextToSpeech textToSpeech;

    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static  String name;
    private static  String phone;
    private static  String message;
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }

        textToSpeech = new TextToSpeech(this, this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }

        List<SmsFormat> List = new ArrayList<SmsFormat>();

        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cur = getContentResolver().query(uri, null, null, null, null);
        startManagingCursor(cur);

        if (cur.moveToFirst()) {
            for (int i = 0; i < cur.getCount(); i++) {
                SmsFormat sms = new SmsFormat();
                if (cur.getColumnIndexOrThrow("body") > -1) {
                    sms.setBody(cur
                            .getString(cur.getColumnIndexOrThrow("body"))
                            .toString());
                }
                if (cur.getColumnIndexOrThrow("address") > -1) {
                    sms.setNumber(cur.getString(
                            cur.getColumnIndexOrThrow("address")).toString());
                }

                String name = null;
                name = Search(cur.getString(
                        cur.getColumnIndexOrThrow("address")).toString());

                if (name != " ") {
                    sms.setName(name);
                    List.add(sms);
                }
                cur.moveToNext();
            }
        }
        cur.close();

        setListAdapter(new ListAdapter(this, List));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted

            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SmsFormat sms = (SmsFormat) getListAdapter().getItem(position);
        Log.i("MSG", sms.getBody());
        name=sms.getName();
        phone=sms.getNumber();
        message=sms.getBody();
        Toast.makeText(this,sms.getName()+" "+sms.getNumber()+" "+sms.getBody(), Toast.LENGTH_SHORT).show();
        textSpeach();
    }

    private void textSpeach() {
        String text = "Sender name is "+name+"Sender number is "+phone+" and the message is "+message;
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

    public String Search(String number) {
        Uri uri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String name = " ";

        ContentResolver contentResolver = getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, new String[] {
                        BaseColumns._ID, ContactsContract.PhoneLookup.DISPLAY_NAME },
                null, null, null);

        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup
                        .getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }

        return name;
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
        String text = "You are on the list of inbox messages. Click any where to hear message detail.";
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
        startActivity(new Intent(InboxActivity.this,MainActivity.class));
        finish();
    }

}
