package org.time.code;

/**
 * Base container of abstract sequence of binary code/bits
 */
public interface CodeWrapper {
    /**
     * String representation of binary data set
     * @return String with raw data
     */
    String toBinaryString();
}
