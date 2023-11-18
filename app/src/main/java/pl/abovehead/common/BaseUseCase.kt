package pl.abovehead.common

interface SuspendUseCase<T>{
    suspend fun execute(): T
}