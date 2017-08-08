package org.time.exception;

/**
 * Exception occurring while timecode encoding, for example, invalid input time string or time format.
 */
public class TimeEncodingException extends RuntimeException {
    public TimeEncodingException(String message) {
        super(message);
    }
}
