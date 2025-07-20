package es.gonzagile.jmockload.generators.dumb;

import es.gonzagile.jmockload.generators.ValueGenerator;

/**
 * Sets the specified value to the specified field.
 * @param <T> fixed valued to be set
 */
public final class FixedValueGenerator<T> implements ValueGenerator<T> {
    private final T value;

    public FixedValueGenerator(T value) {
        this.value = value;
    }

    @Override
    public T generate() {
        return value;
    }
}
