package com.zlatamigas.readme.util.validator;

public class Validator {
    public static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String PASSWORD_PATTERN = "^[\\w]{6,20}$";

    public static boolean validateEmail(String email){
        return email.matches(EMAIL_PATTERN);
    }

    public static boolean validatePassword(String password){
        return password.matches(PASSWORD_PATTERN);
    }
}
