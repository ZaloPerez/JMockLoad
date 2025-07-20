package es.gonzagile.jmockload.generators;

import es.gonzagile.jmockload.constraints.FieldConstraint;

import java.util.List;

@FunctionalInterface
public interface ValueGenerator<T> {
    T generate();

    default T generate(int index) {
        return generate();
    }

    default T generate(int index, List<FieldConstraint> constraints) {
        return generate(index);
    }
}
