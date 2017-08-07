package org.time.core.impl;

import org.time.data.Bits;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by ashamsutdinov on 07.08.2017.
 */
public class BerlinClockTest {

    private final int TEST_ITERATIONS = 100;

    private BerlinClock instance;
    private PrintStream traceStream;

    @Before
    public void init() {
        instance = new BerlinClock();
        traceStream = new PrintStream(System.err);
    }

    @Test
    public void encodeTime() throws Exception {
        int i = 0;
        while(i++ < TEST_ITERATIONS) {
            // firstly generate random time as integer values
            Random random = new Random();
            int h = random.nextInt(23);
            int m = random.nextInt(59);
            int s = random.nextInt(59);
    
            traceStream.println("expected hours = " + h);
            traceStream.println("expected minutes = " + m);

            Bits<BitSet> bits = instance.encodeTime(h, m, s);

            String bBits = bits.toBinaryString();

            int hoursBitsX5  = bBits.substring(1, 5).replaceAll("0", "").length();
            int hoursBitsX1  = bBits.substring(5, 9).replaceAll("0", "").length();
            int minutesBitsX5 = bBits.substring(9, 20).replaceAll("0", "").length();
            int minutesBitsX1 = bBits.substring(20, 24).replaceAll("0", "").length();
    
            // check hours
            traceStream.println("result hours = " + (hoursBitsX5 * 5 + hoursBitsX1));
            assertTrue( (hoursBitsX5 * 5 + hoursBitsX1) == h);
    
            // check minutes
            traceStream.println("result minutes = " + (minutesBitsX5 * 5 + minutesBitsX1));
            assertTrue( (minutesBitsX5 * 5 + minutesBitsX1) == m);

            // check seconds <=> just a parity of seconds
            assertTrue(bBits.substring(0, 1).equals((s % 2 == 1 ? "1" : "0")));
        }
    }

    @Test
    public void decodeTime() throws Exception {
        int i = 0;
        while(i++ < TEST_ITERATIONS) {
            // firstly generate random time as integer values
            Random random = new Random();
            int h = random.nextInt(23);
            int m = random.nextInt(59);
            int s = random.nextInt(59);

            String expectedString = String.format("%02d", h) + ":" + String.format("%02d", m);

            traceStream.println("expected time = " + expectedString);

            Bits<BitSet> bits = instance.encodeTime(h, m, s);
            String resultString = instance.decodeTime(bits);

            traceStream.println("result time = " + resultString);

            assertEquals(expectedString, resultString);
        }
    }

}