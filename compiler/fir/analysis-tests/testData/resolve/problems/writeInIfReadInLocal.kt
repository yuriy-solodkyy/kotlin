fun foo(arg: Boolean) {
    val callToUse: Int
    if (arg) {
        callToUse = 1
    } else {
        callToUse = 2
    }

    fun local(): Int {
        return <!UNINITIALIZED_VARIABLE!>callToUse<!>
    }
}