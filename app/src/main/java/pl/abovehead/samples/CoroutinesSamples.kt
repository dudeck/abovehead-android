package pl.abovehead.samples

import android.util.Log
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

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
        cancelJoin()
        runTimeout()
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

    fun cancelJoin() = runBlocking {
        val index = 6
        val job = launch (Dispatchers.Default){
            repeat(1000) {
                Log.d(tag + index, "job: I'm sleeping $it")
                delay(500L)
            }
        }
        delay(1300L)
        Log.d(tag + index, "main: I'm tired of waiting!")
        job.cancel()
        Log.d(tag + index, "main: Cancelled")
        job.join()
        //job.cancelAndJoin()
        Log.d(tag + index, "main: join called so I can quit.")
        //2024-01-30 17:15:58.753 25419-25654 CoroutinesSamples6      pl.abovehead                         D  job: I'm sleeping 0
        //2024-01-30 17:15:59.256 25419-25655 CoroutinesSamples6      pl.abovehead                         D  job: I'm sleeping 1
        //2024-01-30 17:15:59.759 25419-25655 CoroutinesSamples6      pl.abovehead                         D  job: I'm sleeping 2
        //2024-01-30 17:16:00.053 25419-25419 CoroutinesSamples6      pl.abovehead                         D  main: I'm tired of waiting!
        //2024-01-30 17:16:00.055 25419-25419 CoroutinesSamples6      pl.abovehead                         D  main: Cancelled
        //2024-01-30 17:16:00.056 25419-25419 CoroutinesSamples6      pl.abovehead                         D  main: join called so I can quit.
    }

    fun runTimeout() = runBlocking {
        val index = 7
        withTimeout(1300L) {
            repeat(1000) { i ->
                Log.d(tag + index, "I'm sleeping $i ...")
                delay(500L)
            }
        }
    }

}

/*

withContext and runBlocking are both constructs used in Kotlin for working with coroutines,
but they serve different purposes and are used in different contexts.

withContext: It is used to change the coroutine's context for a specific block of code. It
allows you to switch to a different coroutine context, such as Dispatchers.IO (for I/O-bound tasks),
 Dispatchers.Main (for UI-related tasks in Android), etc. It's commonly used when you need to
 perform a suspending function in a specific context.

Example:

kotlin
Copy code
suspend fun fetchData() = withContext(Dispatchers.IO) {
    // Perform IO-bound task here
}
runBlocking: It is used to start a new coroutine and block the current thread until its completion.
It is mainly used in testing and in the main function of a Kotlin application, where you want to
block the main thread to wait for the coroutines to finish.

Example:

kotlin
Copy code
fun main() = runBlocking {
    // Launch coroutines here
}
Note that using runBlocking in Android or in any UI application is generally discouraged because
it can lead to blocking the main thread, causing the UI to become unresponsive.

In summary, withContext is used to switch coroutine contexts within a coroutine, while runBlocking
is used to start a new coroutine and block the current thread until its completion. Use them based
on the specific requirements of your coroutine code.
 */