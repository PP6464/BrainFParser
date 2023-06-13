import parser.Parser
import parser.formatAndValidateCodeForParsing
import java.io.File

fun main(args: Array<String>) {
    val fileName = args[0]

    File("./src/main/resources/${fileName}.bf").useLines { fileText ->
        val code = formatAndValidateCodeForParsing(fileText.joinToString())
        Parser().parseBF(code)
    }
}