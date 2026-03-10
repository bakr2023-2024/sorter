package test;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import main.Sorter;
import main.SortingAlgs;

public class SorterTest {
    private final int N = 100;
    private final Random rand = new Random();
    private final Sorter sorter = new Sorter();

    @ParameterizedTest
    @EnumSource(SortingAlgs.class)
    void testAlg(SortingAlgs alg) {
        for (int x = 0; x < 100; x++) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++)
            arr[i] = rand.nextInt(2000);
        int[] copy = Arrays.copyOf(arr, N);
        sorter.sort(arr, alg);
        Arrays.sort(copy);
        assertArrayEquals(copy, arr);
    }
}
}
