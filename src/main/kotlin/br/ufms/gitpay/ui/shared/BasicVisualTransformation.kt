package br.ufms.gitpay.ui.shared

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

open class BasicVisualTransformation(
    private val maskMap: Map<Int, String>
) : VisualTransformation {

    private var offsetMap = BasicOffsetMapping(maskMap.mapValues { (_, pattern) -> pattern.length - 2 })

    constructor(mask: String) : this(genAnnotatedMap(mask))

    override fun filter(text: AnnotatedString): TransformedText {
        val mask = text.text.mapIndexed { index, c ->
            maskMap[index]?.replace("\$c", "$c") ?: c
        }.joinToString(separator = "")
        return TransformedText(AnnotatedString(mask), offsetMap)
    }

    class BasicOffsetMapping(
        private var offsetMap: Map<Int, Int>
    ) : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int {
            return offset + offsetMap.filter { it.key < offset }.values.sum()
        }

        override fun transformedToOriginal(offset: Int): Int {
            return offset - offsetMap.filter { it.key < offset }.values.sum()
        }
    }
}

private fun genAnnotatedMap(mask: String): Map<Int, String> {
    val maskChar = setOf('#', '?', '9', 'A', 'a', '*', '$')
    val annotatedMap = mutableMapOf<Int, String>()

    var i = 0
    var validChar = 0
    while (i < mask.length) {
        var j = if (mask[i] in maskChar) {
            validChar++; i + 1
        } else i
        var substr = ""
        while (j < mask.length && mask[j] !in maskChar) {
            substr += mask[j]
            j++
        }
        if (substr.isNotEmpty()) {
            if (i == 0) {
                annotatedMap[validChar] = "$substr\$c"
            } else {
                annotatedMap[validChar - 1] = "\$c$substr"
            }
        }
        i = j
    }
    return annotatedMap
}
