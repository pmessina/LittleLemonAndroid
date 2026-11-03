package com.coursera.littlelemon.users

import android.util.Patterns

sealed interface EmailValidation {
    data object Valid : EmailValidation
    data object Empty : EmailValidation
    data object InvalidFormat : EmailValidation
}

fun validateEmail(email: String): EmailValidation = when {
    email.isBlank() -> EmailValidation.Empty
    !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> EmailValidation.InvalidFormat
    else -> EmailValidation.Valid
}
