// !LANGUAGE: +InlineClasses
// IGNORE_BACKEND: JVM_IR
// IGNORE_BACKEND_FIR: JVM_IR
// IGNORE_BACKEND_MULTI_MODULE: JVM_IR, JVM_MULTI_MODULE_IR_AGAINST_OLD

// FILE: inline.kt

inline class IC(val value: String) {
    inline fun <T> dispatchInline(): T = value as T
}

inline fun <T> IC.extensionInline(): T = value as T

inline fun <T> normalInline(a: IC): T = a.value as T

// FILE: box.kt
// NO_CHECK_LAMBDA_INLINING

fun <T> extension(a: IC): T = bar(a, object : IFace<IC, T> {
    override fun call(it: IC): T = it.extensionInline()
})

fun <T> dispatch(a: IC): T = bar(a, object : IFace<IC, T> {
    override fun call(it: IC): T = it.dispatchInline()
})

fun <T> normal(a: IC): T = bar(a, object : IFace<IC, T> {
    override fun call(it: IC): T = normalInline(it)
})

interface IFace<T, R> {
    fun call(ic: T): R
}

fun <T, R> bar(value: T, f: IFace<T, R>): R {
    return f.call(value)
}

fun box(): String {
    var res = extension<String>(IC("O")) + "K"
    if (res != "OK") return "FAIL 2: $res"

    res = dispatch<String>(IC("O")) + "K"
    if (res != "OK") return "FAIL 3: $res"

    res = normal<String>(IC("O")) + "K"
    if (res != "OK") return "FAIL 3: $res"

    return "OK"
}