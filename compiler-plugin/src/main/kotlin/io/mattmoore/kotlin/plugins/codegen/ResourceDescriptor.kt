package io.mattmoore.kotlin.plugins.codegen

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.metadata.ImmutableKmClass
import com.squareup.kotlinpoet.metadata.KotlinPoetMetadataPreview
import com.squareup.kotlinpoet.metadata.hasAnnotations
import com.squareup.kotlinpoet.metadata.specs.internal.ClassInspectorUtil
import com.squareup.kotlinpoet.metadata.specs.toFileSpec

@KotlinPoetMetadataPreview
class ResourceDescriptor(
        private val originalName: String,
        private val pack: String,
        private val kmClass: ImmutableKmClass
) : Descriptor<FileSpec> {

    private val className: String = "${originalName}Builder"

    private fun paths() {

        val functions = kmClass.functions

        for (function in functions){
            val hasAnnotation = function.hasAnnotations
            println(hasAnnotation)

        }

    }


    override fun describe(): FileSpec {
        paths()
        return kmClass.toFileSpec(null)
    }
}