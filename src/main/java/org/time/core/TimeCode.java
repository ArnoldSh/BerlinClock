package org.time.core;

import org.time.data.Bits;
import org.time.exception.TimeEncodingException;

/**
 * Created by ashamsutdinov on 07.08.2017.
 * Provides basic functionality of encoding/decoding time
 */
public interface TimeCode<T> {
    public Bits<T> encodeTime(String time) throws TimeEncodingException;
    public Bits<T> encodeTime(String time, String format) throws TimeEncodingException;
    public Bits<T> encodeTime(int h, int m, int s) throws TimeEncodingException;
    public String decodeTime(Bits<T> bits) throws TimeEncodingException;
}
