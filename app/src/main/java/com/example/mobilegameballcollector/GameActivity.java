package com.example.mobilegameballcollector;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class GameActivity extends AppCompatActivity implements Animation.AnimationListener {

    private int collectedBalls;
    private int currentRecord;
    private String leftOrRight;
    private String gameChoice;

    TextView collectedTextView;
    TextView newRecordTextView;
    TextView youLostTextView;

    ImageView firstImgMove;
    ImageView secondImgMove;

    Button leftButton;
    Button rightButton;

    Animation firstAnimMoveToBottom;
    Animation secondAnimMoveToBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        firstImgMove = findViewById(R.id.firstImgMove);
        secondImgMove = findViewById(R.id.secondImgMove);

        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);

        collectedTextView = findViewById(R.id.collectedTextView);
        newRecordTextView = findViewById(R.id.newRecordTextView);
        youLostTextView = findViewById(R.id.youLostTextView);

        leftButton.setOnClickListener(onClickListener);
        rightButton.setOnClickListener(onClickListener);

        ImageView imgView = findViewById(R.id.firstImgMove);
        ImageView imgView2 = findViewById(R.id.secondImgMove);

        imgView.setVisibility(View.INVISIBLE);
        imgView2.setVisibility(View.INVISIBLE);

        SharedPreferences sharedPreferences =
                getSharedPreferences("MobileGameBallCollectorRecord", MODE_PRIVATE);
        currentRecord = sharedPreferences.getInt("record", 0);


    }

    private String getLeftOrRight() {
        String[] choices = new String[]{"left", "right"};
        int randomChoice = new Random().nextInt(choices.length);

        return choices[randomChoice];
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkChoice(String choice) {
        gameChoice = getLeftOrRight();


        leftButton.setEnabled(false);
        rightButton.setEnabled(false);

        if (gameChoice == "left") {
            firstAnimMoveToBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
            firstAnimMoveToBottom.setAnimationListener(this);
            firstImgMove.setTranslationZ(1);
            firstImgMove.startAnimation(firstAnimMoveToBottom);
        } else {
            secondAnimMoveToBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
            secondAnimMoveToBottom.setAnimationListener(this);
            secondImgMove.setTranslationZ(1);
            secondImgMove.startAnimation(secondAnimMoveToBottom);
        }

        leftButton.setEnabled(false);

        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        leftButton.setEnabled(true);
                        firstImgMove.setTranslationZ(-1);
                    }
                });
            }
        }, 1000);

        rightButton.setEnabled(false);

        Timer buttonTimer2 = new Timer();
        buttonTimer2.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        rightButton.setEnabled(true);
                        secondImgMove.setTranslationZ(-1);
                    }
                });
            }
        }, 1000);

        if (choice == gameChoice) {
            addAnotherCollectedBall();
        } else {
            firstImgMove.setVisibility(View.INVISIBLE);
            secondImgMove.setVisibility(View.INVISIBLE);
            showGameEnded();
        }
    }

    private void addAnotherCollectedBall() {
        collectedBalls++;

        if (collectedBalls > currentRecord) {
            currentRecord = collectedBalls;

            SharedPreferences.Editor sharedPreferencesEditor =
                    getSharedPreferences("MobileGameBallCollectorRecord", MODE_PRIVATE).edit();

            sharedPreferencesEditor.putInt("record", collectedBalls);

            sharedPreferencesEditor.apply();
            sharedPreferencesEditor.commit();

            newRecordTextView.setText("N E W  R E C O R D : " + collectedBalls);
            newRecordTextView.setVisibility(View.VISIBLE);
        }

        this.collectedTextView.setText(String.valueOf(collectedBalls));
    }

    private void showGameEnded() {
        collectedBalls = 0;

        this.collectedTextView.setText(String.valueOf(collectedBalls));
        youLostTextView.setVisibility(View.VISIBLE);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {
            youLostTextView.setVisibility(View.INVISIBLE);
            newRecordTextView.setVisibility(View.INVISIBLE);

            if (v.getId() == R.id.leftButton) {
                leftOrRight = "left";

                checkChoice(leftOrRight);
            } else if (v.getId() == R.id.rightButton) {
                leftOrRight = "right";

                checkChoice(leftOrRight);
            }

        }
    };

    public void onFragmentInteraction(Uri uri) {
        Log.i("Tag", "onFragmentInteraction called");
    }


    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation
        // check for move animation
        if (animation == firstAnimMoveToBottom && animation == secondAnimMoveToBottom) {
            Toast.makeText(getApplicationContext(), "Animation Stopped", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub
    }


}