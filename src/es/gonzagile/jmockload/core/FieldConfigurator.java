package es.gonzagile.jmockload.core;

import es.gonzagile.jmockload.constraints.FieldConstraint;
import es.gonzagile.jmockload.generators.ValueGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Configures a field in order to generate the expected value depending on the generator and the constraints used.
 * @param <T> Meant to be used by basic objects with no inheritance, nor collections or other objects as attributes.
 */
public final class FieldConfigurator<T> {

    private final String fieldName;
    private final JMockLoad<T> owner;
    private ValueGenerator<?> generator;
    private final List<FieldConstraint> constraints = new ArrayList<>();

    /**
     * Constructor used by JMockLoad to configure the field with the name passed as parameter.
     * @param fieldName the name of the field to be configured.
     * @param owner the JMockLoad which will configure the field.
     */
    FieldConfigurator(String fieldName, JMockLoad<T> owner) {
        this.fieldName = fieldName;
        this.owner = owner;
    }

    private FieldConfigurator() {
        this(null, null);
    }

    /**
     * Sets the generator to be used to generate the values of the field.
     * @param generator The generator to be used.
     * @return this.
     */
    public FieldConfigurator<T> generator(ValueGenerator<?> generator) {
        this.generator = generator;
        return this;
    }

    /**
     * Adds the constraint passed as parameter to the list of constraints to be used to evaluate the value of the field.
     * @param constraint The constraint to be used.
     * @return this.
     */
    public FieldConfigurator<T> constraint(FieldConstraint constraint) {
        this.constraints.add(constraint);
        return this;
    }

    /**
     * Adds the constraints passed as parameters to the list of constraints to be used to evaluate the value of the field.
     * @param constraints The constraints to be used.
     * @return this.
     */
    public FieldConfigurator<T> constraints(FieldConstraint... constraints) {
        this.constraints.addAll(Arrays.asList(constraints));
        return this;
    }

    /**
     * Applies both the generators and constraints previously added to the field.
     * @return JMockLoad.
     */
    public JMockLoad<T> apply() {
        if (generator != null) {
            owner.withCustomGenerator(fieldName, generator);
        }
        for (FieldConstraint c : constraints) {
            owner.withConstraint(fieldName, c);
        }
        return owner;
    }
}
