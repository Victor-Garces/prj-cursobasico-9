package com.example.christopher.magicball;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BallActivity extends AppCompatActivity {

    private String[] phrase = new String[20];
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

        phrase[0] = "It is certain";
        phrase[1] = "It is decidedly so";
        phrase[2] = "Without a doubt";
        phrase[3] = "Yes definitely";
        phrase[4] = "You may rely on it";
        phrase[5] = "As I see it, yes";
        phrase[6] = "Most likely";
        phrase[7] = "Yes";
        phrase[8] = "Signs point to yes";
        phrase[9] = "Reply hazy try again";
        phrase[10] = "Ask again later";
        phrase[11] = "Better not tell you now";
        phrase[12] = "Cannot predict now";
        phrase[13] = "Concentrate and ask again";
        phrase[14] = "Don't count on it";
        phrase[15] = "My reply is no";
        phrase[16] = "My sources say no";
        phrase[17] = "Outlook not so good";
        phrase[18] = "Very doubtful";
        phrase[19] = "Outlook good";

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                // Do stuff!
                TextView tv = findViewById(R.id.magic_phrase);
                tv.setText(phrase[(int)Math.floor(Math.random()* 20)]);
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













