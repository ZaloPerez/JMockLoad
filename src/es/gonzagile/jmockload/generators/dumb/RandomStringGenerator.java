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
        Random random = new Random();
        int length = r.nextInt(maxlength - minlength +1) + minlength;
        for(int i = 0; i < length; i++) {
            sb.append(random.nextBoolean() ? (char) ('a' + random.nextInt(26)) :
                    random.nextBoolean() ? (char) ('A' + random.nextInt(26)) : (char) ('0' + random.nextInt(9)));
        }
        return sb.toString();
    }
}
