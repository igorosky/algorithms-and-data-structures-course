package Tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Ex2.PascalIterator;

public class Ex2 {
    @Test
    public void FirstThousand() {
        final List<Integer> values = new ArrayList<>();
        values.add(1);
        for(int test = 1; test <= 1000; ++test) {
            int i = 0;
            for(final int val : PascalIterator.newPascalIterator(test).get()) {
                assertEquals((int)values.get(i++), val);
            }
            assertEquals(values.size(), i);

            values.add(1);
            for(int j = values.size() - 2; j > 0; --j) {
                values.set(j, values.get(j) + values.get(j - 1));
            }
        }
    }
}
