class Some {
    private val x = object : Any() {
        fun foo() = "OK"
    }

    fun bar() = x.foo()
}

fun box() = Some().bar()