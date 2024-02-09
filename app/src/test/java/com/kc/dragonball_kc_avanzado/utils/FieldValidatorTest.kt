package com.kc.dragonball_kc_avanzado.utils

import org.junit.Assert.*

import org.junit.Test

class FieldValidatorTest {

    // SUT
    private val fieldValidator: FieldValidator = FieldValidator()
    @Test
    fun `WHEN invalid emails THEN false`() {
        // Invalid emails
        arrayOf("fail", "fail@", "fail@fail", "fail@fail.").map {
            assertEquals(false, fieldValidator.validateEmail(it))
        }
    }

    @Test
    fun `WHEN valid emails THEN true`() {
        // Valid emails
        arrayOf(
            "simple@example.com",
            "very.common@example.com",
            "abc@example.co.uk",
            "disposable.style.email.with+symbol@example.com",
            "other.email-with-hyphen@example.com",
            "fully-qualified-domain@example.com",
        ).map {
            assertEquals(true, fieldValidator.validateEmail(it))
        }
    }

    @Test
    fun `WHEN password length greater than 2 THEN true`() {
        // Not gonna separate this one as it's too trivial
        arrayOf(
            "foo",
            "foobar1",
            "fooBarBaz*",
            ).map {
                assertEquals(true, fieldValidator.validatePassword(it))
        }
    }
}