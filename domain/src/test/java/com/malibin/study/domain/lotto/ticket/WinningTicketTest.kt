package com.malibin.study.domain.lotto.ticket

import com.google.common.truth.Truth.assertThat
import com.malibin.study.domain.lotto.LottoNumber
import com.malibin.study.domain.lotto.result.Prize
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class WinningTicketTest {
    @Test
    fun `당첨 번호 6개에 보너스 번호가 포함될 수 없다`() {
        //given
        val winningNumbers = LottoTicket(1, 2, 3, 4, 5, 6)
        val bonusNumber = LottoNumber.of(6)
        //when
        val actualException =
            runCatching { WinningTicket(winningNumbers, bonusNumber) }.exceptionOrNull()
        //then
        assertAll(
            { assertThat(actualException).isInstanceOf(IllegalArgumentException()::class.java) },
            {
                assertThat(actualException).hasMessageThat()
                    .isEqualTo("보너스 번호 ($bonusNumber)는 당첨번호($winningNumbers)와 중복될 수 없습니다.")
            }
        )
    }

    @MethodSource("provideLottoTicketsAndPrizes")
    @ParameterizedTest
    fun `당첨 티켓과 각 로또 티켓을 비교하면 로또 순위를 반환한다`(otherTicket: LottoTicket, prize: Prize) {
        //given
        val winningNumbers = LottoTicket(1, 2, 3, 4, 5, 6)
        val bonusNumber = LottoNumber.of(20)
        val winningTicket = WinningTicket(winningNumbers, bonusNumber)
        //when
        val actualPrize = winningTicket.compareWith(otherTicket)
        //then
        assertThat(actualPrize).isEqualTo(prize)
    }

    @Test
    fun `복수의 티켓과 당첨번호를 비교해 결과를 출력한다`() {
        //given
        val otherTickets = listOf(
            LottoTicket(1, 2, 3, 4, 5, 6),
            LottoTicket(1, 2, 3, 4, 5, 20),
            LottoTicket(1, 2, 3, 4, 5, 7),
            LottoTicket(1, 2, 3, 4, 5, 8),
            LottoTicket(1, 2, 3, 4, 5, 9),
        )
        val winningNumbers = LottoTicket(1, 2, 3, 4, 5, 6)
        val bonusNumber = LottoNumber.of(20)
        val winningTicket = WinningTicket(winningNumbers, bonusNumber)
        val expectedPrize = mapOf(Prize.First to 1, Prize.Second to 2, Prize.Third to 3)
        //when
        val actualPrize = winningTicket.compareWith(otherTickets)
        //then
        assertThat(actualPrize).isEqualTo(
            expectedPrize
        )
    }

    companion object {
        @JvmStatic
        fun provideLottoTicketsAndPrizes(): List<Arguments> {
            return listOf(
                Arguments.of(LottoTicket(1, 2, 3, 4, 5, 6), Prize.First), //1등
                Arguments.of(LottoTicket(1, 2, 3, 4, 5, 20), Prize.Second), //2등
                Arguments.of(LottoTicket(1, 2, 3, 4, 5, 7), Prize.Third), //3등
                Arguments.of(LottoTicket(1, 2, 3, 4, 7, 8), Prize.Fourth), //4등
                Arguments.of(LottoTicket(1, 2, 3, 7, 8, 9), Prize.Fifth), //5등
                Arguments.of(LottoTicket(1, 2, 7, 8, 9, 10), Prize.Lose), //Lose
            )
        }
    }
}

