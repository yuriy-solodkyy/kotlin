abstract class IrConst<T> {
    abstract val kind: IrConstKind<T>
    abstract val value: T

    fun <D> visit(transformer: IrElementTransformer<D>, data: D) = transformer.visitConst(this, data)
}

sealed class IrConstKind<T>(val asString: String) {
    object Null : IrConstKind<Nothing?>("Null")
    object Boolean : IrConstKind<Boolean>("Boolean")
}

interface IrElementVisitor<out R, in D> {
    fun visitElement(element: Any, data: D): R

    fun <T> visitConst(expression: IrConst<T>, data: D) = visitElement(expression, data)
}

interface IrElementTransformer<in D> : IrElementVisitor<Any, D> {
    override fun visitElement(element: Any, data: D): Any {
        return element
    }

    override fun <T> visitConst(expression: IrConst<T>, data: D) = visitElement(expression, data)
}

interface Pass {
    fun box(): String
}

class Some : Pass {
    override fun box(): String {
        val constant = object : IrConst<Nothing?>() {
            override val kind = IrConstKind.Null
            override val value = null
        }
        val result = constant.visit(object : IrElementTransformer<Any> {}, "data")
        if (result is IrConst<*> && result.value == null) {
            return "OK"
        }
        return "FAIL"
    }
}

fun box() = Some().box()