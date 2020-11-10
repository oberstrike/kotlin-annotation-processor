package io.mattmoore.kotlin.plugins.annotations.codegen

import com.squareup.kotlinpoet.metadata.*
import io.mattmoore.kotlin.plugins.codegen.KotlinClassGenerator
import org.junit.Test

class KotlinClassGeneratorTest {

    data class Person(
            private val vorname: String = "Test123",
            private val nachname: String = "Test",
            private val telefonnummer: String,
            private val arg2: Int,
            private val arg3: Int,
            private val arg4: Int
    )

    @KotlinPoetMetadataPreview
    @Test
    fun meinTest() {
        val kmClass = Person::class.toImmutableKmClass()

        val kotlinClassGenerator = KotlinClassGenerator(
                packageName = "test",
                originalName = "Person",
                kmClass = kmClass
        )

        val generated = kotlinClassGenerator.generated
        println(generated)

    }

}