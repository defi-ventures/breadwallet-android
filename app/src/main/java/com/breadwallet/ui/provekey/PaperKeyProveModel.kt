/**
 * BreadWallet
 *
 * Created by Pablo Budelli on <pablo.budelli@breadwallet.com> 10/10/19.
 * Copyright (c) 2019 breadwallet LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.breadwallet.ui.provekey

import com.breadwallet.ui.navigation.OnCompleteAction

data class PaperKeyProveModel(
    /** Paper key */
    val phrase: List<String>,
    /** Position of the first word to be verified */
    val firstWordIndex: Int,
    /** Position of the second word to be verified */
    val secondWordIndex: Int,
    /** Action to be done when dismissing the flow */
    val onComplete: OnCompleteAction,
    /** Current state of the first input field */
    val firstWordState: WordState = WordState.EMPTY,
    /** Current state of the second input field */
    val secondWordSate: WordState = WordState.EMPTY,
    /** Flag to show animation when the words were validated */
    val showBreadSignal: Boolean = false
) {
    companion object {
        private const val WORD_COUNT = 12
        fun createDefault(phrase: List<String>, onComplete: OnCompleteAction): PaperKeyProveModel {
            check(phrase.size == WORD_COUNT) { "Paper key must contain $WORD_COUNT words" }
            val indices = (0 until WORD_COUNT).shuffled()
            val firstWord = indices[0]
            val secondWord = indices[1]
            return PaperKeyProveModel(phrase, firstWord, secondWord, onComplete)
        }
    }

    /** Used to represent the state of the content of an input field */
    enum class WordState { EMPTY, INVALID, VALID }

    val firstWord: String get() = phrase[firstWordIndex]
    val secondWord: String get() = phrase[secondWordIndex]
    override fun toString() =
        "PaperKeyProveModel(phrase=${phrase.size}, firstWordIndex=$firstWordIndex, " +
            "secondWordIndex=$secondWordIndex, onComplete=$onComplete, firstWordState=$firstWordState, " +
            "secondWordSate=$secondWordSate, showBreadSignal=$showBreadSignal)"
}