package com.example.sherryproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.*;

public class HomeActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextToSpeech textToSpeech;
    private String provider;
    private static  final int REQUEST_LOCATION=1;

    Button getlocationBtn;
    TextView showLocationTxt;

    LocationManager locationManager;
    String latitude,longitude;

    RelativeLayout message_img, call_img, battery_img, notes_img, camera_img, time_img, location_img,date_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        message_img = (RelativeLayout) findViewById(R.id.messages);
        call_img = (RelativeLayout) findViewById(R.id.call);
        battery_img = (RelativeLayout) findViewById(R.id.battery);
        notes_img = (RelativeLayout) findViewById(R.id.notes);


        camera_img = (RelativeLayout) findViewById(R.id.camera);
        date_img = (RelativeLayout) findViewById(R.id.date);
        time_img = (RelativeLayout) findViewById(R.id.time);
        location_img = (RelativeLayout) findViewById(R.id.location);


        textToSpeech = new TextToSpeech(this, this);


        call_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(HomeActivity.this, CallActivity.class));
                return true;
            }
        });
        camera_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(HomeActivity.this, CameraActivity.class));
                return true;
            }
        });

        notes_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(HomeActivity.this, NotesActivity.class));
                return true;
            }
        });


        battery_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(HomeActivity.this, BatteryActivity.class));
                return true;
            }
        });


        message_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(HomeActivity.this, MessagesActivity.class));
                return true;
            }
        });

        date_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
                String formattedDate = df.format(c);


                String text = "Current date is"+String.valueOf(formattedDate);
                if ("".equals(text)) {
                    text = "Please enter some text to speak.";
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                else {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }


                return true;
            }
        });


        location_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                ActivityCompat.requestPermissions(HomeActivity.this,new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //Write Function To enable gps

                    OnGPS();
                }
                else
                {
                    //GPS is already On then

                    getLocation();
                }


                return true;
            }
        });

        time_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Date d=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
                String currentDateTimeString = sdf.format(d);

                String text = "Current time is"+String.valueOf(currentDateTimeString);
                if ("".equals(text)) {
                    text = "Please enter some text to speak.";
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                else {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
                return true;

            }
        });


        call_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeakCall();
            }
        });

        camera_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeakCamera();
            }
        });


        notes_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeakNote();
            }
        });


        message_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeakMessage();
            }
        });

        date_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeakDate();
            }
        });


        battery_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeakBattery();
            }
        });


        time_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeakTime();
            }
        });

        location_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeakLocation();
            }
        });

    }

    private void texttoSpeakDate() {
        String text = "You Clicked on date button. Press Long click here to know your current date and month.";
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


    private void getLocation() {

        //Check Permissions again


        if (ActivityCompat.checkSelfPermission(HomeActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {




            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();
                getAddress(lat,longi);
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();
                getAddress(lat,longi);


            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();
                getAddress(lat,longi);

            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
                String text = "Something went wrong please try again.";
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
        else
        {

            Toast.makeText(this, "Something went wrong. Please check your internet connection", Toast.LENGTH_SHORT).show();
            String text = "Something went wrong please try again. Please check your internet connection";
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


            //Thats All Run Your App
        }

    }
    private void OnGPS() {

        String text = "Your location setting is off please first on your location or gps setting.";
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
    private void texttoSpeakLocation() {
        String text = "You Clicked on location button. Press Long click here to know your current location.";
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

    private void  texttoSpeakTime() {

        String text = "You Clicked on time button. Press Long click here to know time.";
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

    private void texttoSpeakCamera() {

        String text = "You Clicked on camera button. Press Long click here to go to camera activity.";
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

    private void texttoSpeakCall() {
        String text = "You Clicked on call button. Press Long click here to go to call activity.";
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

    private void texttoSpeakNote() {
        String text = "You Clicked on note button. Press Long click here to go to notes activity.";
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

    private void texttoSpeakBattery() {
        String text = "You Clicked on battery button. Press Long click here to go to Battery activity.";
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

    private void texttoSpeakMessage() {
        String text = "You Clicked on message button. Press Long click here to go to message activity.";
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
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }




    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();


            String country = obj.getCountryName();
            String addre= obj.getAddressLine(0);
            String city = obj.getLocality();

            String totalAdr = "Your country is" +country +" your city is "+city + " your addresss is "+addre;

            String text = totalAdr;
            if ("".equals(text)) {
                text = "Please enter some text to speak.";
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
            else {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        String text = "You are on the home page. Press any where to perform some action";

        if ("".equals(text))
        {
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
