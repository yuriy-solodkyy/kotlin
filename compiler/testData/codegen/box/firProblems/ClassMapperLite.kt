// WITH_RUNTIME

object ClassMapperLite {
    // Kotlin ClassId -> JVM desc
    // e.g. "kotlin.IntArray" -> "[I"
    //      "kotlin.String.Companion" -> "Lkotlin/jvm/internal/StringCompanionObject"
    //      "kotlin/collections/Map.Entry" -> "Ljava/util/Map$Entry"
    private val map: Map<String, String> = mutableMapOf<String, String>().apply {
        val primitives = listOf(
            "Boolean", "Z",
            "Char", "C",
            "Byte", "B",
            "Short", "S",
            "Int", "I",
            "Float", "F",
            "Long", "J",
            "Double", "D"
        )

        for (i in primitives.indices step 2) {
            put("kotlin/${primitives[i]}", primitives[i + 1])
            put("kotlin/${primitives[i]}Array", "[${primitives[i + 1]}")
        }

        put("kotlin/Unit", "V")

        fun add(kotlinSimpleName: String, javaInternalName: String) {
            put("kotlin/$kotlinSimpleName", "L$javaInternalName;")
        }

        add("Any", "java/lang/Object")
        add("Nothing", "java/lang/Void")
        add("Annotation", "java/lang/annotation/Annotation")

        for (klass in listOf("String", "CharSequence", "Throwable", "Cloneable", "Number", "Comparable", "Enum")) {
            add(klass, "java/lang/$klass")
        }

        for (klass in listOf("Iterator", "Collection", "List", "Set", "Map", "ListIterator")) {
            add("collections/$klass", "java/util/$klass")
            add("collections/Mutable$klass", "java/util/$klass")
        }

        add("collections/Iterable", "java/lang/Iterable")
        add("collections/MutableIterable", "java/lang/Iterable")
        add("collections/Map.Entry", "java/util/Map\$Entry")
        add("collections/MutableMap.MutableEntry", "java/util/Map\$Entry")

        for (i in 0..22) {
            add("Function$i", "kotlin/jvm/functions/Function$i")
            add("reflect/KFunction$i", "kotlin/reflect/KFunction")
        }

        //Boolean is purposefully omitted from this list, even though it has a Companion Object.
        //This assures that an older compiler won't get confused by the new signature, preventing a bug in compatibility.
        for (klass in listOf("Char", "Byte", "Short", "Int", "Float", "Long", "Double", "String", "Enum")) {
            add("$klass.Companion", "kotlin/jvm/internal/${klass}CompanionObject")
        }
    }

    /**
     * @param classId the name of the class in the format: "org/foo/bar/Test.Inner"
     */
    @JvmStatic
    fun mapClass(classId: String): String {
        return map[classId] ?: "L${classId.replace('.', '$')};"
    }
}

fun box(): String {
    ClassMapperLite.mapClass("xxx")
    return "OK"
}
