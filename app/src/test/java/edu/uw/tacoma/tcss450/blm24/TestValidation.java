package edu.uw.tacoma.tcss450.blm24;

import org.junit.Test;

import edu.uw.tacoma.tcss450.blm24.megaphone.login.Validation;

public class TestValidation {

    @Test
    public void testValidEmail() {
        assert(Validation.validateEmail("name@domain.extension"));
    }

    @Test
    public void testInvalidEmailNoExtension() {
        assert(!Validation.validateEmail("name@domain"));
    }

    @Test
    public void testInvalidEmailNoName() {
        assert(!Validation.validateEmail("@domain.extension"));
    }

    @Test
    public void testInvalidEmailDomain() {
        assert(!Validation.validateEmail("name@domain"));
    }

    @Test
    public void testValidPassword() {
        assert Validation.validatePassword("123456");
    }

    @Test
    public void testShortPassword() {
        assert !Validation.validatePassword("12345");
    }



}
