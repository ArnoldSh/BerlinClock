package org.time.core;

import org.time.code.CodeWrapper;
import org.time.exception.TimeEncodingException;

/**
 * Provides basic functionality of encoding/decoding time by specified algorithms
 */
public interface TimeCode {
    /**
     * Encodes time string by default format "HH:mm:ss"
     * @param time input time string
     * @return code wrapper
     * @throws TimeEncodingException if input time string is invalid
     */
    CodeWrapper encodeTime(String time) throws TimeEncodingException;
    
    /**
     * Encodes time string by specified time format (@see {@link java.text.SimpleDateFormat})
     * @param time input time string
     * @param format time format for parse
     * @return code wrapper
     * @throws TimeEncodingException if input time string or time format is invalid
     */
    CodeWrapper encodeTime(String time, String format) throws TimeEncodingException;
    
    /**
     * Encodes time specified by three integer values of hours, minutes and seconds
     * @param h hours
     * @param m minutes
     * @param s seconds
     * @return codeWrapper
     * @throws TimeEncodingException if input values are invalid - hours out of range [0..24) and minutes/seconds out of range [0..60)
     */
    CodeWrapper encodeTime(int h, int m, int s) throws TimeEncodingException;
    
    /**
     * Decodes code into human-readable time string
     * @param codeWrapper wrapper with binary code set of encoded time
     * @return time string in default "HH:mm:ss" format
     * @throws TimeEncodingException if input binary code set is invalid
     */
    String decodeTime(CodeWrapper codeWrapper) throws TimeEncodingException;
    
    /**
     * Decodes code into human-readable time string
     * @param codeWrapper wrapper with binary code set of encoded time
     * @param format time format (@see {@link java.text.SimpleDateFormat})
     * @return time string by specified time format
     * @throws TimeEncodingException if input binary code set is invalid
     */
    String decodeTime(CodeWrapper codeWrapper, String format) throws TimeEncodingException;
}
