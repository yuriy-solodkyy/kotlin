// WITH_RUNTIME

class Some(var x: String) {
    init {
        if (x.isEmpty()) {
            x = "OK"
        }
    }
}

fun box(): String {
    return Some("").x
}