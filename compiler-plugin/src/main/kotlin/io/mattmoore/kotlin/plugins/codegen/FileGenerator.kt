package io.mattmoore.kotlin.plugins.codegen

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.classinspector.elements.ElementsClassInspector
import com.squareup.kotlinpoet.classinspector.reflective.ReflectiveClassInspector
import com.squareup.kotlinpoet.metadata.*
import io.mattmoore.kotlin.plugins.annotations.KotlinBuilder
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.ws.rs.GET
import javax.ws.rs.Path


@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(FileGenerator.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class FileGenerator : AbstractProcessor() {
    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(KotlinBuilder::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    @KotlinPoetMetadataPreview
    override fun process(set: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {
        val elementUtils = processingEnv.elementUtils
        val types = processingEnv.typeUtils
        val elementClassInspector = ElementsClassInspector.create(elementUtils, types)
        val classInspector = ReflectiveClassInspector.create()

        roundEnvironment?.getElementsAnnotatedWith(KotlinBuilder::class.java)
                ?.forEach {
                    val typeElement = it as TypeElement

                    val pack = processingEnv.elementUtils.getPackageOf(typeElement).toString()

                    val kmClass = typeElement.toImmutableKmClass()

                    val className = typeElement.asClassName()

                    val test = elementClassInspector.containerData(kmClass, typeElement.asClassName(), null)
                    val test2 = classInspector.containerData(kmClass, typeElement.asClassName(), null)

                    generateType(typeElement.simpleName.toString(), pack, kmClass)
                }


        return true
    }

    @KotlinPoetMetadataPreview
    public fun generateType(className: String, pack: String, kmClass: ImmutableKmClass) {
        generateClass(className, pack, kmClass)
    }

    @KotlinPoetMetadataPreview
    private fun generateClass(className: String, pack: String, kmClass: ImmutableKmClass) {

        val fileSpec = ResourceDescriptor(
                originalName = className,
                pack = pack,
                kmClass = kmClass
        ).describe()

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        val fileName = "${className}Builder"
        fileSpec.writeTo(File("${kaptKotlinGeneratedDir}/$fileName.kt"))
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}
