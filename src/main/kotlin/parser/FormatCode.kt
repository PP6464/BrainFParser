package parser

val validChars = listOf('+', '-', '>', '<', '.', ',', '[', ']')

/*
    * params:
    * code: the code to format
    * returns:
    * the code without any comments or whitespaces and on one line
*/
fun formatAndValidateCodeForParsing(code: String): String {
    return code.filter { validChars.contains(it) }
}