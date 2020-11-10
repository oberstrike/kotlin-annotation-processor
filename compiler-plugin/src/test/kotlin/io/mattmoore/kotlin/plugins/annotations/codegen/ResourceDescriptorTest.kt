package io.mattmoore.kotlin.plugins.annotations.codegen

import com.squareup.kotlinpoet.classinspector.elements.ElementsClassInspector
import com.squareup.kotlinpoet.metadata.KotlinPoetMetadataPreview
import com.squareup.kotlinpoet.metadata.specs.toFileSpec
import com.squareup.kotlinpoet.metadata.toImmutableKmClass
import io.mattmoore.kotlin.plugins.codegen.FileGenerator
import io.mattmoore.kotlin.plugins.codegen.ResourceDescriptor
import org.junit.Test
import javax.ws.rs.GET
import javax.ws.rs.Path

class ResourceDescriptorTest {

    @Path(value = "/api/")
    class Data {

        @GET
        fun get(): String = ""

    }

    @KotlinPoetMetadataPreview
    @Test
    fun test() {
        val kmClass = Data::class.toImmutableKmClass()

        val resourceDescriptor = ResourceDescriptor(
                originalName = "IData",
                pack = "test",
                kmClass = kmClass
        )


        val fileSpec = resourceDescriptor.describe()


    }
}