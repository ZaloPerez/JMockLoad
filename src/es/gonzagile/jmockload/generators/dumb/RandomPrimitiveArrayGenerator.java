package es.gonzagile.jmockload.generators.dumb;

import es.gonzagile.jmockload.constraints.FieldConstraint;
import es.gonzagile.jmockload.generators.ValueGenerator;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

public class RandomPrimitiveArrayGenerator<T> implements ValueGenerator<Object> {
    private final Class<T> primitiveType;
    private final ValueGenerator<T> generator;
    private final int minlen;
    private final int maxlen;
    private final Random r = new Random();

    public RandomPrimitiveArrayGenerator(Class<T> primitiveType, ValueGenerator<T> generator, int size) {
        this(primitiveType, generator, size, size);
    }

    public RandomPrimitiveArrayGenerator(Class<T> primitiveType, ValueGenerator<T> generator, int minlen, int maxlen) {
        if (!primitiveType.isPrimitive()) {
            throw new IllegalArgumentException("Non-primitives are not allowed: " + primitiveType);
        }
        this.primitiveType = primitiveType;
        this.generator = generator;
        this.minlen = minlen;
        this.maxlen = maxlen;
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
        int len = minlen == maxlen ? minlen : r.nextInt(minlen, maxlen);
        Object array = Array.newInstance(primitiveType, len);
        for (int i = 0; i < len; i++) {
            Object value = generator.generate(i, constraints);
            Array.set(array, i, value);
        }
        return array;
    }
}
