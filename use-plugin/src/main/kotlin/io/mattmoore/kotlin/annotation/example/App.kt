package io.mattmoore.kotlin.annotation.example

import io.mattmoore.kotlin.annotation.GreetingGenerator

@GreetingGenerator
class Foo

fun main(args: Array<String>) {
  println(GeneratedFoo().greeting())
}
