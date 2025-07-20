package es.gonzagile.jmockload.constraints;

import java.util.Set;

/**
 * A constraint used to check if all the characters of a certain string are present on the defined char dictionary.
 */
public final class StringCharactersDictionaryConstraint implements FieldConstraint {
    private final Set<Character> dictionary;

    /**
     * Constructor that gets a char dictionary stored in a set in order to validate the String.
     * @param dictionary a set of characters used as dictionary. Can use CommonCharset to pick between some predefined dictionaries.
     */
    public StringCharactersDictionaryConstraint(Set<Character> dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Checks wether all the characters that form a String are present in the defined dictionary or not. Will return false if value is not a String.
     * @param value the param to be checked. It must be a String, otherwise an exception will be thrown.
     * @return true if all chars of the string are in the dictionary, false otherwise.
     */
    @Override
    public boolean isValid(Object value) {
        if(!(value instanceof String input)) throw new IllegalArgumentException("the param of the method must be a String");
        return input.chars().mapToObj(c -> (char) c).anyMatch(c -> !dictionary.contains(c));
    }

    public Set<Character> getDictionary() {
        return dictionary;
    }
}
