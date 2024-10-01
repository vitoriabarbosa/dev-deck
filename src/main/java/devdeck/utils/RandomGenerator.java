package devdeck.utils;

import java.util.Random;

public class RandomGenerator {
    private Random random;

    public RandomGenerator() {
        random = new Random();
    }

    // Returns a random integer
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    // Returns a random double between 0.0 and 1.0
    public double nextDouble() {
        return random.nextDouble();
    }

    // Shuffles an array of integers
    public void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
