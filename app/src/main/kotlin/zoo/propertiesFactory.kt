package zoo

import java.io.FileInputStream
import java.util.Properties


abstract class PropertiesFactory<T>:Factory<T>{

    private val properties = Properties()

    fun loadProperties(propertiesPath: String) {
        FileInputStream(propertiesPath).use { properties.load(it) }
    }
    override fun create(key: String): T {
        val className=matchKeytoValue(key)
        return createObjectFromValue(className)

    }
    private fun matchKeytoValue(key: String):String{
        return  properties.getProperty(key)
            ?: throw IllegalArgumentException(
                "No mapping for '$key' in properties file"
            )
    }
    

    private fun createObjectFromValue(className: String): T{
        try{
            //here could be a problem of the thing is in propertie but its not a thing you can create
           val theobj= Class.forName(className)
            .getDeclaredConstructor()
            .newInstance() as T
        return theobj
        }
            catch(e:Exception){
                throw (RuntimeException("Failed to create instance for key '$className'", e))

            }
      
    }


}
