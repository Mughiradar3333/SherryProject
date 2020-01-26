package com.example.sherryproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sherryproject.Helper.InternetCheck;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel;
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabelDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class CameraActivity extends AppCompatActivity  implements TextToSpeech.OnInitListener{

    CameraView cameraView;
    Button btnDetect;
    AlertDialog waitingDialog;
    TextToSpeech textToSpeech;


    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        textToSpeech = new TextToSpeech(this, this);

        cameraView=(CameraView)findViewById(R.id.camera_view);
        btnDetect=(Button)findViewById(R.id.btn_detect);
        waitingDialog = new SpotsDialog.Builder().setContext(this).setMessage("Please Waiting").setCancelable(false).build();

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                waitingDialog.show();
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap,cameraView.getWidth(),cameraView.getHeight(),false);
                cameraView.stop();

                runDetector(bitmap);

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.start();
                cameraView.captureImage();
            }
        });

    }

    private void runDetector(Bitmap bitmap) {

        final FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        new InternetCheck(new InternetCheck.Consumer() {
            @Override
            public void accept(Boolean internet) {

                if (internet){
                    waitingDialog.dismiss();
                    FirebaseVisionCloudDetectorOptions options = new FirebaseVisionCloudDetectorOptions.Builder().setMaxResults(1).build();
                    FirebaseVisionCloudLabelDetector detector = FirebaseVision.getInstance().getVisionCloudLabelDetector(options);

                    detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionCloudLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionCloudLabel> firebaseVisionCloudLabels) {
//                            processDataResult(firebaseVisionCloudLabels);
                            waitingDialog.dismiss();
                            for (FirebaseVisionCloudLabel label : firebaseVisionCloudLabels){
                                Toast.makeText(CameraActivity.this, "Firebase Label "+label.getLabel(), Toast.LENGTH_SHORT).show();
                            }
                            }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CameraActivity.this, "Image Cant Detect Successfully", Toast.LENGTH_SHORT).show();
                            String text = "Image Detection Unsuccessful.";
                            if ("".equals(text)) {
                                text = "Please enter some text to speak.";
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                            }
                            else {
                                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                            }
                            startActivity(new Intent(CameraActivity.this,HomeActivity.class));
                        }
                    });
                }else {
                    waitingDialog.dismiss();
                    String text = "Please on your internet connection to detect image.";
                    if ("".equals(text)) {
                        text = "Please enter some text to speak.";
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                    else {
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    startActivity(new Intent(CameraActivity.this,HomeActivity.class));
                }

            }
        });

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
        String text = "You are in the Image Detection activity. Press long click on Bottom to get battery information.";
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