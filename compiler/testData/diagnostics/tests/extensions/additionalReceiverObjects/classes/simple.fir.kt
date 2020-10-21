object Some {
    val a = 1
}

class A with(Some) {
    val b = <!UNRESOLVED_REFERENCE!>a<!>
}

with<A> fun f() {}