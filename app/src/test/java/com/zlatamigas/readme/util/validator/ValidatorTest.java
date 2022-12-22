package com.zlatamigas.readme.util.validator;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidatorTest {

    @Test
    public void validateCorrectEmail(){
        assertTrue(Validator.validateEmail("user@mail.ru"));
    }

    @Test
    public void validateIncorrectEmail(){
        assertFalse(Validator.validateEmail("user@ru"));

    }

    @Test
    public void validateCorrectPassword(){
        assertTrue(Validator.validatePassword("123456"));
    }

    @Test
    public void validateIncorrectPassword(){
        assertFalse(Validator.validatePassword("12345"));
    }
}
