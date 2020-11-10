package io.mattmoore.kotlin.plugins.annotations.example

import io.mattmoore.kotlin.plugins.annotations.KotlinBuilder
import kotlin.jvm.Throws

@KotlinBuilder
class Test{

    @Throws(Exception::class)
    fun x() {}
}

fun main() {

}
