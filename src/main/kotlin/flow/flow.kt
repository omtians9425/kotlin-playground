package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    // two flows
    val nums = (1..3).asFlow().onEach { delay(300L) }
    val strs = flowOf("a", "b", "c", "d").onEach { delay(800L) }

    println("--zip-")
    zipSample(nums, strs)

    println("--combine")
    combineSample(nums, strs)
    combineMore(nums, strs)
}

suspend fun zipSample(one: Flow<Int>, two: Flow<String>) {
    val startTime = System.currentTimeMillis()

    // The value with more elements is ignored (ex: "d")
    // 要素数が同じかつ足並み揃えたいならこっち(片方だけ更新されただけでemitされたくない場合)
    one.zip(two) { a, b -> "$a$b" }.collect {
        println("$it at ${System.currentTimeMillis() - startTime} ms from start")
    }
}

suspend fun combineSample(one: Flow<Int>, two: Flow<String>) {
    val startTime = System.currentTimeMillis()

    // Collect all pairs of the latest values.
    // The value updated faster than another one's value is ignored
    // どれかが更新されただけで処理が走るが、どれも1回ずつなどの場合は問題ない
    one.combine(two) { a, b -> "$a$b" }.collect {
        println("$it at ${System.currentTimeMillis() - startTime} ms from start")
    }
}

suspend fun combineMore(one: Flow<Int>, two: Flow<String>) {
    val three = (1..5).asFlow().onEach { delay(1000L) }

    combine(one, two, three) { one, two, three ->
        "$one - $two - $three"
    }.collect { println(it)}
}
