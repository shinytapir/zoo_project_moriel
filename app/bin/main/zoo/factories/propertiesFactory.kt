package factories
import java.util.Properties
import java.io.FileInputStream

class PropertiesFactory<T>(
    private val properties: Properties
) : Factory<T> {

override fun create(key: String): T {
    val className = resolveClass(key)
    val result = createObject(className)
    try {
        return result as T
    } catch (e: ClassCastException) {
         throw IllegalArgumentException("${result::class.java.name} is not of the expected type")
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
    throw RuntimeException("Failed to create instance of $className", e)
}


}

