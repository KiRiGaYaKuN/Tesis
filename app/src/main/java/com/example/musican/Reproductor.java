package com.example.musican;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

public class Reproductor extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="mesagge";

    ImageButton play_pause, back, next;
    MediaPlayer mp;
    ImageView iv;
    int posicion = 0;

    MediaPlayer vectormp [] = new MediaPlayer[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);

        TextView messageview=findViewById(R.id.prueba);
        play_pause = (ImageButton) findViewById(R.id.play);
        back = (ImageButton) findViewById(R.id.back);
        next = (ImageButton) findViewById(R.id.next);

        Intent intent=getIntent();
        String messagetext = intent.getStringExtra(EXTRA_MESSAGE);
        messageview.setText("Reproduciendo " + messagetext);

        switch(messagetext) {
            case "Nota Re":
                vectormp[0] = MediaPlayer.create(this, R.raw.re);
                vectormp[0].start();

                break;
            case "estrellita":
                vectormp[0] = MediaPlayer.create(this, R.raw.estrellita);
                vectormp[0].start();
                break;
            default:
                messageview.setText("Pruebe otra tarjeta");
                Toast.makeText(Reproductor.this, "La nota o partitura no se encuentra", Toast.LENGTH_LONG).show();
        }



        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vectormp[0].isPlaying()){
                    vectormp[0].pause();
                    play_pause.setBackgroundResource(android.R.drawable.ic_media_play);
                }else{
                    vectormp[0].start();
                    play_pause.setBackgroundResource(android.R.drawable.ic_media_pause);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vectormp[0].seekTo(vectormp[0].getCurrentPosition() + 5000);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vectormp[0].seekTo(vectormp[0].getCurrentPosition() - 5000);
            }
        });

    }

}