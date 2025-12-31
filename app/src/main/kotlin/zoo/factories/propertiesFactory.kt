package factories

import java.util.Properties
import kotlin.reflect.KClass

class PropertiesFactory<T : Any>(
    private val properties: Properties,
    expectedType: KClass<T>
) : Factory<T>(expectedType) {

override fun getClassForKey(key: String): String =
        properties.getProperty(key)
            ?: throw IllegalArgumentException("No class found for key: $key")

}


