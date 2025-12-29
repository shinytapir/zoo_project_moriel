package app

import factories.PropertiesFactory

/**
 * Creates objects using the provided factory.
 * Each key is mapped to an instance if creation succeeds,
 * or `null` if the factory fails to create the object.
 *
 */
fun <T : Any> createObjects(factory: PropertiesFactory<T>, keys: List<String>): Map<String, T?> =
    keys.associateWith { key ->
        try {
            factory.create(key.lowercase())
        } catch (e: Exception) {
            null
        }
    }