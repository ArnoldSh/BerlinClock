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
    
            BitSet hoursBitsX5  = bits.get().get(1, 5);
            BitSet hoursBitsX1  = bits.get().get(5, 9);
            BitSet minutesBitsX5 = bits.get().get(9, 20);
            BitSet minutesBitsX1 = bits.get().get(20, 24);
    
            // check hours
            traceStream.println("result hours = " + (hoursBitsX5.cardinality() * 5 + hoursBitsX1.cardinality()));
            assertTrue( (hoursBitsX5.cardinality() * 5 + hoursBitsX1.cardinality()) == h);
    
            // check minutes
            traceStream.println("result minutes = " + (minutesBitsX5.cardinality() * 5 + minutesBitsX1.cardinality()));
            assertTrue( (minutesBitsX5.cardinality() * 5 + minutesBitsX1.cardinality()) == m);

            // check seconds <=> just a parity of seconds
            assertTrue(bits.get().get(0) == (s % 2 == 1));
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