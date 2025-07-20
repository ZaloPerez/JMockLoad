package es.gonzagile.jmockload.generators.dumb;

import es.gonzagile.jmockload.generators.ValueGenerator;

import java.util.Random;

/**
 * Sets a Long with a randm value to the specified field. A range of min and max possible value can be set.
 */
public final class RandomLongGenerator  implements ValueGenerator<Long> {
    private final long min;
    private final long max;
    private final Random r = new Random();

    /**
     * Constructor that generates a Long with a random value.
     */
    public RandomLongGenerator() {
        this.min = Long.MIN_VALUE;
        this.max = Long.MAX_VALUE;
    }

    /**
     * Constructor that generates a Long with a random value within the specified range.
     * @param min min allowed value.
     * @param max max allowed value.
     */
    public RandomLongGenerator(long min, long max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Generates the random Long value.
     * @return a Long with a random value.
     */
    @Override
    public Long generate() {
        return r.nextLong(min, max);
    }
}