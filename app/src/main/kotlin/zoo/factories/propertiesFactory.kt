package zoo

abstract class PropertiesFactory<T>(
    private val expectedType: Class<T>
) : Factory<T> {

    private val resolver = PropertiesResolver()

    fun loadProperties(propertiesPath: String) {
        resolver.load(propertiesPath)
    }

    override fun create(key: String): T {
        val className = resolver.resolve(key)
        val clazz = loadClass(className)

        verifyType(clazz, expectedType)

        return createObject(clazz)
    }

    private fun loadClass(className: String): Class<*> {
        try {
            return Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException(
                "There is a mapping, but the class does not exist in the project",
                e
            )
        }
    }

    private fun verifyType(
        currentClass: Class<*>,
        expectedType: Class<*>
    ) {
        if (!expectedType.isAssignableFrom(currentClass)) {
            throw IllegalArgumentException(
                "Expected type: ${expectedType.name}, but got: ${currentClass.name}"
            )
        }
    }


    private fun createObject(currentClass: Class<*>): T {
        try {
            return currentClass
                .getDeclaredConstructor()
                .newInstance() as T
        } catch (e: Exception) {
            throw RuntimeException(
                "Failed to create instance of ${currentClass.name}",
                e
            )
        }
    }
}
