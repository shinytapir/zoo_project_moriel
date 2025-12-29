package factories
import java.util.Properties
import java.io.FileInputStream
import kotlin.reflect.KClass


class PropertiesFactory<T:Any>(
    private val properties: Properties,
    private val expectedType: KClass<T>
) : Factory<T> {

  
override fun create(key: String): T {
    val className = resolveClass(key)
    val result = createObject(className)
    if (expectedType.isInstance(result)) {
        return result as T
    } else {
        throw IllegalArgumentException("$key is not of expected type.")
    }
}

private fun resolveClass(key: String): String =
    properties.getProperty(key)
    ?: throw IllegalArgumentException("No class found for key: $key") 

private fun createObject(className: String): Any = try {
    Class.forName(className)
        .getDeclaredConstructor()
        .newInstance()
} catch (e: Exception) {
    throw RuntimeException("Failed to create instance of $className")
}

}

