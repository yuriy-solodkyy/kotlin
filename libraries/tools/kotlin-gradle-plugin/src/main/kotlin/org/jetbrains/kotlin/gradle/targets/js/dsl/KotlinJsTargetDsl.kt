/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.targets.js.dsl

import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.api.GradleException
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.util.ConfigureUtil
import org.jetbrains.kotlin.gradle.dsl.KotlinJsDce
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinJsCompilation
import org.jetbrains.kotlin.gradle.targets.js.KotlinJsPlatformTestRun
import org.jetbrains.kotlin.gradle.targets.js.KotlinJsReportAggregatingTestRun
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsBinaryContainer
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsExec
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

interface KotlinJsSubTargetContainerDsl : KotlinTarget {
    val nodejs: KotlinJsNodeDsl

    val browser: KotlinJsBrowserDsl

    val isNodejsConfigured: Boolean

    val isBrowserConfigured: Boolean

    fun whenNodejsConfigured(body: KotlinJsNodeDsl.() -> Unit)

    fun whenBrowserConfigured(body: KotlinJsBrowserDsl.() -> Unit)
}

interface KotlinJsTargetDsl : KotlinTarget {
    var moduleName: String?

    fun browser() = browser(Action {})
    fun browser(body: Action<KotlinJsBrowserDsl>)

    @Deprecated(
        "Use browser(Action<KotlinJsBrowserDsl>) instead",
        level = DeprecationLevel.WARNING,
        replaceWith = ReplaceWith(
            "browser(Action(body))",
            "org.gradle.api.Action"
        )
    )
    fun browser(body: KotlinJsBrowserDsl.() -> Unit) {
        browser(Action(body))
    }

    @Deprecated(
        "Use browser(Action<KotlinJsBrowserDsl>) instead",
        level = DeprecationLevel.WARNING,
        replaceWith = ReplaceWith(
            "browser(Action(fn))",
            "org.gradle.api.Action"
        )
    )
    fun browser(fn: Closure<*>) {
        browser(
            Action { ConfigureUtil.configure(fn, this) }
        )
    }

    fun nodejs() = nodejs(Action {})
    fun nodejs(body: Action<KotlinJsNodeDsl>)

    @Deprecated(
        "Use nodejs(Action<KotlinJsNodeDsl>) instead",
        level = DeprecationLevel.WARNING,
        replaceWith = ReplaceWith(
            "nodejs(Action(body))",
            "org.gradle.api.Action"
        )
    )
    fun nodejs(body: KotlinJsNodeDsl.() -> Unit) {
        nodejs(Action(body))
    }

    @Deprecated(
        "Use nodejs(Action<KotlinJsNodeDsl>) instead",
        level = DeprecationLevel.WARNING,
        replaceWith = ReplaceWith(
            "nodejs(Action(fn))",
            "org.gradle.api.Action"
        )
    )
    fun nodejs(fn: Closure<*>) {
        nodejs(
            Action {
                ConfigureUtil.configure(fn, this)
            }
        )
    }

    fun useCommonJs()

    val binaries: KotlinJsBinaryContainer

    @Deprecated(
        message = "produceExecutable() was changed on binaries.executable()",
        replaceWith = ReplaceWith("binaries.executable()"),
        level = DeprecationLevel.ERROR
    )
    fun produceExecutable() {
        throw GradleException("Please change produceExecutable() on binaries.executable()")
    }

    val testRuns: NamedDomainObjectContainer<KotlinJsReportAggregatingTestRun>

    // Need to compatibility when users use KotlinJsCompilation specific in build script
    override val compilations: NamedDomainObjectContainer<out KotlinJsCompilation>
}

interface KotlinJsSubTargetDsl {
    fun testTask(body: Action<KotlinJsTest>)

    @Deprecated(
        "Use testTask(Action<KotlinJsTest>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "testTask(Action(body))",
            "org.gradle.api.Action"
        )
    )
    fun testTask(body: KotlinJsTest.() -> Unit) {
        testTask(Action(body))
    }

    @Deprecated(
        "Use testTask(Action<KotlinJsTest>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "testTask(Action(fn))",
            "org.gradle.api.Action"
        )
    )
    fun testTask(fn: Closure<*>) {
        testTask(
            Action {
                ConfigureUtil.configure(fn, this)
            }
        )
    }

    val testRuns: NamedDomainObjectContainer<KotlinJsPlatformTestRun>
}

interface KotlinJsBrowserDsl : KotlinJsSubTargetDsl {
    fun commonWebpackConfig(body: Action<KotlinWebpackConfig>)

    @Deprecated(
        "Use commonWebpackConfig(Action<KotlinWebpackConfig>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "commonWebpackConfig(Action(body))",
            "org.gradle.api.Action"
        )
    )
    fun commonWebpackConfig(body: KotlinWebpackConfig.() -> Unit) {
        commonWebpackConfig(Action(body))
    }

    @Deprecated(
        "Use commonWebpackConfig(Action<KotlinWebpackConfig>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "commonWebpackConfig(Action(fn))",
            "org.gradle.api.Action"
        )
    )
    fun commonWebpackConfig(fn: Closure<*>) {
        commonWebpackConfig(
            Action {
                ConfigureUtil.configure(fn, this)
            }
        )
    }

    fun runTask(body: Action<KotlinWebpack>)

    @Deprecated(
        "Use runTask(Action<KotlinWebpack>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "runTask(Action(body))",
            "org.gradle.api.Action"
        )
    )
    fun runTask(body: KotlinWebpack.() -> Unit) {
        runTask(Action(body))
    }

    @Deprecated(
        "Use runTask(Action<KotlinWebpack>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "runTask(Action(body))",
            "org.gradle.api.Action"
        )
    )
    fun runTask(fn: Closure<*>) {
        runTask(
            Action {
                ConfigureUtil.configure(fn, this)
            }
        )
    }

    @ExperimentalDistributionDsl
    fun distribution(body: Action<Distribution>)

    @Deprecated(
        "Use distribution(Action<Distribution>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "distribution(Action(body))",
            "org.gradle.api.Action"
        )
    )
    @ExperimentalDistributionDsl
    fun distribution(body: Distribution.() -> Unit) {
        distribution(Action(body))
    }

    @Deprecated(
        "Use distribution(Action<Distribution>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "distribution(Action(fn))",
            "org.gradle.api.Action"
        )
    )
    @ExperimentalDistributionDsl
    fun distribution(fn: Closure<*>) {
        distribution(
            Action {
                ConfigureUtil.configure(fn, this)
            }
        )
    }

    fun webpackTask(body: Action<KotlinWebpack>)

    @Deprecated(
        "Use webpackTask(Action<KotlinWebpack>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "webpackTask(Action(body))",
            "org.gradle.api.Action"
        )
    )
    fun webpackTask(body: KotlinWebpack.() -> Unit) {
        webpackTask(Action(body))
    }

    @Deprecated(
        "Use webpackTask(Action<KotlinWebpack>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "webpackTask(Action(fn))",
            "org.gradle.api.Action"
        )
    )
    fun webpackTask(fn: Closure<*>) {
        webpackTask(
            Action {
                ConfigureUtil.configure(fn, this)
            }
        )
    }

    @ExperimentalDceDsl
    fun dceTask(body: Action<KotlinJsDce>)

    @Deprecated(
        "Use dceTask(Action<KotlinJsDce>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "dceTask(Action(body))",
            "org.gradle.api.Action"
        )
    )
    @ExperimentalDceDsl
    fun dceTask(body: KotlinJsDce.() -> Unit) {
        dceTask(Action(body))
    }

    @Deprecated(
        "Use dceTask(Action<KotlinJsDce>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "dceTask(Action(fn))",
            "org.gradle.api.Action"
        )
    )
    @ExperimentalDceDsl
    fun dceTask(fn: Closure<*>) {
        dceTask(
            Action {
                ConfigureUtil.configure(fn, this)
            }
        )
    }
}

interface KotlinJsNodeDsl : KotlinJsSubTargetDsl {
    fun runTask(body: Action<NodeJsExec>)

    @Deprecated(
        "Use runTask(Action<NodeJsExec>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "runTask(Action(body))",
            "org.gradle.api.Action"
        )
    )
    fun runTask(body: NodeJsExec.() -> Unit) {
        runTask(Action(body))
    }

    @Deprecated(
        "Use runTask(Action<NodeJsExec>) instead",
        level = DeprecationLevel.HIDDEN,
        replaceWith = ReplaceWith(
            "runTask(Action(fn))",
            "org.gradle.api.Action"
        )
    )
    fun runTask(fn: Closure<*>) {
        runTask(
            Action {
                ConfigureUtil.configure(fn, this)
            }
        )
    }
}