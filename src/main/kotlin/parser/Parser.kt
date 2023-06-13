package parser

class Parser {
    val memory = (1..(System.getenv("BF_MEMORY_SIZE")?.toInt() ?: 30000)).map { 0 }.toMutableList()
    var currentPointerIndex = 0

    /*
        * params:
        * code: The code to destructure
        * returns:
        * List<String>: The code destructured at the first level (i.e. nested loops not destructured)
    */
    private fun destructure(code: String): List<String> {
        val result = mutableListOf<String>()
        var codeCopy = code
        val notLoopCodeRegex = Regex("([^\\[\\]]+)")
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
            } else {
                result.add(firstNotLoopingCodeMatch)
                codeCopy = codeCopy.removePrefix(firstNotLoopingCodeMatch)
            }
        }
        return result
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
        println(destructured)
    }
}
