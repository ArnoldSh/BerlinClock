package org.time.data;

/**
 * Created by ashamsutdinov on 07.08.2017.
 * Base container of abstract sequence of binary data (bits)
 */
public interface Bits<T> {
    public boolean[] toBooleanBits();
    public byte[] toByteBits();
    public String toBinaryString();
}
