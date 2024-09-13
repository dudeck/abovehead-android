package pl.abovehead.common

interface Repository<T> {
    suspend fun fetch(url: String): T
}