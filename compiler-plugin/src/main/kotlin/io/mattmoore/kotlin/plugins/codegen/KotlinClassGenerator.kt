package io.mattmoore.kotlin.plugins.codegen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.metadata.*
import com.squareup.kotlinpoet.metadata.specs.internal.ClassInspectorUtil
import com.squareup.kotlinpoet.metadata.specs.toTypeSpec
import kotlinx.metadata.*
import kotlinx.metadata.jvm.fieldSignature


@KotlinPoetMetadataPreview
class KotlinClassGenerator(private val packageName: String,
                           private val originalName: String,
                           private val kmClass: ImmutableKmClass

) {
    val generated
        get() = generate()

    lateinit var associations: Map<String, ImmutableKmValueParameter>

    private val className: String = "${originalName}Builder"

    private val builderClass = KmClassifier.Class(packageName.replace(".", "/") + "/$className")

    private val originalClass = KmClassifier.Class(packageName.replace(".", "/") + "/$originalName")

    private var propertyCount = 0

    private fun init() {
        val constructor = kmClass.constructors.first()
        val valueParameters = constructor.valueParameters

        associations = valueParameters.associateBy { param ->
            kmClass.properties.findLast { it.name == param.name }!!
        }.mapKeys { entry -> entry.key.name }
    }

    //Main function
    private fun generate(): FileSpec {
        init()

        val nameProperties = generateProperties()
        val setters = generateSetters()

        val kmClass = KmClass().apply {
            name = className
            flags = flagsOf(Flag.IS_PUBLIC)

        }

        return FileSpec.builder(packageName, className).apply {
            addType(
                    kmClass.toImmutable().toTypeSpec(null).toBuilder().apply {
                        for (setter in setters) {
                            addFunction(setter)
                        }
                        for (property in nameProperties) {
                            addProperty(property)
                        }
                        //       val builder = generateBuildFunction()
                        //              addFunction(builder)

                    }.build()
            )
        }.build()
    }

/*    private fun generateBuildFunction(): FunSpec {
        val variableNames = as .map { it.name }

        var statement = "return $originalName("
        for (name in variableNames) {
            statement += "$name = $name, "
        }

        statement = statement.dropLast(2)
        statement += ")"
        return FunSpec.builder("build")
                .returns(ClassName(packageName, originalName))
                .addStatement(statement)
                .build()
    }*/

    private fun generateProperties(): MutableList<PropertySpec> {
        val properties = mutableListOf<PropertySpec>()
        for (association in associations) {
            val name = association.key
            val param = association.value

            val declaresDefaultValue = param.declaresDefaultValue
            if (declaresDefaultValue)
                continue

            val newReturnType = param.type!!
            val returnTypeClassname = ClassInspectorUtil.createClassName((newReturnType.classifier as KmClassifier.Class).name)

            val longClazz = ClassInspectorUtil.createClassName(KmClassifier.Class(Long::class.qualifiedName!!).name)

            if(propertyCount == 0) propertyCount = 1
            else propertyCount += propertyCount

            val targetProperty = PropertySpec.builder(name, returnTypeClassname)
                    .mutable()
                    .addModifiers(KModifier.PRIVATE)
                    .initializer("null")
                    .setter(FunSpec
                            .setterBuilder()
                            .addParameter("value", returnTypeClassname)
                            .addStatement("field = value")
                            .addStatement("${name}Status = $propertyCount")
                            .build()
                    ).build()

            val statusProperty = PropertySpec.builder("${name}Status", longClazz)
                    .mutable()
                    .initializer("0")
                    .build()

            properties.add(statusProperty)
            properties.add(targetProperty)


        }
        return properties
    }

    private fun generateSetters(): MutableList<FunSpec> {
        val setters = mutableListOf<FunSpec>()

        for (association in associations) {
            val name = association.key
            val param = association.value

            val declaresDefaultValue = param.declaresDefaultValue
            if (declaresDefaultValue)
                continue

            val setterName = "set${name.capitalize()}"

            val builderName = builderClass.name
            val builderClassName = ClassInspectorUtil.createClassName(builderName)

            val newReturnType = param.type!!
            val returnTypeClassname = ClassInspectorUtil.createClassName((newReturnType.classifier as KmClassifier.Class).name)

            setters.add(
                    FunSpec.builder(setterName).apply {
                        returns(builderClassName)
                        parameters += mutableListOf(ParameterSpec.builder(
                                name = "p${name.capitalize()}",
                                type = returnTypeClassname).build())
                        addStatement("return apply { $name = p${name.capitalize()} }")
                    }.build()
            )
        }
        return setters
    }

}