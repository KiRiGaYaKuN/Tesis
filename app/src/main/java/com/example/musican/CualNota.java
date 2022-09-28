package com.example.musican;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class CualNota extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="mesagge";

    private int seconds = 60;
    private boolean running = true;
    private boolean delay = false;
    private int puntos = 0;
    private int errores = 0;
    TextView nota;
    TextView puntuacion;
    ImageView er;
    String respuesta = "";
    public String[] notas = {"Do","Re","Mi","Fa","Sol","La","Si"};
    public Partituras parti = new Partituras();
    public String[] partitura;
    Random aleatorio = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cual_nota);

        Intent intent=getIntent();
        String messagetext = intent.getStringExtra(EXTRA_MESSAGE);
        String[] partes = messagetext.split(",");

        switch(partes[1]) {
            case "Estrellita":
                partitura = parti.getEstrellita();
                break;
            case "Partitura":
                break;
            default:
                Toast.makeText(CualNota.this, "La nota o partitura no se encuentra", Toast.LENGTH_LONG).show();
        }
        runTimer();

        String[] opciones = {"","",""};
        int y = aleatorio.nextInt(partitura.length);

        nota = (TextView) findViewById(R.id.nota);
        nota.setText("Cual es la nota de la posición " + (y+1));
        opciones[0] = partitura[y];
        respuesta = partitura[y];
        for (int x = 1; x<3; x++){
           int r = aleatorio.nextInt(7);
           if (!(notas[r].equals(opciones[0]) || notas[r].equals(opciones[1]) || notas[r].equals(opciones[2]))){
               opciones[x] = notas[r];
           }
           else{
               x--;
           }
        }
        Button op1 = (Button) findViewById(R.id.eleccion1);
        Button op2 = (Button) findViewById(R.id.eleccion2);
        Button op3 = (Button) findViewById(R.id.eleccion3);

        int[] res = {-1,-1,-1};
        for (int x = 0; x<3; x++){
            int r = aleatorio.nextInt(3);
            if (r != res[0] && r != res[1] && r != res[2]){
                res[x] = r;
            }
            else{
                x--;
            }
        }
        op1.setText(opciones[res[0]]);
        op2.setText(opciones[res[1]]);
        op3.setText(opciones[res[2]]);

        puntuacion = (TextView) findViewById(R.id.puntos);

        op1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                String var = op1.getText().toString();
                if (var.equals(respuesta)){
                    puntos ++;
                    puntuacion.setText("Puntos " + puntos);
                    cambio();
                }else{
                    errores();
                }

            }
        });

        op2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                String var = op2.getText().toString();
                if (var.equals(respuesta)){
                    puntos ++;
                    puntuacion.setText("Puntos " + puntos);
                    cambio();
                }else{
                    errores();
                }

            }
        });

        op3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                String var = op3.getText().toString();
                if (var.equals(respuesta)){
                    puntos ++;
                    puntuacion.setText("Puntos " + puntos);
                    cambio();
                }else{
                    errores();
                }

            }
        });


    }

    private void cambio(){
        String[] opciones = {"","",""};
        int y = aleatorio.nextInt(partitura.length);

        nota = (TextView) findViewById(R.id.nota);
        nota.setText("Cual es la nota de la posición " + (y+1));
        opciones[0] = partitura[y];
        respuesta = partitura[y];
        for (int x = 1; x<3; x++){
            int r = aleatorio.nextInt(7);
            if (!(notas[r].equals(opciones[0]) || notas[r].equals(opciones[1]) || notas[r].equals(opciones[2]))){
                opciones[x] = notas[r];
            }
            else{
                x--;
            }
        }
        Button op1 = (Button) findViewById(R.id.eleccion1);
        Button op2 = (Button) findViewById(R.id.eleccion2);
        Button op3 = (Button) findViewById(R.id.eleccion3);

        int[] res = {-1,-1,-1};
        for (int x = 0; x<3; x++){
            int r = aleatorio.nextInt(3);
            if (r != res[0] && r != res[1] && r != res[2]){
                res[x] = r;
            }
            else{
                x--;
            }
        }
        op1.setText(opciones[res[0]]);
        op2.setText(opciones[res[1]]);
        op3.setText(opciones[res[2]]);
    }

    private void errores(){
        errores ++;
        if (errores == 1){
            er = (ImageView) findViewById(R.id.er1);
            er.setVisibility(View.VISIBLE);
        }
        else{
            if (errores == 2){
                er = (ImageView) findViewById(R.id.er2);
                er.setVisibility(View.VISIBLE);
            }else{
                if (errores == 3){
                    er = (ImageView) findViewById(R.id.er3);
                    er.setVisibility(View.VISIBLE);
                    delay = true;
                }
            }

        }
    }

    private void runTimer(){

        TextView timeView =(TextView) findViewById(R.id.time);

        //Declarar un handles
        Handler handler = new Handler();
        //Invocar metodo post e instanciar runnable
        handler.post(new Runnable() {
            @Override
            public void run() {

                if(delay){
                    seconds = 0;
                    delay = false;
                }

                int sec = seconds;
                String time = String.format(Locale.getDefault(),"%d",seconds);

                if(seconds > 0){
                    timeView.setText(time);
                    handler.postDelayed(this,1000);
                }

                if(seconds == -1){
                    Intent i = new Intent(CualNota.this, Resultados.class);
                    String res = "" + puntos;
                    i.putExtra(Resultados.EXTRA_MESSAGE,res);
                    startActivity(i);
                }

                if(seconds == 0){
                    timeView.setText(time);
                    handler.postDelayed(this,10000);
                }

                if(running)
                    seconds --;
            }
        });
    }
}