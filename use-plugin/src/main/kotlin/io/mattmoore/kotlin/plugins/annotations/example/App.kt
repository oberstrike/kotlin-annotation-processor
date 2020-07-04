package io.mattmoore.kotlin.plugins.annotations.example

import io.mattmoore.kotlin.plugins.annotations.GreetingGenerator

@GreetingGenerator
class Foo

fun main(args: Array<String>) {
  println(GeneratedFoo().greeting())
}
