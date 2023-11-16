package pl.abovehead

interface SuspendUseCase{
    suspend fun execute(): Any
}