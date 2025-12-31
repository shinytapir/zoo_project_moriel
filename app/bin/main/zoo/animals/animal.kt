package zoo

abstract class Animal {

    abstract fun printYourSound()

    fun printYourName() {
        print(this::class.simpleName)
    }

}

