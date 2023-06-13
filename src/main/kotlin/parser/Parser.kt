package parser

import java.util.Scanner

class Parser {
    private val memory = (1..(System.getenv("BF_MEMORY_SIZE")?.toInt() ?: 30000)).map { 0 }.toMutableList()
    private var currentPointerIndex = 0
    private val scanner = Scanner(System.`in`)

    /*
        * params:
        * code: The code to destructure
        * returns:
        * List<String>: The code destructured at the first level (i.e. nested loops not destructured)
    */
    private fun destructure(code: String): List<String> {
        val result = mutableListOf<String>()
        var codeCopy = code
        val notLoopCodeRegex = Regex("([^\\[\\]]*)")
        while (codeCopy.isNotBlank()) {
            val firstNotLoopingCodeMatch = notLoopCodeRegex.find(codeCopy)?.destructured?.component1() ?: ""
            if (firstNotLoopingCodeMatch.isBlank()) {
                var nOpenSquareBracket = 1 // Number of [ encountered
                var nCloseSquareBracket = 0 // Number of ] encountered
                var index = 1
                var loopString = "["
                while (nOpenSquareBracket > nCloseSquareBracket /* When this condition fails, we must have reached the end of the loop */) {
                    loopString += codeCopy[index]
                    when (codeCopy[index]) {
                        '[' -> nOpenSquareBracket++
                        ']' -> nCloseSquareBracket++
                    }
                    index++
                }
                result.add(loopString)
                codeCopy = codeCopy.removePrefix(loopString)
            } else {
                result.add(firstNotLoopingCodeMatch)
                codeCopy = codeCopy.removePrefix(firstNotLoopingCodeMatch)
            }
        }
        return result
    }

    /*
        * params:
        * code: The loop to execute
        * returns:
        * void
    */
    private fun executeLoop(code: String) {
        val destructured = destructure(code.removePrefix("[").removeSuffix("]"))
        while (memory[currentPointerIndex] != 0) {
            destructured.map {
                if (it.startsWith("[")) {
                    // command is a loop
                    executeLoop(it)
                } else {
                    // command is not a loop
                    it.map {char ->
                        when (char) {
                            '<' -> {
                                if (currentPointerIndex != 0) currentPointerIndex-- else throw Error("Can't shift pointer index to index -1")
                            }
                            '>' -> {
                                if (currentPointerIndex != memory.size - 1) currentPointerIndex++ else throw Error("Can't shift pointer index to index ${memory.size}")
                            }
                            ',' -> {
                                val input = scanner.next().single()
                                memory[currentPointerIndex] = input.code
                            }
                            '.' -> println(memory[currentPointerIndex].toChar())
                            '+' -> memory[currentPointerIndex]++
                            '-' -> memory[currentPointerIndex]--
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    /*
        * params:
        * code: The code to execute
        * returns:
        * void
    */
    fun parseBF(code: String) {
        if (code.isBlank()) return
        val destructured = destructure(code)
        destructured.map {
            if (it.startsWith("[")) {
                // command is a loop
                executeLoop(it)
            } else {
                // command is normal command
                it.map {char ->
                    when (char) {
                        '<' -> {
                            if (currentPointerIndex != 0) currentPointerIndex-- else throw Error("Can't shift pointer index to index -1")
                        }
                        '>' -> {
                            if (currentPointerIndex != memory.size - 1) currentPointerIndex++ else throw Error("Can't shift pointer index to index ${memory.size}")
                        }
                        ',' -> {
                            val input = scanner.next().single()
                            memory[currentPointerIndex] = input.code
                        }
                        '.' -> println(memory[currentPointerIndex].toChar())
                        '+' -> memory[currentPointerIndex]++
                        '-' -> memory[currentPointerIndex]--
                        else -> {}
                    }
                }
            }
        }

        scanner.close()
    }
}
