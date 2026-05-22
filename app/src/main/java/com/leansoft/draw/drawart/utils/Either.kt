package com.leansoft.draw.drawart.utils

sealed class Either<out L, out R> {

    /**
     * Represents the left side of [Either] class which by convention
     * is a "Failure".
     */
    data class Left<out L>
    constructor(val left: L) : Either<L, Nothing>()

    /**
     * Represents the right side of [Either] class which by convention
     * is a "Success".
     */
    data class Right<out R>
    constructor(val right: R) : Either<Nothing, R>()

    /**
     * Returns true if this is a Right, false otherwise.
     * @see Right
     */
    val isRight get() = this is Right<R>

    /**
     * Returns true if this is a Left, false otherwise.
     * @see Left
     */
    val isLeft get() = this is Left<L>

    /**
     * Applies fnL if this is a Left or fnR if this is a Right.
     * @see Left
     * @see Right
     */
    fun <T> fold(fnL: (L) -> T, fnR: (R) -> T): T =
        when (this) {
            is Left -> fnL(left)
            is Right -> fnR(right)
        }

    /**
     * Applies fnL if this is a Left or fnR if this is a Right.
     *
     * Kotlin Coroutines Support.
     * @see Left
     * @see Right
     */
    suspend fun <T> coFold(fnL: suspend (L) -> T, fnR: suspend (R) -> T): T =
        when (this) {
            is Left -> fnL(left)
            is Right -> fnR(right)
        }

    companion object
}