package com.kc.dragonball_kc_avanzado.utils

object Validator {
    /**
     * Check if a given string is a valid email (text@text.1+characters)
     * @param str The mail to be validated.
     * @return Boolean indicating if a mail is valid.
     */
    fun validateEmail(str: String) = str.matches("^[A-Za-z0-9+_.-]+@(.+)\\..+\$".toRegex())

    /**
     * Check if a given string is a valid password (password length > 2)
     * I can't enforce a proper strong password validation as no rules were enforced for them at
     * creation time, doing it now will cause some accounts to be inaccessible
     * @param str The password to be validated.
     * @return Boolean indicating if a password is valid.
     */
    fun validatePassword(str: String) = str.length > 2
}