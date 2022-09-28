package com.example.musican;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Locale;

public class Ejercicio2 extends AppCompatActivity {

    private int seconds = 10;
    private boolean running = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio2);

        runTimer();
    }

    private void runTimer(){

        //Declarar un handles
        Handler handler = new Handler();
        //Invocar metodo post e instanciar runnable
        handler.post(new Runnable() {
            @Override
            public void run() {
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),"%d",secs);

                if(seconds == 0){
                    Intent i = new Intent(Ejercicio2.this, Ejercicio21.class);
                    startActivity(i);
                }

                if(running)
                    seconds --;

                handler.postDelayed(this,1000);
            }
        });
    }
}