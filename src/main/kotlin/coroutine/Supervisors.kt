package coroutine

import kotlinx.coroutines.*
import java.lang.RuntimeException

fun main() {
    runBlocking {
        supervisorJobSample()

        supervisorScopeSample()
    }
}

fun supervisorJobSample() = runBlocking {
    val scope = CoroutineScope(SupervisorJob())

    scope.launch {
        delay(200L)
        println("job1")
    }

    scope.launch {
        delay(100L)
        println("job2")
        throw RuntimeException()
    }
    delay(1000L)
}

fun supervisorScopeSample() = runBlocking {
    val scope = CoroutineScope(Job())

    scope.launch {
        supervisorScope {
            launch {
                delay(200L)
                println("job1")
            }
            launch {
                delay(100L)
                throw RuntimeException()
            }
        }
    }
    delay(1000L)
}