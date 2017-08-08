package org.time.code.impl;

import org.time.code.CodeWrapper;

import java.util.BitSet;

/**
 * Simple implementation of Berlin Clock bits representation.
 */
public class BerlinClockBits implements CodeWrapper {

    private BitSet bitSet;

    public BerlinClockBits(BitSet bitSet) {
        this.bitSet = bitSet;
    }
    
    @Override
    public String toBinaryString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 24; i++) {
            sb.append(bitSet.get(i) ? "1" : "0");
        }
        return sb.toString();
    }
}
