package es.gonzagile.jmockload.generators.dumb;

import es.gonzagile.jmockload.generators.ValueGenerator;

import java.util.Random;

/**
 * Sets a random boolean value to the specified field.
 */
public final class RandomBooleanGenerator implements ValueGenerator<Boolean> {
    private final Random r = new Random();

    public RandomBooleanGenerator() {
    }

    /**
     * Generates a random boolean value.
     * @return
     */
    @Override
    public Boolean generate() {
        return r.nextBoolean();
    }
}