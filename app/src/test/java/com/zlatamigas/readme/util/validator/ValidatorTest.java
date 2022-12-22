package com.zlatamigas.readme.util.validator;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidatorTest {

    @Test
    public static void validateCorrectEmail(){
        assertTrue(Validator.validateEmail("user@mail.ru"));
    }

    @Test
    public static void validateIncorrectEmail(){
        assertFalse(Validator.validateEmail("user@.ru"));

    }

    @Test
    public static void validateCorrectPassword(){
        assertTrue(Validator.validatePassword("12345"));
    }

    @Test
    public static void validateIncorrectPassword(){
        assertFalse(Validator.validatePassword("123456"));
    }
}