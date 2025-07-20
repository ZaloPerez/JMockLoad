package es.gonzagile.jmockload.constraints;

/**
 * A constraint used to check the length of a String
 */
public final class StringLengthConstraint implements FieldConstraint {
    private final int minlength;
    private final int maxlength;

    /**
     * Sets the exact length a String must have in order to pass the validation
     * @param length exact allowed length of the String
     */
    public StringLengthConstraint(int length) {
        this.maxlength = length;
        this.minlength = length;
    }

    /**
     * Sets a range of allowed valid lengths a String must have in order to pass the validation
     * @param minlength min length allowed
     * @param maxlength max length allowed
     */
    public StringLengthConstraint(int minlength, int maxlength) {
        if(minlength > maxlength) throw new IllegalArgumentException("minlength can't be greather than maxlength");
        this.minlength = minlength;
        this.maxlength = maxlength;
    }

    /**
     * Checks wether the String has the allowed length or not.
     * @param value the param to be checked. It must be a String, otherwise an exception will be thrown.
     * @return true if the length of the String is allowed.
     */
    @Override
    public boolean isValid(Object value) {
        if(!(value instanceof String)) throw new IllegalArgumentException("the param of the method must be a String");
        return ((String) value).length() >= minlength && ((String) value).length() <= maxlength;
    }

    public int getLength() {
        return maxlength;
    }

    public int getMinlength() {
        return minlength;
    }

    public int getMaxlength() {
        return maxlength;
    }
}
