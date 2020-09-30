fun println(arg: Int) {}

fun foo(arg: Boolean) {
    val x: Int
    if (arg) {
        x = 1
    } else {
        x = 2
    }

    class Local {
        fun bar() {
            println(<!UNINITIALIZED_VARIABLE!>x<!>) // Should be Ok
        }
    }
}