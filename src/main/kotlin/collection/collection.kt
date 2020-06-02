package collection

fun main() {
    associateBy()
    associateWith()
    associateTo()
}

data class Data(val fn: String, val ln: String)

// "By" determines the key selector.
// The value is the same as element.
fun associateBy() {
    val list = listOf(
        Data("sato", "shungo"),
        Data("kato", "keita")
    )

    // {sato=Data(fn=sato, ln=shungo), kato=Data(fn=kato, ln=keita)}
    val r1 = list.associateBy { it.fn }
    println(r1.javaClass.name)
    println(r1)

    // We can also determine the value result.
    // {sato=shungo, kato=keita}
    val r2 = list.associateBy(Data::fn, Data::ln)
    println(r2.javaClass.name)
    println(r2)
}

// "With" determines the value result.
// The key is the same as element.
fun associateWith() {
    val list = listOf(
        Data("sato", "shungo"),
        Data("kato", "keita")
    )

    val r1 = list.associateWith { it.ln } // same as associateBy({it}, Data::ln)
    println(r1.javaClass.name)
    println(r1)
}

// "To" appends entries to an existing destination
fun associateTo() {
    val original = mutableMapOf("saito" to "kento")
    val list = listOf(
        Data("sato", "shungo"),
        Data("kato", "keita")
    )

    val r1 = list.associateTo(original) { Pair(it.fn, it.ln) }
    println(r1.javaClass.name)
    println(r1)
}