package com.example.christopher.magicball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Utilities utilities = new Utilities();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        utilities.hideToolbar(MainActivity.this);
        getSupportActionBar().hide();
        ImageView startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Bundle b = new Bundle();
        Intent intent = new Intent(this,BallActivity.class);
        b.putStringArray("phrase",utilities.fillComboPhrases());
        intent.putExtra("Key",b);
        startActivity(intent);
    }
}
