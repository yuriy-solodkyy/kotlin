fun println(arg: Int) {}

fun foo() {
    val x: Int
    try {
        x = 0
    } finally {}

    println(<!UNINITIALIZED_VARIABLE!>x<!>) // Should be Ok
}