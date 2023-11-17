package pl.abovehead

interface SuspendUseCase<T>{
    suspend fun execute(): T
}