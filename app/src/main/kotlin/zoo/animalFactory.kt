package zoo

import java.io.FileInputStream
import java.util.Properties

object AnimalFactory{
    private  val props = Properties()

    fun load(propertiesPath:String){
        FileInputStream(propertiesPath).use{props.load(it)}
    }

    fun createAnimal(type:String):Animal{
    val className = props.getProperty(type)
        ?: throw IllegalArgumentException("Unknown animal: '$type'")

        val animal= Class.forName(className)
        .getDeclaredConstructor()
        .newInstance() as Animal
        return animal 
    } 
}

