package zoo

import java.io.FileInputStream
import java.util.Properties


abstract class PropertiesFactory<T>:Factory<T>{

    private val properties = Properties()

    fun loadProperties(propertiesPath: String) {
        FileInputStream(propertiesPath).use { properties.load(it) }
    }

    // template method
    override fun create(key: String): T {
        return objectFromProperty(key)

    }
    
    // shared step: create the object from properties
    private fun objectFromProperty(key: String): T{
        val className = properties.getProperty(key)
            ?: throw IllegalArgumentException(
                "No mapping for '$key' in properties file"
            )

        try{
            //here could be a problem of the thing is in propertie but its not a thing you can create
           val theobj= Class.forName(className)
            .getDeclaredConstructor()
            .newInstance() as T
        return theobj
        }
            catch(e:Exception){
                throw (RuntimeException("Failed to create instance for key '$key'", e))

            }
      
    }


}
