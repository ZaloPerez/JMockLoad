package es.gonzagile.jmockload.constraints;

import java.util.Random;
import java.util.Set;

/**
 * A constraint used to check if a String has a certain suffix.
 */
public final class StringSuffixConstraint implements FieldConstraint {
    private final char splitter;
    private final String suffix;
    private final int maxlength;
    private final int minlength;
    private final boolean splitterbased;
    private final Set<Character> suffixdictionary;

    /**
     * Constructor used to set an exact suffix a String must end with in order to pass the validation.
     * @param suffix the exact allowed suffix.
     */
    public StringSuffixConstraint(String suffix) {
        if(null == suffix || suffix.isEmpty()) throw new IllegalArgumentException("Suffix can't be null nor empty");
        this.suffix = suffix;
        this.splitter = suffix.charAt(0);
        this.splitterbased = false;
        this.maxlength = suffix.length();
        this.minlength = suffix.length();
        this.suffixdictionary = null;
    }

    /**
     * Constructor used to set a splitter char that splits the main String and an unsetted suffix with an exact length.
     * @param splitter the splitter char.
     * @param length the allowed length of the suffix including the splitter char. 0 or lower will cause an exception.
     */
    public StringSuffixConstraint(char splitter, int length) {
        this(splitter, length, null);
    }

    /**
     * Constructor used to set a splitter char that splits the main String and an unsetted suffix with an exact length.
     * It also checks if all the chars of the suffix(excluding the splitter) are present in the defined dictionary.
     * @param splitter the splitter char.
     * @param length the allowed length of the suffix including the splitter char. 0 or lower will cause an exception.
     * @param suffixdictionary a dictionary of chars used to check if all the chars of the suffix are allowed.
     */
    public StringSuffixConstraint(char splitter, int length, Set<Character> suffixdictionary) {
        if(length <= 0) throw new IllegalArgumentException("Suffix's length can't be less than 1'");
        this.splitterbased = true;
        this.splitter = splitter;
        this.maxlength = length;
        this.minlength = length;
        StringBuilder sb = new StringBuilder();
        sb.append(splitter);
        sb.append("%".repeat(Math.max(0, length - 1)));
        this.suffix = sb.toString();
        this.suffixdictionary = suffixdictionary;
    }

    /**
     * Constructor used to set a splitter char that splits the main String and an unsetted suffix with a variable length.
     * It also checks if all the chars of the suffix(excluding the splitter) are present in the defined dictionary.
     * @param splitter the splitter char.
     * @param minlength the min length allowed. 0 or lower will cause and exception.
     * @param maxlength the max length allowed. if it is lower than minlength it will cause an exception.
     */
    public StringSuffixConstraint(char splitter, int minlength, int maxlength) {
        if(minlength <= 0) throw new IllegalArgumentException("minlength can't be less than 1'");
        if(minlength > maxlength) throw new IllegalArgumentException("minlength can't be greater than maxlength");
        this.splitterbased = true;
        this.splitter = splitter;
        this.minlength = minlength;
        this.maxlength = maxlength;
        StringBuilder sb = new StringBuilder();
        sb.append(splitter);
        sb.append("%".repeat(Math.max(0, new Random().nextInt(maxlength - minlength) + minlength)));
        this.suffix = sb.toString();
        this.suffixdictionary = null;
    }

    /**
     * Checks wether the String contains a valid suffix or not based on its length, its splitter char(if any) and/or the chars on it.
     * @param value the param to be checked. It must be a String, otherwise an exception will be thrown.
     * @return true if the suffix properties are allowed.
     */
    @Override
    public boolean isValid(Object value) {
        if(!(value instanceof String stringvalue)) throw new IllegalArgumentException("the param of the method must be a String");
        if(stringvalue.length() < minlength || stringvalue.length() > maxlength) return false;
        if(splitterbased) {
            if(minlength == maxlength) return stringvalue.charAt(maxlength-1) == splitter;
            for(int i = maxlength -1; i >= minlength; i--) {
                if(stringvalue.charAt(i) == splitter) return true;
            }
        } else {
            return stringvalue.substring(stringvalue.length() - suffix.length()).equals(suffix);
        }
        return true;
    }

    public String getSuffix() {
        return suffix;
    }

    public boolean isSplitterbased() {
        return splitterbased;
    }

    public Set<Character> getSuffixdictionary() {
        return suffixdictionary;
    }
}
