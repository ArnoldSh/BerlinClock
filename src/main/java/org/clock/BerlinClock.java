package org.clock;

import org.exception.BerlinClockException;

import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ashamsutdinov on 07.08.2017.
 */
public class BerlinClock {
    
    /**
     * Transforms input time in the simple string format HH:mm:ss to Berlin Clock representation as BitSet
     * @param time input time
     * @return Berlin's clock format as BitSet
     * @throws BerlinClockException if illegal input time string
     */
    public static BitSet toBerlinClock(String time) throws BerlinClockException {
        return toBerlinClock(time, "HH:mm:ss");
    }
    
    /**
     * Transforms input time by specified time format to Berlin Clock representation as BitSet
     * @param time input time
     * @return Berlin's clock format as BitSet
     * @throws BerlinClockException if illegal input time string or time format
     */
    public static BitSet toBerlinClock(String time, String format) throws BerlinClockException {
        try {
            if(format == null) {
                throw new BerlinClockException("Invalid input time format");
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(time);
            Calendar gc = new GregorianCalendar();
            gc.setTime(date);

            return toBerlinClock(gc.get(Calendar.HOUR_OF_DAY), gc.get(Calendar.MINUTE), gc.get(Calendar.SECOND));
            
        } catch(Exception e) {
            throw new BerlinClockException("Invalid input time string");
        }
        
    }
    
    /**
     * Transforms input time wrapped into primitive integer values (h, m, s) to Berlin Clock representation as BitSet
     * @param h hours
     * @param m minutes
     * @param s seconds
     * @return Berlin's clock format as BitSet
     * @throws BerlinClockException if illegal input time values
     */
    public static BitSet toBerlinClock(int h, int m, int s) throws BerlinClockException {
    
        if(h < 0 || h > 23 || m < 0 || m > 59 || s < 0 || s > 59) { // simple check
            throw new BerlinClockException("Invalid input time string");
        }
        
        BitSet bits = new BitSet(1 /* biggest yellow lamp = second's blinker */ +
                4 + /* big red lamps = 5 hour indicator */
                4 + /* big red lamps = 1-4 hour indicators */
                11 + /* small yellow/red lamps =  5 minute indicators */
                4 /* big yellow lamps = 1-4 minute indicators */
        ); // total = 24 lamps
    
        bits.set(0, s % 2 == 1); // indicates odd second
    
        int hoursBitsX5 = h / 5; // count of 5-hour red lamps
        int hoursBitsX1 = h % 5; // count of 1-hour red lamps
    
        bits.set(1, 1 + hoursBitsX5);
        bits.set(5, 5 + hoursBitsX1);
    
        int minutesBitsX5 = m / 5; // the same as hours
        int minutesBitsX1 = m % 5;
    
        bits.set(9, 9 + minutesBitsX5);
        bits.set(20, 20 + minutesBitsX1);

        return bits;
    }
    
    /**
     * Presents parse bits as string in time format "HH:mm" (without seconds because of data loss)
     * @param bits input time in BerlinClock format wrapped into BitSet
     * @return String human-readable string with time
     * @throws BerlinClockException if input BitSet is invalid and not supports BerlinClock's format
     */
    public static String parseBerlinClock(BitSet bits) throws BerlinClockException {
        try {
            int h = bits.get(1, 5).cardinality() * 5 + bits.get(5, 9).cardinality();
            int m = bits.get(9, 20).cardinality() * 5 + bits.get(20, 24).cardinality();
            return String.format("%02d", h) + ":" + String.format("%02d", m);
        } catch(IndexOutOfBoundsException e) {
            throw new BerlinClockException("Input BitSet is invalid - bit set is not supports BerlinClock's format");
        }
    }
    
    public static void main(String[] args) {
        BitSet berlinClockBits = BerlinClock.toBerlinClock("07:01:00");
        String time = BerlinClock.parseBerlinClock(berlinClockBits);
        System.err.println(time);
    }
    
}
