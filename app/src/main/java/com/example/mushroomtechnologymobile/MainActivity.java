package com.example.mushroomtechnologymobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread mythread = new Thread(){
            @Override
            public void run() {

                try{
                    sleep(1000);
                    Intent intent = new Intent(getApplicationContext(),MenuActive.class);
                    startActivity(intent);
                    finish();

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        mythread.start();
    }
}
