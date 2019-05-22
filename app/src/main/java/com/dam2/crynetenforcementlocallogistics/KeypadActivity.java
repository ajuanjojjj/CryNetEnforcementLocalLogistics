package com.dam2.crynetenforcementlocallogistics;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Stack;

public class KeypadActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {
    private static final String BTN_KEYPAD = "btn_keypad";  //NON-NLS
    private static final String TEXT_PLAIN = "text/plain";   //NON-NLS

    private final static String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//NON-NLS
    private final static String NUMBERS = "1234567890";//NON-NLS
    private static final int LARGO_KEY = 12;

    private final Button[] botones = new Button[20];
    private static final byte salt = (byte) 136; //TODO Pruebas sobre aleatoriedad en matrix
    private int pos = 0;

    private char[] typedValues;
    private char[] typedChars;

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

        typedChars = new char[LARGO_KEY];
        typedValues = new char[LARGO_KEY];

        TextView view = findViewById(R.id.txt_typedKey);
        view.setText(generateLabel());

        for (int i = 0; i < botones.length; i++) {
            int id = getResources().getIdentifier(BTN_KEYPAD + i, "id", getPackageName()); //NON-NLS
            botones[i] = findViewById(id);
        }
        fillButtons();
    }

    private static String generateLabel(char[] typedChars) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < typedChars.length; i++) {
            char typedChar = typedChars[i];
            if (i != 0 && i % 4 == 0)
                builder.append('-');
            builder.append(typedChar == 0 ? "\uF07F" : typedChar);
        }
        return builder.toString();
    }

    private void fillButtons() {
        char[] matrix = generateMatrix(pos, salt);
        Tag[] tags = new Tag[botones.length];

        for (int i = 0; i < tags.length; i++) {
            Tag tag = new Tag();
            tag.setValue(i);
            tag.setRepresent(matrix[i]);
            tags[i] = tag;
        }

        Arrays.sort(tags);

        for (int i = 0; i < botones.length; i++) {
            botones[i].setTag(tags[i]);
            botones[i].setText(tags[i].toString());
        }

    }

    private static char[] generateMatrix(int pos, int salt) {
        double step = Math.abs(salt ^ pos);
        char[] matriz = new char[20];

        Stack<Character> pilaLetter = new Stack<>();
        for (Character character : LETTERS.toCharArray())
            pilaLetter.add(character);

        Stack<Character> pilaNumber = new Stack<>();
        for (Character character : NUMBERS.toCharArray())
            pilaNumber.add(character);

        for (int i = 0; i < 16; i++) {
            matriz[i] = pilaLetter.remove((int) (step * (i + 1)) % pilaLetter.size());
        }

        for (int i = 16; i < 20; i++) {
            matriz[i] = pilaNumber.remove((int) (step * (i + 1)) % pilaNumber.size());
        }

        return matriz;
    }

    protected void pulsarBoton(View v) {
        Tag tag = (Tag) v.getTag();
        TextView view = findViewById(R.id.txt_typedKey);


        view.setText(generateLabel(typedChars));

        pos++;
        if (pos >= LARGO_KEY) {
            Toast.makeText(this, String.format("Secuence= %s\nPresses= %s", new String(typedChars), new String(typedValues)), Toast.LENGTH_LONG).show();
            typedChars = new char[LARGO_KEY];
            typedValues = new char[LARGO_KEY];
            pos = 0;
            view.setText(generateLabel(typedChars));
        }

        fillButtons();
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        String message = new String(typedValues);
        Toast.makeText(this, "test", Toast.LENGTH_LONG).show(); //NON-NLS
        NdefRecord ndefRecord = NdefRecord.createMime(TEXT_PLAIN, message.getBytes());
        return new NdefMessage(ndefRecord);
    }


    private class Tag implements Comparable<Tag> {
        private char represent;
        private char value;

        public int compareTo(Tag compareTag) {
            char compareRepresent = compareTag.getRepresent();

            return this.represent - compareRepresent;
        }

        public char getRepresent() {
            return represent;
        }

        public void setRepresent(char represent) {
            this.represent = represent;
        }

        public char getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = Integer.toString(value, 20).charAt(0);
        }

        @NonNull
        @Override
        public String toString() {
            return represent + "";
        }
    }
}
