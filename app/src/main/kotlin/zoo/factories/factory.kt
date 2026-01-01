package factories

import kotlin.reflect.KClass
import kotlin.reflect.cast
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger(Factory::class.java)

abstract class Factory<T : Any>(
    protected val expectedType: KClass<T>
) {

    /**
     * Maps a key to the fully qualified class name of the object to be created.
     *
     * @param key A string key representing the object type.
     * @return The fully qualified class name corresponding to the key.
     */
    protected abstract fun getClassNameForKey(key: String): String

    /**
     * template method for creating an instance of the class corresponding to the given key.
     *  mapping the key to the right class is an abstract step 
     *
     * @param key A string key representing the object type.
     * @return An instance of type T.
     * @throws RuntimeException if the class cannot be instantiated.
     * @throws IllegalArgumentException if the created object is not of the expected type.
     */
    fun create(key: String): T {
        logger.debug("factory creating instance for key: $key")
        val className = getClassNameForKey(key)
        val instance = instantiateClass(className)
        return validateType(key, instance)
    }

    /**
     * Instantiates a class given its fully qualified name using reflection.
     *
     * @param className The fully qualified class name.
     * @return The created object as Any.
     * @throws RuntimeException if instantiation fails.
     */
    private fun instantiateClass(className: String): Any {
        try {
            return Class.forName(className)
                .getDeclaredConstructor()
                .newInstance()
        } 
        catch (e: Exception) {
            throw RuntimeException("factory failed - Failed to create instance of $className", e)
        }
    }

    /**
     * Validates that the created object is of the expected type T.
     *
     * @param key The key used to create the object.
     * @param instance The created object.
     * @return The object cast to type T if validation passes.
     * @throws IllegalArgumentException if the object is not of type T.
     */
    private fun validateType(key: String, instance: Any): T {
        if (expectedType.isInstance(instance)) {
            logger.info("factory successfully created instance of $expectedType for key: $key")
            return expectedType.cast(instance)
        } 
        else {
            throw IllegalArgumentException(
                "factory failed - $key is not of expected type. Expected: $expectedType, got: ${instance::class}"
            )
        }
    }
}
