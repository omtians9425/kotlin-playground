package serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable
data class Data(val a: Int, val b: Int, val c: String = "")

fun main() {
    val json = Json(JsonConfiguration.Stable.copy(ignoreUnknownKeys = true))

    // to json
    val jsonData = json.stringify(Data.serializer(), Data(1, 2, "3"))
    val jsonList = json.stringify(Data.serializer().list, listOf(Data(1,2,"3"), Data(4,5,"6")))
    println(jsonData)
    println(jsonList)

    // from json
    val obj = json.parse(Data.serializer(), """{"a":3, "b":2, "c": "1"}""")
    println(obj)

    // do not forget to specify 'ignoreUnknownKeys=true'
    val obj1 = json.parse(Data.serializer(), """{"a":3, "b":141, "c":"hoge", "d":414, "e":0}""")
    println(obj1)

    val obj2 = json.parse(Data.serializer(), """{"a":3, "b":141}""")
    println(obj2)

    val list = json.parse(Data.serializer().list, """[{"a":1,"b":2,"c":"3"},{"a":4,"b":5,"c":"6"}]""")
    println("$list, ${list.javaClass.simpleName}")
}

