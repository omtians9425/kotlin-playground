package coroutine

import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.RuntimeException

fun main () {
    // async
    runBlocking {
        asyncExceptionSampleWithSupervisorScopeOk()
//        asyncExceptionSampleWithCoroutineScopeFailed() // crash
        withExceptionHandler { asyncExceptionSampleWithCoroutineScopeFailed() } // OK
    }

    // launch
    runBlocking {
        withExceptionHandler { launchExceptionSampleWithCoroutineScopeOk() }
        withExceptionHandler { launchExceptionSampleWithSupervisorScopeOk() }
        withExceptionHandler { launchExceptionSampleWithSupervisorScopeFailed() } // crash
    }
}

/**
 * async
 */

suspend fun asyncExceptionSampleWithSupervisorScopeOk() {
    supervisorScope {
        val deferred = async { throw RuntimeException() }
        try {
            deferred.await()
        } catch (e: Exception) {
            println("handle error")
        }
    }
}

suspend fun asyncExceptionSampleWithCoroutineScopeFailed() {
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

/**
 * launch
 */

suspend fun launchExceptionSampleWithSupervisorScopeFailed() {
    supervisorScope {
        launch { throw Exception() }
    }
}

// when use supervisorScope, you must handle in launch immediately.
suspend fun launchExceptionSampleWithSupervisorScopeOk() {
    supervisorScope {
        launch {
            try {
                throw Exception()
            } catch (e: Exception) {
                println("inner handled")
            }
        }
    }
}

suspend fun launchExceptionSampleWithCoroutineScopeOk() {
    coroutineScope {
        launch { throw Exception() }
    }
}

suspend fun withExceptionHandler(block: suspend() -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        println("handled.")
    }
}