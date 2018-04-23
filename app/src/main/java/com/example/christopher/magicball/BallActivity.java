package com.example.christopher.magicball;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.Locale;


public class BallActivity extends AppCompatActivity {
    Utilities utilities = new Utilities();
    TextToSpeech textToSpeech;
    private String[] phrase = new String[20];
    private String magicTextMessage;
    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);
        utilities.hideToolbar(BallActivity.this);
        getSupportActionBar().hide();
        Intent receivedIntent = getIntent();
        final Bundle b = receivedIntent.getBundleExtra("Key");
        phrase = b.getStringArray("phrase");


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() throws InterruptedException {
                utilities.ballMovement(BallActivity.this);
                magicTextMessage = utilities.magicPhraseGenerator(BallActivity.this);
                textToSpeech = new TextToSpeech(BallActivity.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status == TextToSpeech.SUCCESS) {
                            int result = textToSpeech.setLanguage(Locale.ENGLISH);
                            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Log.e("TTS","Language not supported");
                            }
                            else {
                                textToSpeech.setPitch(0.001f);
                                textToSpeech.setSpeechRate(0.001f);
                                textToSpeech.speak(magicTextMessage,TextToSpeech.QUEUE_FLUSH,null);
                            }
                        }
                        else{
                            Log.e("TTS","Initialization failed");
                        }
                    }
                });
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}













