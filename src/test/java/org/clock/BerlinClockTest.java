package org.clock;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by ashamsutdinov on 07.08.2017.
 */
public class BerlinClockTest {
    @Test
    public void toBerlinClock() throws Exception {
        int n = 100;
        while(n-- > 0) {
            // firstly generate random time as integer values
            Random random = new Random();
            int h = random.nextInt(23);
            int m = random.nextInt(59);
            int s = random.nextInt(59);
    
            System.err.println("expected hours = " + h);
            System.err.println("expected minutes = " + m);
            
            BitSet bits = BerlinClock.toBerlinClock(h, m, s);
    
            BitSet hoursBitsX5  = bits.get(1, 5);
            BitSet hoursBitsX1  = bits.get(5, 9);
            BitSet minutesBitsX5 = bits.get(9, 20);
            BitSet minutesBitsX1 = bits.get(20, 24);
    
            // check hours
            System.err.println("result hours = " + (hoursBitsX5.cardinality() * 5 + hoursBitsX1.cardinality()));
            assertTrue( (hoursBitsX5.cardinality() * 5 + hoursBitsX1.cardinality()) == h);
    
            // check minutes
            System.err.println("result minutes = " + (minutesBitsX5.cardinality() * 5 + minutesBitsX1.cardinality()));
            assertTrue( (minutesBitsX5.cardinality() * 5 + minutesBitsX1.cardinality()) == m);

            // check seconds <=> just a parity of seconds
            assertTrue(bits.get(0) == (s % 2 == 1));

        }
    }
    
}