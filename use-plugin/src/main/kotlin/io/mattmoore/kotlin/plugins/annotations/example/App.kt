package io.mattmoore.kotlin.plugins.annotations.example

import io.mattmoore.kotlin.plugins.annotations.Greeter

@Greeter
class Greeter

fun main(args: Array<String>) {
  println(GeneratedGreeter().greeting())
}
