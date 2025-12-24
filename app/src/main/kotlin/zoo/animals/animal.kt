package zoo

abstract class Animal {
   fun printYourName(){
   print(this::class.simpleName)
   }
   abstract fun printYourSound()
}