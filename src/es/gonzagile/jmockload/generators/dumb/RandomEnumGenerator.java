package es.gonzagile.jmockload.generators.dumb;

import es.gonzagile.jmockload.constraints.FieldConstraint;
import es.gonzagile.jmockload.generators.ValueGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Sets an Enum with a random value of the specified type to the specified field.
 */
public class RandomEnumGenerator<T extends Enum<T>> implements ValueGenerator<T> {
    private final Class<T> enumType;
    private final Random random = new Random();

    /**
     * Constructor that sets the type of the Enum to be generated.
     * @param enumType The type of the Enum to be generated.
     */
    public RandomEnumGenerator(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public T generate() {
        return this.generate(0, List.of());
    }

    @Override
    public T generate(int index) {
        return this.generate(index, List.of());
    }

    @Override
    public T generate(int index, List<FieldConstraint> constraints) {
        T[] constants = enumType.getEnumConstants();
        if (constants == null || constants.length == 0) {
            throw new IllegalStateException("Enum " + enumType + " has no constants");
        }

        List<T> candidates = Arrays.stream(constants)
                .filter(c -> constraints.stream().allMatch(con -> con.isValid(c)))
                .toList();

        if (candidates.isEmpty()) {
            throw new IllegalStateException("No valid enum values for " + enumType.getSimpleName() +
                    " with given constraints: " + constraints);
        }

        return candidates.get(random.nextInt(candidates.size()));
    }
}
