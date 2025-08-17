package es.gonzagile.jmockload.constraints;

/**
 * Base interface which all field constraints must implement.
 */
public sealed interface FieldConstraint permits StringCharactersDictionaryConstraint, StringLengthConstraint, StringSuffixConstraint, AllowedEnumValuesConstraint, ForbiddenEnumValuesConstraint {
    /**
     * Checks wether the param passes all validations of the constraint or not
     * @param value the param to be checked
     * @return true if param passes all validations of the constraint
     */
    boolean isValid(Object value);
}
