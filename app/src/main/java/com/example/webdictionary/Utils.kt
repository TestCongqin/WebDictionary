package com.example.webdictionary

object Utils {


    fun isAlpha(c: Char) =  c in 'a'..'z' || c in 'A'..'Z'

    fun isAlphaWord(word: CharSequence) = word.filterNot { isAlpha(it) }.isEmpty()

}