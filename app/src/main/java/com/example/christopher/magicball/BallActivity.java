package com.example.christopher.magicball;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;


public class BallActivity extends AppCompatActivity  {
    TextToSpeech tts;
    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();

        Intent receivedIntent = getIntent();
        final Bundle b = receivedIntent.getBundleExtra("Phrase");
        b.getStringArray("frase");

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                // Do stuff!
                final TextView tv = findViewById(R.id.magic_phrase);
                tv.setText(b.getStringArray("frase")[(int)Math.floor(Math.random()* 20)]);

                tts = new TextToSpeech(BallActivity.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status == TextToSpeech.SUCCESS)
                        {
                            int result = tts.setLanguage(Locale.ENGLISH);

                            if(result == TextToSpeech.LANG_MISSING_DATA
                                    || result == TextToSpeech.LANG_NOT_SUPPORTED)
                            {
                                Log.e("TTS ","Language not supported");
                            }
                            else {
                                tts.setPitch(0.001f);
                                tts.setSpeechRate(0.001f);
                                tts.speak((String) tv.getText(),TextToSpeech.QUEUE_FLUSH,null);
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













