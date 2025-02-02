package com.malibin.study.domain.lotto

@JvmInline
value class LottoNumber private constructor(
    val number: Int,
) {
    init {
        require(number in RANGE) { "입력 숫자 ($number)는 로또 숫자 범위(1 ~ 45) 내의 숫자가 아닙니다." }
    }

    override fun toString(): String = "$number"

    companion object {
        private const val MIN_LOTTO_NUMBER = 1
        private const val MAX_LOTTO_NUMBER = 45

        val RANGE: IntRange = MIN_LOTTO_NUMBER..MAX_LOTTO_NUMBER

        private val cachedLottoNumbers: Map<Int, LottoNumber> =
            RANGE.associateWith { LottoNumber(it) }

        fun of(number: Int): LottoNumber {
            return cachedLottoNumbers[number]
                ?: throw IllegalArgumentException("입력 숫자 ($number)는 로또 숫자 범위(1 ~ 45) 내의 숫자가 아닙니다.")
        }
    }
}
