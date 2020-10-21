/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package kotlin.text

// actually \s is enough to match all whitespace, but \xA0 added because of different regexp behavior of Rhino used in Selenium tests
public actual fun Char.isWhitespace(): Boolean = toString().matches("[\\s\\xA0]")

@kotlin.internal.InlineOnly
public actual inline fun Char.toLowerCase(): Char = js("String.fromCharCode")(toInt()).toLowerCase().charCodeAt(0).unsafeCast<Int>().toChar()

@kotlin.internal.InlineOnly
public actual inline fun Char.toUpperCase(): Char = js("String.fromCharCode")(toInt()).toUpperCase().charCodeAt(0).unsafeCast<Int>().toChar()

/**
 * Returns `true` if this character is a Unicode high-surrogate code unit (also known as leading-surrogate code unit).
 */
public actual fun Char.isHighSurrogate(): Boolean = this in Char.MIN_HIGH_SURROGATE..Char.MAX_HIGH_SURROGATE

/**
 * Returns `true` if this character is a Unicode low-surrogate code unit (also known as trailing-surrogate code unit).
 */
public actual fun Char.isLowSurrogate(): Boolean = this in Char.MIN_LOW_SURROGATE..Char.MAX_LOW_SURROGATE

/**
 * Returns the Unicode general category of this character as an Int.
 */
@kotlin.internal.InlineOnly
internal actual inline fun Char.getCategoryValue(): Int = getCategoryValue(this.toInt())

/**
 * Returns `true` if this character (Unicode code point) is defined in Unicode.
 *
 * A character is considered to be defined in Unicode if its [category] is not [CharCategory.UNASSIGNED].
 */
public actual fun Char.isDefined(): Boolean {
    if (this < '\u0080') {
        return true
    }
    return getCategoryValue() != CharCategory.UNASSIGNED.value
}

/**
 * Returns `true` if this character is a letter.
 *
 * A character is considered to be a letter if its [category] is [CharCategory.UPPERCASE_LETTER],
 * [CharCategory.LOWERCASE_LETTER], [CharCategory.TITLECASE_LETTER], [CharCategory.MODIFIER_LETTER], or [CharCategory.OTHER_LETTER].
 *
 * @sample samples.text.Chars.isLetter
 */
public actual fun Char.isLetter(): Boolean {
    if (this in 'a'..'z' || this in 'A'..'Z') {
        return true
    }
    if (this < '\u0080') {
        return false
    }
    return getCategoryValue() in CharCategory.UPPERCASE_LETTER.value..CharCategory.OTHER_LETTER.value
}

/**
 * Returns `true` if this character is a letter or digit.
 *
 * @see isLetter
 * @see isDigit
 *
 * @sample samples.text.Chars.isLetterOrDigit
 */
public actual fun Char.isLetterOrDigit(): Boolean {
    if (this in 'a'..'z' || this in 'A'..'Z' || this in '0'..'9') {
        return true
    }
    if (this < '\u0080') {
        return false
    }

    val categoryValue = getCategoryValue()
    return categoryValue == CharCategory.DECIMAL_DIGIT_NUMBER.value
            || categoryValue in CharCategory.UPPERCASE_LETTER.value..CharCategory.OTHER_LETTER.value
}

/**
 * Returns `true` if this character is a digit.
 *
 * A character is considered to be a digit if its [category] is [CharCategory.DECIMAL_DIGIT_NUMBER].
 *
 * @sample samples.text.Chars.isDigit
 */
public actual fun Char.isDigit(): Boolean {
    if (this in '0'..'9') {
        return true
    }
    if (this < '\u0080') {
        return false
    }
    return getCategoryValue() == CharCategory.DECIMAL_DIGIT_NUMBER.value
}

/**
 * Returns `true` if this character is an upper case letter.
 *
 * A character is considered to be an upper case letter if its [category] is [CharCategory.UPPERCASE_LETTER].
 *
 * @sample samples.text.Chars.isUpperCase
 */
public actual fun Char.isUpperCase(): Boolean {
    if (this in 'A'..'Z') {
        return true
    }
    if (this < '\u0080') {
        return false
    }
    return getCategoryValue() == CharCategory.UPPERCASE_LETTER.value
}

/**
 * Returns `true` if this character is a lower case letter.
 *
 * A character is considered to be a lower case letter if its [category] is [CharCategory.LOWERCASE_LETTER].
 *
 * @sample samples.text.Chars.isLowerCase
 */
public actual fun Char.isLowerCase(): Boolean {
    if (this in 'a'..'z') {
        return true
    }
    if (this < '\u0080') {
        return false
    }
    return getCategoryValue() == CharCategory.LOWERCASE_LETTER.value
}

/**
 * Returns `true` if this character is a title case letter.
 *
 * A character is considered to be a title case letter if its [category] is [CharCategory.TITLECASE_LETTER].
 *
 * @sample samples.text.Chars.isTitleCase
 */
public actual fun Char.isTitleCase(): Boolean {
    if (this < '\u0080') {
        return false
    }
    return getCategoryValue() == CharCategory.TITLECASE_LETTER.value
}

/**
 * Returns `true` if this character is an ISO control character.
 *
 * A character is considered to be an ISO control character if its [category] is [CharCategory.CONTROL].
 *
 * @sample samples.text.Chars.isISOControl
 */
public actual fun Char.isISOControl(): Boolean {
    return this <= '\u001F' || this in '\u007F'..'\u009F'
}
