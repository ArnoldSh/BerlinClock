package org.time.core.impl;

import org.time.core.TimeCode;
import org.time.code.impl.BerlinClockBits;
import org.time.code.CodeWrapper;
import org.time.exception.TimeDecodingException;
import org.time.exception.TimeEncodingException;

import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Berlin Clock implementation based on BitSet wrapper.
 */
public class BerlinClock implements TimeCode {
    
    /**
     * Transforms input time in the simple string format HH:mm:ss to Berlin Clock representation as BitSet
     * @param time input time
     * @return Berlin's time format as BitSet
     * @throws TimeEncodingException if illegal input time string
     */
    @Override
    public CodeWrapper encodeTime(String time) throws TimeEncodingException {
        return encodeTime(time, "HH:mm:ss");
    }
    
    /**
     * Transforms input time by specified time format to Berlin Clock representation as BitSet
     * @param time input time
     * @return Berlin's time format as BitSet
     * @throws TimeEncodingException if illegal input time string or time format
     */
    @Override
    public CodeWrapper encodeTime(String time, String format) throws TimeEncodingException {
        try {
            if(format == null) {
                throw new TimeEncodingException("Invalid input time format");
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(time);
            Calendar gc = new GregorianCalendar();
            gc.setTime(date);

            return encodeTime(gc.get(Calendar.HOUR_OF_DAY), gc.get(Calendar.MINUTE), gc.get(Calendar.SECOND));
            
        } catch(Exception e) {
            throw new TimeEncodingException("Invalid input time string");
        }
        
    }
    
    /**
     * Transforms input time wrapped into primitive integer values (h, m, s) to Berlin Clock representation as BitSet
     * @param h hours
     * @param m minutes
     * @param s seconds
     * @return Berlin's time format as BitSet
     * @throws TimeEncodingException if illegal input time values
     */
    @Override
    public CodeWrapper encodeTime(int h, int m, int s) throws TimeEncodingException {
    
        if(h < 0 || h > 23 || m < 0 || m > 59 || s < 0 || s > 59) { // simple check
            throw new TimeEncodingException("Invalid input time string");
        }
        
        BitSet bitSet = new BitSet( // total = 24 lamps
                1 /* biggest yellow lamp = second's blinker */ +
                    4 + /* big red lamps = 5 hour indicator */
                    4 + /* big red lamps = 1-4 hour indicators */
                    11 + /* small yellow/red lamps =  5 minute indicators */
                    4 /* big yellow lamps = 1-4 minute indicators */
        );

        bitSet.set(0, s % 2 == 1); // indicates odd second
    
        int hoursBitsX5 = h / 5; // count of 5-hour red lamps
        int hoursBitsX1 = h % 5; // count of 1-hour red lamps
    
        bitSet.set(1, 1 + hoursBitsX5);
        bitSet.set(5, 5 + hoursBitsX1);
    
        int minutesBitsX5 = m / 5; // the same as hours
        int minutesBitsX1 = m % 5;
    
        bitSet.set(9, 9 + minutesBitsX5);
        bitSet.set(20, 20 + minutesBitsX1);

        return new BerlinClockBits(bitSet);
    }
    
    /**
     * Presents parse bits as string in time format "HH:mm" (without seconds because of code loss)
     * @param codeWrapper input time in BerlinClock format wrapped into BitSet
     * @return String human-readable string with time
     * @throws TimeDecodingException if input BitSet is invalid and not supports BerlinClock's format
     */
    @Override
    public String decodeTime(CodeWrapper codeWrapper) throws TimeDecodingException {

        String bBits = codeWrapper.toBinaryString();
        int hoursBitsX5 = bBits.substring(1, 5).replaceAll("0", "").length();
        int hoursBitsX1 = bBits.substring(5, 9).replaceAll("0", "").length();
        int minutesBitsX5 = bBits.substring(9, 20).replaceAll("0", "").length();
        int minutesBitsX1 = bBits.substring(20, 24).replaceAll("0", "").length();
        try {
            int h = hoursBitsX5 * 5 + hoursBitsX1;
            int m = minutesBitsX5 * 5 + minutesBitsX1;
            return String.format("%02d", h) + ":" + String.format("%02d", m) + ":00";
        } catch(IndexOutOfBoundsException e) {
            throw new TimeEncodingException("Input BitSet is invalid - bit set is not supports BerlinClock's format");
        }
    }
    
    @Override
    public String decodeTime(CodeWrapper codeWrapper, String format) throws TimeEncodingException {
        throw new UnsupportedOperationException();
    }
    
}
