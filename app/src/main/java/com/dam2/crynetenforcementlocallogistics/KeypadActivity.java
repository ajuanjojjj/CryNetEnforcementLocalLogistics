package com.dam2.crynetenforcementlocallogistics;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class KeypadActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback{
    private static final String BTN_KEYPAD = "btn_keypad";  //NON-NLS
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; //NON-NLS
    private static final long SEMILLA_INICIAL = 98163975832515L;
    private static final int LARGO_KEY = 10;
    private static final String TEXT_PLAIN = "text/plain";   //NON-NLS
    private static final String SECUENCE_S_PRESSES_S = "Secuence= %s\nPresses= %s";  //NON-NLS
    private final Button[] botones = new Button[20];
    private String typedKeys = "";
    private String typedChars = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypass);

        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Toast.makeText(this, "Sorry this device does not have NFC.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
        }

        mAdapter.setNdefPushMessageCallback(this, this);

        for (int i = 0; i < botones.length; i++) {
            int id = getResources().getIdentifier(BTN_KEYPAD + i, "id", getPackageName()); //NON-NLS
            botones[i] = findViewById(id);
            final int pos = i;
            botones[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button boton = (Button) v;
                    String caracter = boton.getText().toString();
                    pulsar(pos, caracter);
                    llenarBotones(CARACTERES.hashCode() ^ caracter.hashCode());
                }
            });
        }


        llenarBotones(SEMILLA_INICIAL);
    }

    private void pulsar(int pos, String secuencia){
        StringBuilder mostrar = new StringBuilder();
        TextView view = findViewById(R.id.txt_typedKey);
        for (int i = 0; i < 10-(typedChars.length()+1); i++)
            mostrar.append("\uF07F");
        typedChars += secuencia;
        mostrar.append(typedChars);
        view.setText(mostrar.toString());
        typedKeys += pos + "-";
        if (typedChars.length() >= LARGO_KEY) {
            Toast.makeText(this, String.format(SECUENCE_S_PRESSES_S, typedChars, typedKeys), Toast.LENGTH_LONG).show();
            view.setText("\uF07F\uF07F\uF07F\uF07F\uF07F\uF07F\uF07F\uF07F\uF07F\uF07F");
            typedChars = "";
            typedKeys = "";
        }
    }

    private void llenarBotones(long seed){
        String cadena = generateButtonString(seed);

        for (int i = 0; i < botones.length; i++) {
            String caracter = cadena.charAt(i) + "";
            botones[i].setText(caracter);
        }
    }

    private String generateButtonString(long seed){
        ArrayList<Character> caracteres = new ArrayList<>();
        for (char c : CARACTERES.toCharArray()) {
            caracteres.add(c);
        }

        char[] cadena = new char[botones.length];
        /*
        Random gen = new Random(seed);
        for (int i = 0; i < cadena.length; i++){
            int pos = gen.nextInt(caracteres.size() - 1);
            char caracter = caracteres.get(pos);
            caracteres.remove(pos);
            cadena[i] = caracter;
        }
        /*/

        for (int i = 0; i < cadena.length; i++){
            int pos = (int) (Math.abs(seed) % caracteres.size());
            char caracter = caracteres.get(pos);
            caracteres.remove(pos);
            cadena[i] = caracter;
        }

        Arrays.sort(cadena);

        return new String(cadena);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        String message = typedKeys;
        Toast.makeText(this, "test", Toast.LENGTH_LONG).show(); //NON-NLS
        NdefRecord ndefRecord = NdefRecord.createMime(TEXT_PLAIN, message.getBytes());
        return new NdefMessage(ndefRecord);
    }

}
