package com.dam2.crynetenforcementlocallogistics;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

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
    public void isMatrixGood(){
        int sample = 300000;

        long[] result = countMatrixOcurrences(sample);
        double deviation = standardDeviation(result);

        assertTrue(deviation < 10);
    }

    @Test
    public void checkKeypadWorks() {
        checkKeypadWorks(new Random().nextLong());
    }

    public void checkKeypadWorks(long key) {

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


}
