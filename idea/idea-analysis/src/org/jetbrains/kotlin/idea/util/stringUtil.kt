/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.util.string

fun String.collapseSpaces(): String {
    val builder = StringBuilder()
    var haveSpaces = false
    for (c in this) {
        if (c.isWhitespace()) {
            haveSpaces = true
        } else {
            if (haveSpaces) {
                builder.append(" ")
                haveSpaces = false
            }
            builder.append(c)
        }
    }
    return builder.toString()
}

