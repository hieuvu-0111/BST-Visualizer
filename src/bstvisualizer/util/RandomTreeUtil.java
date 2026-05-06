package bstvisualizer.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Generates a random set of unique integers for the "Random" button.
 *
 * Returns them in the order they were generated so that inserting them
 * one by one produces a reasonably balanced (non-degenerate) tree.
 */
public class RandomTreeUtil {

    private RandomTreeUtil() {} // utility class

    /**
     * @param count  number of values to generate
     * @param min    inclusive lower bound
     * @param max    inclusive upper bound
     * @return array of {@code count} distinct random ints in [min, max]
     */
    public static int[] generateRandom(int count, int min, int max) {
        Random rng = new Random();
        Set<Integer> seen = new HashSet<>();
        int[] result = new int[count];
        int i = 0;
        while (i < count) {
            int v = min + rng.nextInt(max - min + 1);
            if (seen.add(v)) {
                result[i++] = v;
            }
        }
        return result;
    }
}