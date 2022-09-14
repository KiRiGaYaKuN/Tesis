package com.example.musican;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuEjercicio extends AppCompatActivity {

    Button eje1;
    Button eje2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ejercicio);

        eje1 = (Button) findViewById(R.id.ejercicio1);
        eje2 = (Button) findViewById(R.id.ejercicio2);

        eje1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent i = new Intent(MenuEjercicio.this, Ejercicio1.class);
                startActivity(i);
            }
        });

        eje2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent i = new Intent(MenuEjercicio.this, Ejercicio2.class);
                startActivity(i);
            }
        });
    }
}