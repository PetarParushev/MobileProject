package com.example.mobilegameballcollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button gameButton;
    Button aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameButton = findViewById(R.id.gameButton);
        aboutButton = findViewById(R.id.aboutButton);

        gameButton.setOnClickListener(onClickListener);
        aboutButton.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.gameButton) {
                Intent intent = new Intent(
                        MainActivity.this, GameActivity.class);

                startActivity(intent);
            } else if (v.getId() == R.id.aboutButton) {
                Intent intent = new Intent(
                        MainActivity.this, AboutActivity.class);

                startActivity(intent);
            }
        }
    };
}
