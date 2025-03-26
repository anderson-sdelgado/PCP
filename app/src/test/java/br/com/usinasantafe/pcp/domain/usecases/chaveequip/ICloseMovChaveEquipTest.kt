package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ICloseMovChaveEquipTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val usecase = ICloseMovChaveEquip(
        movChaveEquipRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveEquipRepository get`() =
        runTest {
            whenever(
                movChaveEquipRepository.setClose(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveEquipRepository.setClose"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(1)
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