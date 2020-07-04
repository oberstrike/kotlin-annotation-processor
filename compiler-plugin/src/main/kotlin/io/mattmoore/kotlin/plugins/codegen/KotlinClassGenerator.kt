package io.mattmoore.kotlin.plugins.codegen

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec

class KotlinClassGenerator(private val className: String, private val packageName: String, private val greeting: String = "Merry Christmas!!") {
  val generated
    get() =
      FileSpec.builder(packageName, className)
        .addType(TypeSpec.classBuilder(className)
          .addFunction(FunSpec.builder("greeting")
            .addStatement("""return "$greeting"""")
            .build())
          .build())
        .build()
}