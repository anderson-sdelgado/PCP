package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IStartReceiptMovChaveEquipTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val usecase = IStartReceiptMovChaveEquip(
        movChaveEquipRepository = movChaveEquipRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository start`() =
        runTest {
            whenever(
                movChaveEquipRepository.start()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IStartReceiptMovChaveEquip -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if IStartRemoveChave execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRepository.start()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
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