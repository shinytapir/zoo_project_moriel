package factories

import kotlin.reflect.KClass
import kotlin.reflect.cast

abstract class Factory<T : Any>(
    protected val expectedType: KClass<T>
) {

    protected abstract fun getClassForKey(key: String): String

    // Template method
    fun create(key: String): T {
        val className = getClassForKey(key)
        val result = createObject(className)
        return validate(key, result)
    }
    //overload for list 
        fun create(keys: List<String>): List<T> {
        return keys.map { key -> create(key) }
    }

    private fun createObject(className: String): Any {
        return try {
            Class.forName(className)
                .getDeclaredConstructor()
                .newInstance()
        } catch (e: Exception) {
            throw RuntimeException("Failed to create instance of $className", e)
        }
    }

    private fun validate(key: String, result: Any): T {
        if (expectedType.isInstance(result)) {
            return expectedType.cast(result)
        } else {
            throw IllegalArgumentException(
                "$key is not of expected type. $expectedType expected but got ${result::class}"
            )
        }
    }
}
