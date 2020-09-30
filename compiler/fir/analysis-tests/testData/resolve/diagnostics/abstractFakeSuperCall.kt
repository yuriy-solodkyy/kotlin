interface PsiVariable {
    fun getInitializer(): Any?
}

interface UVariable : PsiVariable {
    override fun getInitializer(): Any? {
        return null
    }
}

abstract class AbstractKotlinUVariable : PsiVariable, UVariable {

}

class KotlinUVariable : AbstractKotlinUVariable(), UVariable, PsiVariable {
    override fun getInitializer(): Any? {
        return super<AbstractKotlinUVariable>.<!ABSTRACT_SUPER_CALL!>getInitializer<!>() // Should be Ok
    }
}
