/*
 * Copyright 2019 Appmattus Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appmattus.kotlinfixture.resolver

import com.appmattus.kotlinfixture.Context
import com.appmattus.kotlinfixture.Unresolved
import com.appmattus.kotlinfixture.decorator.nullability.wrapNullability
import kotlin.reflect.KClass
import kotlin.reflect.KType

internal class TupleKTypeResolver : Resolver {

    @Suppress("ReturnCount")
    override fun resolve(context: Context, obj: Any): Any? {
        if (obj is KType && obj.classifier is KClass<*>) {
            return when (obj.classifier as KClass<*>) {
                Pair::class -> generatePair(context, obj)
                Triple::class -> generateTriple(context, obj)
                else -> Unresolved
            }
        }

        return Unresolved
    }

    private fun generatePair(context: Context, obj: KType): Any? = context.wrapNullability(obj) {
        val keyType = obj.arguments[0].type!!
        val valueType = obj.arguments[1].type!!

        val key = resolve(keyType)
        val value = resolve(valueType)

        if (key == Unresolved || value == Unresolved) {
            Unresolved
        } else {
            key to value
        }
    }

    private fun generateTriple(context: Context, obj: KType): Any? = context.wrapNullability(obj) {
        val firstType = obj.arguments[0].type!!
        val secondType = obj.arguments[1].type!!
        val thirdType = obj.arguments[2].type!!

        val first = resolve(firstType)
        val second = resolve(secondType)
        val third = resolve(thirdType)

        if (first == Unresolved || second == Unresolved || third == Unresolved) {
            Unresolved
        } else {
            Triple(first, second, third)
        }
    }
}