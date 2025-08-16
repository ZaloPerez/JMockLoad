package es.gonzagile.jmockload.generators.dumb;

import es.gonzagile.jmockload.generators.ValueGenerator;

import java.util.Random;

/**
 * Sets a String with random chars and specified length to the specified field. A range of min and max possible length can be set.
 */
public final class RandomStringGenerator implements ValueGenerator<String> {
    private final int minlength;
    private final int maxlength;
    private final Random r = new Random();

    /**
     * Constructor that generates a String with random chars and specified length.
     */
    public RandomStringGenerator(int length) {
        this.minlength = length;
        this.maxlength = length;
    }

    /**
     * Constructor that generates a String with random chars and variable length within the specified range.
     */
    public RandomStringGenerator(int minlength, int maxlength) {
        this.minlength = minlength;
        this.maxlength = maxlength;
    }

    /**
     * Generates the random String value.
     * @return a String with random chars and fixed or variable length.
     */
    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        int length = r.nextInt(maxlength - minlength +1) + minlength;
        for(int i = 0; i < length; i++) {
            sb.append(r.nextBoolean() ? (char) ('a' + r.nextInt(26)) :
                    r.nextBoolean() ? (char) ('A' + r.nextInt(26)) : (char) ('0' + r.nextInt(10)));
        }
        return sb.toString();
    }
}
