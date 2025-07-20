package es.gonzagile.jmockload.generators.smart;

import es.gonzagile.jmockload.commons.CommonCharsets;
import es.gonzagile.jmockload.constraints.FieldConstraint;
import es.gonzagile.jmockload.constraints.StringCharactersDictionaryConstraint;
import es.gonzagile.jmockload.constraints.StringLengthConstraint;
import es.gonzagile.jmockload.constraints.StringSuffixConstraint;
import es.gonzagile.jmockload.generators.ValueGenerator;

import java.util.List;
import java.util.Random;
import java.util.Set;

public final class SmartStringGenerator implements ValueGenerator<String> {
    private final Random r = new Random();

    @Override
    public String generate() {
        return generate(0, List.of());
    }

    @Override
    public String generate(int index) {
        return generate(index, List.of());
    }

    @Override
    public String generate(int index, List<FieldConstraint> constraints) {
        int minlength = 10;
        int maxlength = 10;
        Set<Character> dictionary = CommonCharsets.ALPHANUMERIC;
        Set<Character> suffixdictionary = null;
        String suffix = "";
        boolean splitterbasedsuffix = false;

        for (FieldConstraint c : constraints) {
            if (c instanceof StringLengthConstraint slc) {
                minlength = slc.getMinlength();
                maxlength = slc.getMaxlength();
            } else if(c instanceof StringCharactersDictionaryConstraint scdc) {
                dictionary = scdc.getDictionary();
            } else if(c instanceof StringSuffixConstraint ssc) {
                suffix = ssc.getSuffix();
                splitterbasedsuffix = ssc.isSplitterbased();
                suffixdictionary = ssc.getSuffixdictionary();
                if(suffix.length() > maxlength) throw new IllegalStateException("suffix's length can't be greater than default generated string's maxlength (10)");
            }
        }

        int length;
        if(minlength != maxlength) {
            length = minlength + suffix.length() > maxlength
                    ? new Random().nextInt(suffix.length() + minlength - maxlength) + 1
                    : new Random().nextInt(maxlength - suffix.length()) +1;
        } else {
            length = maxlength - suffix.length();
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(dictionary.toArray()[r.nextInt(dictionary.size())]);
        }
        if(!splitterbasedsuffix) {
            sb.append(suffix);
        } else {
            suffixdictionary = null == suffixdictionary ? dictionary : suffixdictionary;
            sb.append(suffix.charAt(0));
            for(int i = 1; i < suffix.length(); i++) {
                sb.append(suffixdictionary.toArray()[r.nextInt(suffixdictionary.size())]);
            }
        }
        return sb.toString();
    }
}
