package coroutine

import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.RuntimeException

fun main () {
    asyncExceptionSample()
    try {
        asyncExceptionSample_failed()
    } catch (e: Exception) {
        println("handled")
    }
}

fun asyncExceptionSample() = runBlocking {
    supervisorScope {
        val deferred = async { throw RuntimeException() }
        try {
            deferred.await()
        } catch (e: Exception) {
            println("handle error")
        }
    }
}

fun asyncExceptionSample_failed() = runBlocking {
    coroutineScope {
        val deferred = async { throw RuntimeException() }
        try {
            deferred.await()
        } catch (e: Exception) {
            // Exception thrown in async WILL NOT be caught here
            // but propagated up to the scope
            println("handle error")
        }
    }
}