package es.gonzagile.jmockload.constraints;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

/**
 * A constraint used to check that all Enum generated values are on a list of allowed values.
 * @param <E>
 */
public final class AllowedEnumValuesConstraint<E extends Enum<E>> implements FieldConstraint {

    private final Set<E> allowed;

    /**
     * Constructor that sets the list of allowed Enum values to be generated.
     * @param allowedValues The list of allowed Enum values. It must have at least 1 element.
     */
    public AllowedEnumValuesConstraint(E... allowedValues) {
        if (allowedValues == null || allowedValues.length == 0) {
            throw new IllegalArgumentException("At least one enum constant must be allowed");
        }
        this.allowed = EnumSet.copyOf(Arrays.asList(allowedValues));
    }

    @Override
    public boolean isValid(Object value) {
        if (value == null) return false;
        if (!allowed.iterator().next().getClass().isInstance(value)) return false;
        return allowed.contains(value);
    }
}
