package es.gonzagile.jmockload.commons;


import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This is a dictionary class of letters, numbers and symbols
 * grouped in a way to make them easy to use.
 */
public final class CommonCharsets {

    /**
     * All lowercased letters
     */
    public static final Set<Character> LOWERCASE = Set.of(
            'a','b','c','d','e','f','g','h','i','j','k','l','m',
            'n','o','p','q','r','s','t','u','v','w','x','y','z'
    );

    /**
     * All uppercased letters
     */
    public static final Set<Character> UPPERCASE = Set.of(
            'A','B','C','D','E','F','G','H','I','J','K','L','M',
            'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
    );

    /**
     * All numeric chars
     */
    public static final Set<Character> NUMBERS = Set.of(
            '0','1','2','3','4','5','6','7','8','9'
    );

    /**
     * A group of the most common symbols
     */
    public static final Set<Character> SYMBOLS = Set.of(
            '@', '#', '$', '%', '&', '-', '_', '+', '=', '!', '?',
            '.', ',', ':', ';', '/', '\\', '|', '*', '(', ')',
            '[', ']', '{', '}', '<', '>', ' '
    );

    public static final Set<Character> DEFAULT;
    public static final Set<Character> LETTERS;
    public static final Set<Character> ALPHANUMERIC;

    //I do this because I'm lazy as fuck, may remove it in future versions
    static {
        Set<Character> combined = new LinkedHashSet<>();
        combined.addAll(LOWERCASE);
        combined.addAll(UPPERCASE);
        LETTERS = Set.copyOf(combined);

        combined.addAll(NUMBERS);
        ALPHANUMERIC = Set.copyOf(combined);

        combined.addAll(SYMBOLS);
        DEFAULT = Set.copyOf(combined);
    }

    private CommonCharsets() {}
}
