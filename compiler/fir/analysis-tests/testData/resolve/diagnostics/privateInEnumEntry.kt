enum class SomeEnum {
    SINGLE {
        fun bar() {
            <!HIDDEN!>foo<!>() // Should be Ok
        }

        private fun foo() {}
    };
}