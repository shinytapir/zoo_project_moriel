package factories

interface Factory<T> {
    fun create(type: String): T
}