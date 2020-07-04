package io.mattmoore.kotlin.plugins.codegen

import com.google.auto.service.AutoService
import io.mattmoore.kotlin.plugins.annotations.GreetingGenerator
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedOptions
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(FileGenerator.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class FileGenerator : AbstractProcessor() {
  override fun getSupportedAnnotationTypes(): MutableSet<String> {
    return mutableSetOf(GreetingGenerator::class.java.name)
  }

  override fun getSupportedSourceVersion(): SourceVersion {
    return SourceVersion.latest()
  }

  override fun process(set: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {
    roundEnvironment?.getElementsAnnotatedWith(GreetingGenerator::class.java)
      ?.forEach {
        val className = it.simpleName.toString()
        val pack = processingEnv.elementUtils.getPackageOf(it).toString()
        generateClass(className, pack)
      }
    return true
  }

  private fun generateClass(className: String, pack: String) {
    val fileName = "Generated${className}"
    val file = KotlinClassGenerator(fileName, pack).generated
    val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
    file.writeTo(File("${kaptKotlinGeneratedDir}/$fileName.kt"))
  }

  companion object {
    const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
  }
}