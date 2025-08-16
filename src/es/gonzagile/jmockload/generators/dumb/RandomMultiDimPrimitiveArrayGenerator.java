package es.gonzagile.jmockload.generators.dumb;

import es.gonzagile.jmockload.constraints.FieldConstraint;
import es.gonzagile.jmockload.generators.ValueGenerator;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

public class RandomMultiDimPrimitiveArrayGenerator<T> implements ValueGenerator<Object> {
    private final Class<?> primitiveType;
    private final ValueGenerator<T> valuesGenerator;
    private final int[] dimensions;
    private final Random r = new Random();

    public RandomMultiDimPrimitiveArrayGenerator(Class<T> primitiveType, ValueGenerator<T> valuesGenerator, int... dimensions) {
        if (!primitiveType.isPrimitive()) {
            throw new IllegalArgumentException("Non-primitives are not allowed: " + primitiveType);
        }
        this.primitiveType = primitiveType;
        this.valuesGenerator = valuesGenerator;
        this.dimensions = dimensions;
    }

    @Override
    public Object generate() {
        return this.generate(0, List.of());
    }

    @Override
    public Object generate(int index) {
        return this.generate(index, List.of());
    }

    @Override
    public Object generate(int index, List<FieldConstraint> constraints) {
        Object array = Array.newInstance(primitiveType, dimensions);
        fillArray(array, 0, constraints);
        return array;
    }

    private void fillArray(Object array, int level, List<FieldConstraint> constraints) {
        int length = Array.getLength(array);
        if (level == dimensions.length - 1) {
            for (int i = 0; i < length; i++) {
                Object value = valuesGenerator.generate(i, constraints);
                Array.set(array, i, value);
            }
        } else {
            for (int i = 0; i < length; i++) {
                Object subArray = Array.get(array, i);
                fillArray(subArray, level + 1, constraints);
            }
        }
    }
}
