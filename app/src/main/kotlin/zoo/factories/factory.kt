package zoo 

interface Factory<T> {
    fun create(type: String): T
}