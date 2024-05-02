package encryptdecrypt
import java.io.File

fun main(args: Array<String>) {
    val operation = if(args[args.indexOf("-mode") + 1] == "dec") -1 else 1
    val key = if(args.indexOf("-key") == -1) 0 else args[args.indexOf("-key") + 1].toInt()%26
    val data:String = if(args.indexOf("-data") != -1) {
        args[args.indexOf("-data") + 1 ]
    } else if (args.indexOf("-in") != -1) {
        try{
            File(args[args.indexOf("-in") + 1 ]).readText(Charsets.UTF_8)
        } catch(e: Exception) {
            println("Error")
            return
        }
    } else {
        ""
    }

    val message = if(args[args.indexOf("-alg") + 1] == "unicode") {
        data.map { it + operation * key }.joinToString("")
    } else {
        data.map {
            if(operation == 1) {
                when {
                    it.isLowerCase() && it + key > 'z' -> 'a' + key - ('z' - it + 1)
                    it.isLowerCase() && it + key < 'z' -> it + key
                    it.isUpperCase() && it + key > 'Z' -> 'A' + key - ('Z' - it + 1)
                    it.isUpperCase() && it + key < 'Z' -> it + key
                    else -> it
                }
            }
            else {
                when {
                    it.isLowerCase() && it - key < 'a' -> 'z' - key + (it - 'a' + 1)
                    it.isLowerCase() -> it - key
                    it.isUpperCase() && it - key < 'A' -> 'Z' - key + (it - 'A' + 1)
                    it.isUpperCase() -> it - key
                    else -> it
                }
            }
        }.joinToString ("" )
    }
    if(args.indexOf("-out") != -1){
        File(args[args.indexOf("-out") + 1 ]).writeText(message)
    } else {
        println(message)
    }
}
