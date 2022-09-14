package com.example.musican;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button tutorial;
    Button scann;
    Button ejercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tutorial = (Button) findViewById(R.id.tutorial);
        scann = (Button) findViewById(R.id.scan);
        ejercicio = (Button) findViewById(R.id.eje);

        tutorial.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, Tutorial.class);
                startActivity(i);
            }
        });

        scann.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, ScannNfc.class);
                startActivity(i);
            }
        });

        ejercicio.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, MenuEjercicio.class);
                startActivity(i);
            }
        });


    }
}