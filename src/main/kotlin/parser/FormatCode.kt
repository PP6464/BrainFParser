package parser

val validChars = listOf('+', '-', '>', '<', '.', ',', '[', ']')

/*
    * params:
    * code: the code to format
    * returns:
    * the code without any comments or whitespaces and on one line
*/
fun formatAndValidateCodeForParsing(code: String): String {
    var filteredForValidation = code.filter { it == '[' || it == ']' }
    while (filteredForValidation.isNotBlank()) {
        val filteredForValidationCopy = filteredForValidation
        filteredForValidation = filteredForValidation.replace("[]", "")
        if (filteredForValidationCopy == filteredForValidation) throw Error("Ensure that your brackets are balanced and are in the correct order")
    }
    return code.filter { validChars.contains(it) }
}