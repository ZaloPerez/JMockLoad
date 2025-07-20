package es.gonzagile.jmockload.generators.dumb;

import es.gonzagile.jmockload.generators.ValueGenerator;

import java.util.Random;

/**
 * Sets an Integer with a random value to the specified field. A range of min and max possible value can be set.
 */
public final class RandomIntGenerator implements ValueGenerator<Integer> {
    private final int min;
    private final int max;
    private final Random r = new Random();

    /**
     * Constructor that generates an Integer with a random value.
     */
    public RandomIntGenerator() {
        this.min = Integer.MIN_VALUE;
        this.max = Integer.MAX_VALUE;
    }

    /**
     * Constructor that generates an Integer with a random value within the specified range.
     * @param min min allowed value.
     * @param max max allowed value.
     */
    public RandomIntGenerator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Generates the random Integer value.
     * @return an Integer with a random value.
     */
    @Override
    public Integer generate() {
        return r.nextInt(min, max);
    }
}
