package edu.uw.tacoma.tcss450.blm24.megaphone;

import java.util.regex.Pattern;

public final class Validation {

    private Validation() { }

    private static final Pattern emailPattern;

    private static final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~" +
            "-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0" +
            "b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x0" +
            "9\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0" +
            "-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[" +
            "0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]" +
            "?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0" +
            "e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0" +
            "e-\\x7f])+)\\])";

    static {
        emailPattern = Pattern.compile(emailRegex);
    }

    public static final boolean validateEmail(String email) {
        return emailPattern.matcher(email).matches();
    }

    public static final boolean validatePassword(String password) {
        return password.length() >= 6;
    }

}
