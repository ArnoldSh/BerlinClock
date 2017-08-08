package org.time.exception;

/**
 * Exception occurring while timecode decoding, for example, invalid input binary code set.
 */
public class TimeDecodingException extends RuntimeException {
    public TimeDecodingException(String message) {
        super(message);
    }
}
