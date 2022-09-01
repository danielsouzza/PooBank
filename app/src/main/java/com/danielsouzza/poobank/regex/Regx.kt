package com.danielsouzza.poobank.regex

class Regx {
    val emailRegx =
        "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
    val dateRegx =
        "(?:0[1-9]|[12][0-9]|3[01])[-\\/.](?:0[1-9]|1[012])[-\\/.](?:19\\d{2}|20[01][0-9]|2020)"
    val passwordRegx =
        "[0-9A-Za-z]{6,8}"
    val phoneRegx =
        "(\\([1-9]{2}\\)\\s?)([2-8]|9[1-9])(\\d{3}\\-\\d{4})"
    val cpfRegx =
        "^([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})\$"
    val characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun checkPattern(input: String, rex: String): Boolean {
        val pattern = Regex(rex)
        return pattern.containsMatchIn(input)
    }
}