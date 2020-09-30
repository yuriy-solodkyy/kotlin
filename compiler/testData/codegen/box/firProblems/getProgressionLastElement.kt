// WITH_RUNTIME

val some = listOf("-", "O", "-", "K")

fun box(): String {
    var result = ""
    var fake = ""
    for (i in some.indices step 2) {
        fake += some[i]
        result += some[i + 1]
    }
    if (fake != "--") return fake
    return result
}