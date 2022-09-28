package com.example.musican;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class IngresaNota extends AppCompatActivity {

    private int seconds = 60;
    private boolean running = true;
    private boolean delay = false;
    private int puntos = 0;
    private int errores = 0;
    TextView nota;
    TextView puntuacion;
    ImageView er;

    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";

    public String[] notas = {"Do","Re","Mi","Fa","Sol","La","Si"};
    private NfcAdapter mNfcAdapter;
    Random aleatorio = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresa_nota);

        int x = aleatorio.nextInt(7);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        runTimer();
        handleIntent(getIntent());
        nota = (TextView) findViewById(R.id.nota);
        nota.setText(notas[x]);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setupForegroundDispatch(this, mNfcAdapter);
    }

    @Override
    protected void onPause() {

        stopForegroundDispatch(this, mNfcAdapter);

        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new IngresaNota.NdefReaderTask().execute(tag);

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new IngresaNota.NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "Unsupported Encoding", e);
                    }
                }
            }

            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {

            byte[] payload = record.getPayload();

            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

            int languageCodeLength = payload[0] & 0063;

            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {

                nota = (TextView) findViewById(R.id.nota);
                puntuacion = (TextView) findViewById(R.id.puntos);
                String[] parte = result.split(",");
              if (parte[1].equals(nota.getText().toString())){
                    puntos ++;
                    int x = aleatorio.nextInt(7);
                    nota.setText(notas[x]);
                    puntuacion.setText("Puntos " + puntos);
                }else{
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
                    Intent i = new Intent(IngresaNota.this, Resultados.class);
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