package com.appmattus.kotlinfixture.resolver

import com.appmattus.kotlinfixture.Unresolved
import java.math.BigInteger
import kotlin.random.Random
import kotlin.random.asJavaRandom

class BigIntegerResolver : Resolver {

    override fun resolve(obj: Any?, resolver: Resolver): Any? =
        if (obj == BigInteger::class) BigInteger(64, Random.asJavaRandom()) else Unresolved
}