package com.malibin.study.domain.lotto.ticket

import com.google.common.truth.Truth.assertThat
import com.malibin.study.domain.lotto.LottoNumber
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class LottoTicketTest {
    @MethodSource("providesInvalidSizeLottoTickets")
    @ParameterizedTest
    fun `lottoNumber의 size가 6이 아니면 예외를 발생시킨다`(lottoNumbers: IntArray) {
        // when
        val actualException = runCatching { LottoTicket(*lottoNumbers) }.exceptionOrNull()
        // then
        assertAll(
            { assertThat(actualException).isInstanceOf(IllegalArgumentException::class.java) },
            {
                assertThat(actualException).hasMessageThat()
                    .contains("로또 티켓에 번호는 6개만 넣을 수 있습니다. 입력 값 : ${lottoNumbers.contentToString()}")
            }
        )
    }

    @Test
    fun `lottoNumber에는 중복된 숫자가 있어선 안된다`() {
        //when
        val actualException = runCatching { LottoTicket(1, 1, 2, 3, 4, 5) }.exceptionOrNull()
        //then
        assertThat(actualException).isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `특정 숫자가 포함되어있는지 확인할 수 있다`() {
        //when
        val actualResult = LottoTicket(1,2,3,4,5,6).has(LottoNumber.of(7))
        //then
        assertThat(actualResult).isFalse()
    }
    @Test
    fun `로또가 적중한 개수를 반환한다`(){
        //given
        val lottoTicket1 = LottoTicket(1,2,3,4,5,6)
        val lottoTicket2 = LottoTicket(1,2,3,4,5,7)
        //when
        val actualResult = lottoTicket1.countMatchingNumbers(lottoTicket2)
        //then
        assertThat(actualResult).isEqualTo(5)
    }

    companion object {
        @JvmStatic
        fun providesInvalidSizeLottoTickets(): List<Arguments> = listOf(
            Arguments.of(intArrayOf(1, 2, 3, 4)),
            Arguments.of(intArrayOf(1, 2, 3, 4, 5, 6, 7))
        )
    }
}