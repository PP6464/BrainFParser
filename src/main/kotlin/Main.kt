import java.io.File
import java.util.Scanner

fun main(args: Array<String>) {
    val fileName = args[0]
    val memory = (0..30000).map { 0 }.toMutableList()
    val scanner = Scanner(System.`in`)
    var currentPointerIndex = 0
    var isRunningLoop = false
    var isParsingLoop = false
    var increaseIndex = true
    var index = 0
    var loop: String? = null
    val validChars = listOf('+', '-', '>', '<', '.', ',', '[', ']')

    File("./src/main/resources/${fileName}.bf").useLines { fileText ->
        val code = fileText.filter { validChars.contains(it.toCharArray()[0]) }.joinToString("")
        while (index != code.length) {
            code[index].let {
                if (isRunningLoop) {
                    increaseIndex = false
                    loop!!.map { char ->
                        when (char) {
                            '+' -> memory[currentPointerIndex]++
                            '-' -> memory[currentPointerIndex]--
                            '.' -> println(memory[currentPointerIndex].toChar())
                            '>' -> currentPointerIndex++
                            '<' -> currentPointerIndex--
                            ',' -> memory[currentPointerIndex] = scanner.nextLine().single().code
                            else -> {}
                        }
                    }
                    if (memory[currentPointerIndex] == 0) {
                        isRunningLoop = false
                        increaseIndex = true
                        loop = null
                    } else return@let
                }
                when {
                    isParsingLoop && it != ']' -> loop += it
                    it == '+' -> memory[currentPointerIndex]++
                    it == '-' -> memory[currentPointerIndex]--
                    it == '.' -> println(memory[currentPointerIndex].toChar())
                    it == '>' -> currentPointerIndex++
                    it == '<' -> currentPointerIndex--
                    it == ',' -> memory[currentPointerIndex] = scanner.nextLine().single().code
                    it == '[' -> {
                        isParsingLoop = true
                        loop = ""
                    }
                    it == ']' -> {
                        isParsingLoop = false
                        isRunningLoop = true
                    }
                    else -> {}
                }
            }
            if (increaseIndex) index++
        }
    }

    scanner.close()
}