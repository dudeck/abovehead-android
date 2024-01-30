package pl.abovehead.samples

import android.util.Log
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
https://kotlinlang.org/docs/coroutines-guide.html
 */
class CoroutinesSamples {
    val tag = "CoroutinesSamples"

    fun runAll() {
        runBlockingSample()
        runBlockingWithSuspendFun()
        runBlockingWithCoroutineScope()
        runBlockingWith2CoroutinesInCoroutinesScope()
        runBlockingWithJob()
        runBlockingWithDeferredAsyncAwait()
    }

    fun runBlockingSample() = runBlocking {
        val index = 0;
        launch {
            delay(1000L)
            Log.d(tag + index, "World!")
        }
        Log.d(tag + index, "Hello")
        //2024-01-30 16:19:14.316 17037-17037 CoroutinesSamples0      pl.abovehead                         D  Hello
        //2024-01-30 16:19:15.318 17037-17037 CoroutinesSamples0      pl.abovehead                         D  World!
    }

    fun runBlockingWithSuspendFun() = runBlocking {
        val index = 1
        launch { doWorld(index) }
        Log.d(tag + index, "Hello")
        //2024-01-30 16:19:15.319 17037-17037 CoroutinesSamples1      pl.abovehead                         D  Hello
        //2024-01-30 16:19:16.321 17037-17037 CoroutinesSamples1      pl.abovehead                         D  World!
    }

    private suspend fun doWorld(index: Int) {
        delay(1000L)
        Log.d(tag + index, "World!")
    }

    fun runBlockingWithCoroutineScope() = runBlocking {
        doCoroutineScope()
    }

    // runBlocking and coroutineScope builders may look similar because they both wait for their
    // body and all its children to complete. The main difference is that the runBlocking method
    // blocks the current thread for waiting, while coroutineScope just suspends, releasing the
    // underlying thread for other usages. Because of that difference, runBlocking is a regular
    // function and coroutineScope is a suspending function.
    private suspend fun doCoroutineScope() = coroutineScope {
        val index = 2;
        launch {
            launch {
                delay(1000L)
                Log.d(tag + index, "World!")
            }
            Log.d(tag + index, "Hello")
            //2024-01-30 16:26:19.792 18570-18570 CoroutinesSamples2      pl.abovehead                         D  Hello
            //2024-01-30 16:26:20.794 18570-18570 CoroutinesSamples2      pl.abovehead                         D  World!
        }
    }

    fun runBlockingWith2CoroutinesInCoroutinesScope() = runBlocking {
        val index = 3
        do2CoroutinesScope(index)
        Log.d(tag + index, "Done")
    }

    private suspend fun do2CoroutinesScope(index: Int) = coroutineScope {
        launch {
            delay(2000L)
            Log.d(tag + index, "World 2")
        }
        launch {
            delay(1000L)
            Log.d(tag + index, "World 1")
        }
        Log.d(tag + index, "Hello")
        //2024-01-30 16:34:03.484 20480-20480 CoroutinesSamples3      pl.abovehead                         D  Hello
        //2024-01-30 16:34:04.486 20480-20480 CoroutinesSamples3      pl.abovehead                         D  World 1
        //2024-01-30 16:34:05.486 20480-20480 CoroutinesSamples3      pl.abovehead                         D  World 2
        //2024-01-30 16:34:05.487 20480-20480 CoroutinesSamples3      pl.abovehead                         D  Done
    }

    fun runBlockingWithJob() = runBlocking {
        val index = 4
        val job = launch {
            delay(1000L)
            Log.d(tag + index, "World!")
        }
        Log.d(tag + index, "Hello")
        job.join()
        Log.d(tag + index, "Done")
        //2024-01-30 16:40:26.238 22451-22451 CoroutinesSamples4      pl.abovehead                         D  Hello
        //2024-01-30 16:40:27.241 22451-22451 CoroutinesSamples4      pl.abovehead                         D  World!
        //2024-01-30 16:40:27.241 22451-22451 CoroutinesSamples4      pl.abovehead                         D  Done
    }

    fun runBlockingWithDeferredAsyncAwait() = runBlocking {
        val index = 5
        val deferred: Deferred<Int> = async {
            loadData(index)
        }
        Log.d(tag + index, "Waiting...")
        Log.d(tag + index, "Deferred = ${deferred.await()}")
        //2024-01-30 16:56:59.671 22620-22620 CoroutinesSamples5      pl.abovehead                         D  Waiting...
        //2024-01-30 16:56:59.672 22620-22620 CoroutinesSamples5      pl.abovehead                         D  Loading...
        //2024-01-30 16:57:00.675 22620-22620 CoroutinesSamples5      pl.abovehead                         D  Loaded
        //2024-01-30 16:57:00.675 22620-22620 CoroutinesSamples5      pl.abovehead                         D  Deferred = 42
    }

    private suspend fun loadData(index: Int): Int {
        Log.d(tag + index, "Loading...")
        delay(1000L)
        Log.d(tag + index, "Loaded")
        return 42
    }

}