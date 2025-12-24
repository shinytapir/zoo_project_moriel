package zoo

import java.io.FileInputStream
import java.util.Properties
object AppConfig(){
      private val configPath:String = System.getenv("APP_CONFIG_PATH")?:"app/src/main/resources/animals.properties"
      val properties:Properties=Properties.apply{  FileInputStream(configPath).use(::load)}
    
}
