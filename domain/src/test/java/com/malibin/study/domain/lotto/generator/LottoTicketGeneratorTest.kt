package com.malibin.study.domain.lotto.generator

import com.google.common.truth.Truth.assertThat
import com.malibin.study.domain.lotto.LottoNumber
import com.malibin.study.domain.lotto.ticket.LottoTicket
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class LottoTicketGeneratorTest {

    @Test
    fun `6개의 랜덤 번호로 구성된 티켓이 생성된다`() {
        //given
        val ticket = LottoTicket(1, 2, 3, 4, 5, 6)
        val generator = LottoTicketGenerator {
            setOf(
                LottoNumber.of(1),
                LottoNumber.of(2),
                LottoNumber.of(3),
                LottoNumber.of(4),
                LottoNumber.of(5),
                LottoNumber.of(6),
            )
        }
        //when
        val actualLottoTicket = generator.createAutoTicket()
        //then
        assertAll(
            { assertThat(actualLottoTicket).isEqualTo(ticket) }
        )
    }

    @Test
    fun `수동으로 발급한 티켓과 직접 만든 티켓이 일치하는 지를 비교한다`() {
        //given
        val lottoTicket = LottoTicket(1, 2, 3, 4, 5, 6)
        //when
        val actualLottoTicket = LottoTicketGenerator().createManualTicket(setOf(1, 2, 3, 4, 5, 6))
        //then
        assertThat(actualLottoTicket).isEqualTo(lottoTicket)
    }
}