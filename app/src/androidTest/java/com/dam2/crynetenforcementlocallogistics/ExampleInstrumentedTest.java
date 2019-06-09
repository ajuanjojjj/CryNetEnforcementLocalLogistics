package com.dam2.crynetenforcementlocallogistics;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.dam2.crynetenforcementlocallogistics", appContext.getPackageName());
    }
    
    @Test
    public void checkLogin(){
        String[] emailGud = {"jhargreave@hrbiomed.corp"};
        String[] passGud = {"PASSDETEST"};
        String[] emailBad = {"asdasdasdas@gmail.com"};
        String[] passBad = {"dasdasasdasd"};
        for (int i = 0; i < 1; i++)
            assertNotEquals(checkLogin(emailGud[i], passGud[i]), null);

        for (int i = 0; i < 1; i++)
            assertEquals(checkLogin(emailBad[i], passBad[i]), null);
    }

    public String checkLogin(String email, String pass){
        UserLoginTask mAuthTask = new UserLoginTask();
        try {
            return mAuthTask.execute(email, pass).get();
        } catch (Exception e){
            return null;
        }
    }

    @Test
    public void isMatrixGood(){
        int sample = 300000;

        long[] result = countMatrixOcurrences(sample);
        double deviation = standardDeviation(result);

        assertTrue(deviation < 10);
    }

    public long[] countMatrixOcurrences(int sample) {
        String cosa = KeypadActivity.LETTERS + KeypadActivity.NUMBERS;

        long[] ocurrencias = new long[cosa.length()];

        KeypadActivity actividad = new KeypadActivity();

        for (int k = 0; k < sample; k++) {
            for (int i = 0; i < KeypadActivity.LARGO_KEY; i++) {

                char[] matrix = actividad.generateMatrix();

                int pulsada = ThreadLocalRandom.current().nextInt(19); //Tecla a pulsar, me da lo mismo cual

                for (int j = 0; j < matrix.length; j++) {
                    char letra = matrix[j];
                    int pos = cosa.indexOf(letra);
                    if (j == pulsada) {
                        for (int w = 0; w < pulsada; w++)
                            actividad.randomInt();
                    }
                    ocurrencias[pos]++;
                }
            }
            actividad.seed = KeypadActivity.SALT_INICIAL;
        }
        return ocurrencias;
    }

    public double standardDeviation(long[] numbers){
        double average = average(numbers);
        double sd = 0;
        for (long number : numbers) {
            sd += ((number - average) * (number - average)) / (numbers.length - 1);
        }
        return Math.sqrt(sd);
    }

    public double average(long[] numbers){
        double sum = 0;
        for (long number : numbers) {
            sum = sum + number;
        }
        return sum / numbers.length;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate the user.
     */
    class UserLoginTask extends AsyncTask<String, Void, String> {

        private static final String PASSWORD = "password";   //NON-NLS
        private static final String EMAIL = "email";         //NON-NLS
        private static final String URI = "http://crynet.wunderapp.es/servicios/login";
        private String mEmail;
        private String mPassword;
        /*
                UserLoginTask(String email, String password) {
                    mEmail = email;
                    mPassword = password;
                }
        */
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(URI);
                urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "application/" + "json");
                    urlConnection.setDoOutput(true);
                    //urlConnection.setRequestProperty("Authorization", "Bearer " + App.token);

                    String json = "{\"login\": \"" + params[0] + "\",\"password\":\"" + params[1] + "\"}";
                    byte[] outputInBytes = json.getBytes(StandardCharsets.UTF_8);


                    OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                    out.write( outputInBytes );
                    out.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
                    String token =  in.readLine().replace("\"", "");
                    return token;

                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception ex) {
                Log.e("Error en login", ex.getMessage());
                //int responseCode = urlConnection.getResponseCode(); //can call this instead of con.connect()
                /*if (responseCode >= 400 && responseCode <= 499) {
                    throw new Exception("Bad authentication status: " + responseCode); //provide a more meaningful exception message
                }*/
                return null;
            }
        }
    }
}
