package Tests;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import Ex1.Table;

public class Ex1 {
    @Test
    public void RandomTests() {
        final Random rand = new Random(System.currentTimeMillis());
        for(int test = 0; test < 10000; ++test) {
            final int size = rand.nextInt(1000);
            int[] data = new int[size];
            final Table<Integer> table = new Table<>(size);
            for(int i = 0; i < size; ++i) {
                final int value = rand.nextInt(2000000000);
                data[i] = value;
                table.set(i, value);
            }

            int i = 0;
            for(final int val : table) {
                assertEquals(data[i++], val);
            }
            assertEquals(size, i);
        }
    }
}
