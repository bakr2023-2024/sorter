package test;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import main.Sorter;
import main.SortingAlgs;

public class SorterTest {
    private final int N = 10;
    private final Random rand = new Random();
    private final int[] arr = new int[N];
    private final Sorter sorter = new Sorter();

    @BeforeEach
    void setArray() {
        for (int i = 0; i < N; i++)
            arr[i] = rand.nextInt(1000);
    }

    @ParameterizedTest
    @EnumSource(SortingAlgs.class)
    void testAlg(SortingAlgs alg) {
        int[] copy = Arrays.copyOf(arr, N);
        sorter.sort(arr, alg);
        Arrays.sort(copy);
        assertArrayEquals(copy, arr);
    }
}
