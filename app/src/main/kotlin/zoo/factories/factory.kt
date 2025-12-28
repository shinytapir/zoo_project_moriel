package factories

interface Factory<T> {
    fun create(key: String): T
}