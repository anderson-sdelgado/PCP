package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISetStatusSentMovChaveEquipTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val usecase = ISetStatusSentMovChaveEquip(movChaveEquipRepository)

    @Test
    fun `Check return failure if have error in MovChaveEquipRepository setSent`() =
        runTest {
            val list = listOf(
                MovChaveEquip(
                    idMovChaveEquip = 1
                )
            )
            whenever(
                movChaveEquipRepository.setSent(list)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(list)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ISetStatusSentMovChaveEquip"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val list = listOf(
                MovChaveEquip(
                    idMovChaveEquip = 1
                )
            )
            whenever(
                movChaveEquipRepository.setSent(list)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(list)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}