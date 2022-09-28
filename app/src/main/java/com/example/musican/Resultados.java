package com.example.musican;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Resultados extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="0";

    Button reinicio;
    Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        TextView messageview=findViewById(R.id.resultado);
        Intent intent=getIntent();
        String messagetext = intent.getStringExtra(EXTRA_MESSAGE);
        messageview.setText(messagetext);

        reinicio = (Button) findViewById(R.id.reinicio);
        menu = (Button) findViewById(R.id.menu);

        reinicio.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent i = new Intent(Resultados.this, MenuEjercicio.class);
                startActivity(i);
            }
        });

        menu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent i = new Intent(Resultados.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}