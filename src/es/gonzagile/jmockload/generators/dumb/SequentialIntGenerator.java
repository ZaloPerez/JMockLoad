package es.gonzagile.jmockload.generators.dumb;

import es.gonzagile.jmockload.generators.ValueGenerator;

/**
 * Sets an Integer with increasing sequential value to the specified field.
 */
public final class SequentialIntGenerator implements ValueGenerator<Integer> {
    private final int start;

    /**
     * Constructor that generates an Integer's value counting from 0.
     */
    public SequentialIntGenerator() {
        this(0);
    }

    /**
     * Constructor that generates an Integer's value counting from the defined starting number.
     */
    public SequentialIntGenerator(int start) {
        this.start = start;
    }

    /**
     * Generates the Integer's value depending on the start value.
     * @return an Integer with the start value.
     */
    @Override
    public Integer generate() {
        return start;
    }

    /**
     * Generates the Integer's value depending on the start value and an index value passed by parameters.
     * @param index the index of the value to be generated.
     * @return the addition of the start value and the index.
     */
    @Override
    public Integer generate(int index) {
        return this.start + index;
    }
}
