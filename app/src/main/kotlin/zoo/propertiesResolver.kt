package zoo

import java.io.FileInputStream
import java.util.Properties

class PropertiesResolver(){
      private val mappings = Properties()
      fun load (path:String){
        FileInputStream(path).use(mappings::load)
      }
      fun resolve(key:String):String
{
        return  mappings.getProperty(key)
            ?: throw IllegalArgumentException(
                "No mapping for '$key' in properties file"
            )
}}