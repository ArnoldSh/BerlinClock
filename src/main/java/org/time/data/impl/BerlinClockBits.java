package org.time.data.impl;

import org.time.data.Bits;

import java.util.BitSet;

/**
 * Created by ashamsutdinov on 07.08.2017.
 * Simple implementation of Berlin Clock bits representation.
 */
public class BerlinClockBits implements Bits<BitSet> {

    private BitSet data;

    public BerlinClockBits(BitSet data) {
        this.data = data;
    }

    @Override
    public void set(BitSet bits) {
        this.data = bits;
    }

    @Override
    public BitSet get() {
        return this.data;
    }

    @Override
    public boolean[] toBooleanBits() {
        boolean[] booleanBits = new boolean[24];
        for(int i = 0; i < 24; i++) {
            booleanBits[i] = data.get(i);
        }
        return booleanBits;
    }

    @Override
    public byte[] toByteBits() {
        byte[] byteBits = new byte[24];
        for(int i = 0; i < 24; i++) {
            byteBits[i] = data.get(i) ? (byte) 1 : (byte) 0;
        }
        return byteBits;
    }

    @Override
    public String toBinaryString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 24; i++) {
            sb.append(data.get(i) ? "1" : "0");
        }
        return sb.toString();
    }
}
