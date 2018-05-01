package com.example.christopher.magicball;

import android.content.Context;
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
    private String magicTextMessage;
    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);
        utilities.hideToolbar(BallActivity.this);
        hideActionBar();
        final String[] magicalPhrases = utilities.fillComboPhrases();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager != null ? mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) : null;
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                utilities.ballMovement(BallActivity.this);
                magicTextMessage = utilities.magicPhraseGenerator(BallActivity.this,magicalPhrases);
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

    private void hideActionBar()
    {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (null != actionBar)
            actionBar.hide();
    }
}













