package fr.minemobs.luckyblock;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class JsonRegexConvertor {

    private JsonRegexConvertor() {}

    private static final List<Character> regexChar = List.of('*', '+', '?', '^', '$');

    private static String toStringPattern(String regex) {
        try {
            Pattern.compile(regex);
            return regex;
        } catch (PatternSyntaxException e) {
            String json = regex;
            for (int i = 0; i < json.toCharArray().length; i++) {
                if((i == 0 || json.charAt(i - 1) != '\\') && regexChar.contains(json.charAt(i))) {
                    json = json.substring(0, i) + "\\\\" + json.substring(i);
                }
            }
            return json;
        }
    }

    public static Pattern toPattern(String regex) {
        return Pattern.compile(toStringPattern(regex), Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    }

    public static Matcher toMatcher(String regex, String input) {
        return toPattern(regex).matcher(input);
    }
}
