package es.gonzagile.jmockload.generators.dumb;

import es.gonzagile.jmockload.constraints.FieldConstraint;
import es.gonzagile.jmockload.generators.ValueGenerator;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

public final class RandomArrayGenerator<T> implements ValueGenerator<T[]> {
    private final Class<T> componentType;
    private final ValueGenerator<T> generator;
    private final int minlen;
    private final int maxlen;
    private final Random r = new Random();

    public RandomArrayGenerator(Class<T> componentType, ValueGenerator<T> generator, int size) {
        this(componentType, generator, size, size);
    }

    public RandomArrayGenerator(Class<T> componentType, ValueGenerator<T> generator, int minlen, int maxlen) {
        if (componentType.isPrimitive()) {
            throw new IllegalArgumentException("Primitives are not allowed: " + componentType);
        }
        this.componentType = componentType;
        this.generator = generator;
        this.minlen = minlen;
        this.maxlen = maxlen;
    }

    @Override
    public T[] generate() {
        return generate(0, List.of());
    }

    @Override
    public T[] generate(int index) {
        return generate(index, List.of());
    }

    @Override
    public T[] generate(int index, List<FieldConstraint> constraints) {
        int len = minlen == maxlen ? minlen : r.nextInt(minlen, maxlen);
        T[] array = (T[]) Array.newInstance(componentType, len);
        for(int i = 0; i < len; i++) {
            array[i] = generator.generate(i, constraints);
        }
        return array;
    }
}
