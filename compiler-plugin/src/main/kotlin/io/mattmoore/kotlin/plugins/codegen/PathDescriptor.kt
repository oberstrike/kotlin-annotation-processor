package io.mattmoore.kotlin.plugins.codegen

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec

class PathDescriptor : Descriptor<FunSpec> {
    override fun describe(): FunSpec {
        TODO("Not yet implemented")
    }
}

interface Descriptor<T> {
    fun describe(): T
}