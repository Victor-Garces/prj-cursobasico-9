package com.example.christopher.magicball;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


class Utilities {

    private String[] phrase = new String[20];
    private TextView magicMessage;
    private int animationDuration = 500;
    private int possibilityPhrases = 20;
    private float movementDistance = 300f;
    private short repeatCount = 3;

    void hideToolbar(Activity context)
    {
        View decorView = context.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    String[] fillComboPhrases()
    {
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
        return phrase;
    }

    void ballMovement(Activity context) throws InterruptedException {
        ImageView imageView = context.findViewById(R.id.black_ball);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageView,"x",movementDistance);
        animatorX.setDuration(animationDuration);
        animatorX.setRepeatMode(ObjectAnimator.REVERSE);
        animatorX.setRepeatCount(repeatCount);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();
    }

    String magicPhraseGenerator(Activity context)
    {
        magicMessage = context.findViewById(R.id.magic_phrase);
        magicMessage.setText(fillComboPhrases()[(int)Math.floor(Math.random()* possibilityPhrases)]);
        String magicTextMessage = (String) magicMessage.getText();
        return magicTextMessage;
    }
}

