package com.dam2.crynetenforcementlocallogistics;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

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
    public void checkKeypadWorks() {
        checkKeypadWorks(new Random().nextLong());
    }

    public void checkKeypadWorks(long key) {

    }

    public void countMatrixOcurrences(){
        int sample = 999999999;
        String cosa = KeypadActivity.LETTERS + KeypadActivity.NUMBERS;

        int[] ocurrencias = new int[cosa.length()];

        for (int i = 0; i < cosa.length(); i++){
            ocurrencias[i] = cosa.charAt(i);
        }

        for (int i = 0; i < sample; i++) {
            char[] matrix = KeypadActivity.generateMatrix(i % KeypadActivity.LARGO_KEY, KeypadActivity.salt);
            for (char letra: matrix) {
                ocurrencias[cosa.indexOf(letra + "")]++;
            }
        }
    }


}
