package com.blacksw.basicsample;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/***
 * EmailValidator 단위 테스트
 */
public class EmailValidatorTest {

   @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
       assertTrue(EmailValidator.isValidEmail("name@email.com"));
   }

   @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
       assertTrue(EmailValidator.isValidEmail("name@email.co.uk"));
   }

   @Test
    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
       assertFalse(EmailValidator.isValidEmail("name@email"));
   }

   @Test
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
       assertFalse(EmailValidator.isValidEmail("name@email..com"));
   }

   @Test
    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
       assertFalse(EmailValidator.isValidEmail("@gmail.com"));
   }

   @Test
    public void emailValidator_EmptyString_ReturnsFalse() {
       assertFalse(EmailValidator.isValidEmail(""));
   }

   @Test
    public void emailValidator_NullEmail_ReturnsFalse() {
       assertFalse(EmailValidator.isValidEmail(null));
   }

}
