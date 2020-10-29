// IGNORE_BACKEND: NATIVE
var global = "A"

class C {
  init {
      global += "D"
  }

  companion object {
      init {
        global += "B"
      }

      fun ping() {}

      init {
          global += "C"
      }
  }
}

fun box(): String {
  if (global != "A") {
    return "fail1: global = $global"
  }

  val c = C()
  if (global == "ABCD") return "OK" else return "fail2: global = $global"
}

