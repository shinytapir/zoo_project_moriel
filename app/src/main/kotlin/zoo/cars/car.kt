package zoo

abstract class Car {
    fun printYourName(){
    print(this::class.simpleName)
    }
    abstract fun printYourSound()

}