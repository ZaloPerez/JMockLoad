package es.gonzagile.jmockload.constraints;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

/**
 * A constraint used to check that none of the Enum generated values are on a list of forbidden values.
 * @param <E>
 */
public final class ForbiddenEnumValuesConstraint <E extends Enum<E>> implements FieldConstraint {

    private final Set<E> forbidden;

    /**
     * Constructor that sets the list of forbidden Enum values not to be generated.
     * @param forbiddenValues The list of forbidden Enum values. It must have at least 1 element.
     */
    @SafeVarargs
    public ForbiddenEnumValuesConstraint(E... forbiddenValues) {
        if (forbiddenValues == null || forbiddenValues.length == 0) {
            throw new IllegalArgumentException("At least one enum constant must be forbidden");
        }
        this.forbidden = EnumSet.copyOf(Arrays.asList(forbiddenValues));
    }

    @Override
    public boolean isValid(Object value) {
        if (value == null) return true; // null is allowed unless you later forbid it
        if (!forbidden.iterator().next().getClass().isInstance(value)) return true;
        return !forbidden.contains(value);
    }
}
