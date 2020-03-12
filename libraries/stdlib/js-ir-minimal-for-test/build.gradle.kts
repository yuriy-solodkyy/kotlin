import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType.IR
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin

plugins {
    kotlin("multiplatform")
}

kotlin {
    js(IR) {
        nodejs()
    }
}

val commonMainSources by task<Sync> {
    val fullCommonMainSources = tasks.getByPath(":kotlin-stdlib-js-ir:commonMainSources")
    dependsOn(fullCommonMainSources)

    from(fullCommonMainSources.outputs.files.singleFile) {
        exclude(
            listOf(
                "libraries/stdlib/unsigned/**",
                "libraries/stdlib/common/src/generated/_Arrays.kt",
                "libraries/stdlib/common/src/generated/_Collections.kt",
                "libraries/stdlib/common/src/generated/_Comparisons.kt",
                "libraries/stdlib/common/src/generated/_Maps.kt",
                "libraries/stdlib/common/src/generated/_Sequences.kt",
                "libraries/stdlib/common/src/generated/_Sets.kt",
                "libraries/stdlib/common/src/generated/_Strings.kt",
                "libraries/stdlib/common/src/generated/_UArrays.kt",
                "libraries/stdlib/common/src/generated/_URanges.kt",
                "libraries/stdlib/common/src/generated/_UCollections.kt",
                "libraries/stdlib/common/src/generated/_UComparisons.kt",
                "libraries/stdlib/common/src/generated/_USequences.kt",
                "libraries/stdlib/common/src/kotlin/SequencesH.kt",
                "libraries/stdlib/common/src/kotlin/TextH.kt",
                "libraries/stdlib/common/src/kotlin/UMath.kt",
                "libraries/stdlib/common/src/kotlin/collections/**",
                "libraries/stdlib/common/src/kotlin/ioH.kt",
                "libraries/stdlib/src/kotlin/collections/**",
                "libraries/stdlib/src/kotlin/experimental/bitwiseOperations.kt",
                "libraries/stdlib/src/kotlin/properties/Delegates.kt",
                "libraries/stdlib/src/kotlin/random/URandom.kt",
                "libraries/stdlib/src/kotlin/text/**",
                "libraries/stdlib/src/kotlin/time/**",
                "libraries/stdlib/src/kotlin/util/KotlinVersion.kt",
                "libraries/stdlib/src/kotlin/util/Tuples.kt"
            )
        )
    }

    into("$buildDir/commonMainSources")
}

val jsMainSources by task<Sync> {
    val fullJsMainSources = tasks.getByPath(":kotlin-stdlib-js-ir:jsMainSources")
    dependsOn(fullJsMainSources)

    from(fullJsMainSources.outputs.files.singleFile) {
        exclude(
            listOf(
                "libraries/stdlib/js-ir/runtime/collectionsHacks.kt",
                "libraries/stdlib/js-ir/src/generated/**",
                "libraries/stdlib/js-ir/src/kotlin/text/**",
                "libraries/stdlib/js/src/jquery/**",
                "libraries/stdlib/js/src/org.w3c/**",
                "libraries/stdlib/js/src/kotlin/char.kt",
                "libraries/stdlib/js/src/kotlin/collections.kt",
                "libraries/stdlib/js/src/kotlin/collections/**",
                "libraries/stdlib/js/src/kotlin/time/**",
                "libraries/stdlib/js/src/kotlin/console.kt",
                "libraries/stdlib/js/src/kotlin/coreDeprecated.kt",
                "libraries/stdlib/js/src/kotlin/date.kt",
                "libraries/stdlib/js/src/kotlin/debug.kt",
                "libraries/stdlib/js/src/kotlin/grouping.kt",
                "libraries/stdlib/js/src/kotlin/json.kt",
                "libraries/stdlib/js/src/kotlin/promise.kt",
                "libraries/stdlib/js/src/kotlin/regexp.kt",
                "libraries/stdlib/js/src/kotlin/sequence.kt",
                "libraries/stdlib/js/src/kotlin/throwableExtensions.kt",
                "libraries/stdlib/js/src/kotlin/text/**",
                "libraries/stdlib/js/src/kotlin/reflect/KTypeHelpers.kt",
                "libraries/stdlib/js/src/kotlin/reflect/KTypeParameterImpl.kt",
                "libraries/stdlib/js/src/kotlin/reflect/KTypeImpl.kt",
                "libraries/stdlib/js/src/kotlin/dom/**",
                "libraries/stdlib/js/src/kotlin/browser/**"
            )
        )
    }

    from("$rootDir/libraries/stdlib/js-ir-minimal-for-test/src")
    into("$buildDir/jsMainSources")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            kotlin.srcDir(commonMainSources.get().destinationDir)
        }
        val jsMain by getting {
           kotlin.srcDir(jsMainSources.get().destinationDir)
        }
    }
}

tasks.withType<KotlinCompile<*>>().configureEach {
    kotlinOptions.freeCompilerArgs += listOf(
        "-Xallow-kotlin-package",
        "-Xallow-result-return-type",
        "-Xuse-experimental=kotlin.Experimental",
        "-Xuse-experimental=kotlin.ExperimentalMultiplatform",
        "-Xuse-experimental=kotlin.contracts.ExperimentalContracts",
        "-Xinline-classes",
        "-Xopt-in=kotlin.RequiresOptIn",
        "-Xopt-in=kotlin.ExperimentalUnsignedTypes",
        "-Xopt-in=kotlin.ExperimentalStdlibApi"
    )
}

tasks.named("compileKotlinJs") {
    (this as KotlinCompile<*>).kotlinOptions.freeCompilerArgs += "-Xir-module-name=kotlin"
    dependsOn(commonMainSources)
    dependsOn(jsMainSources)
}
