package com.kjh85skill12.holyland;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click_start(View view) {

        Intent intent = new Intent(this,SecondActivity.class);

        startActivity(intent);

        finish();


    }
}
