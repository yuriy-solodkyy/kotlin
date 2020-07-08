/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.descriptors.commonizer.metadata

import org.jetbrains.kotlin.descriptors.commonizer.Target
import org.jetbrains.kotlin.descriptors.commonizer.cir.CirClassifier
import org.jetbrains.kotlin.descriptors.commonizer.cir.CirHasTypeParameters
import org.jetbrains.kotlin.descriptors.commonizer.cir.CirRoot
import org.jetbrains.kotlin.descriptors.commonizer.mergedtree.CirNode
import org.jetbrains.kotlin.descriptors.commonizer.mergedtree.CirNode.Companion.indexOfCommon
import org.jetbrains.kotlin.descriptors.commonizer.mergedtree.CirRootNode
import org.jetbrains.kotlin.descriptors.commonizer.utils.isUnderStandardKotlinPackages
import org.jetbrains.kotlin.name.ClassId

interface VisitingContext {
    val targetIndex: Int
    val target: Target
    val isCommon: Boolean
    val typeParameterIndexOffset: Int
    val topLevelContext: VisitingContext

    fun resolveClassifier(classId: ClassId): CirClassifier {
        if (classId.packageFqName.isUnderStandardKotlinPackages) {
            TODO("How to fetch the classifier from the built-ins module?")
        } else {
            TODO("How to fetch the classifier from the cache?")
        }
    }

    fun childContext(declarationWithTypeParameters: CirHasTypeParameters): VisitingContext {
        val ownTypeParametersCount = declarationWithTypeParameters.typeParameters.size
        return if (ownTypeParametersCount == 0) this else ChildVisitingContext(this, ownTypeParametersCount)
    }

    companion object {
        fun newContext(rootNode: CirRootNode, targetIndex: Int): VisitingContext =
            TopLevelVisitingContext(rootNode, targetIndex)
    }

    private class TopLevelVisitingContext(
        rootNode: CirRootNode,
        override val targetIndex: Int
    ) : VisitingContext {
        override val isCommon = rootNode.indexOfCommon == targetIndex
        override val target = get<CirRoot>(rootNode)!!.target
        override val typeParameterIndexOffset get() = 0
        override val topLevelContext: VisitingContext get() = this
    }

    private class ChildVisitingContext(
        private val parent: VisitingContext,
        typeParametersCount: Int
    ) : VisitingContext by parent {
        override val typeParameterIndexOffset = parent.typeParameterIndexOffset + typeParametersCount
    }
}

inline fun <reified T : Any> VisitingContext.get(node: CirNode<*, *>): T? =
    (if (isCommon) node.commonDeclaration() else node.targetDeclarations[targetIndex]) as T?
