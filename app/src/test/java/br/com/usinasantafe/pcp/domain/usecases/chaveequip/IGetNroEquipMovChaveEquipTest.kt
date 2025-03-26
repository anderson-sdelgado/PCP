package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IGetNroEquipMovChaveEquipTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = IGetNroEquipMovChaveEquip(
        movChaveEquipRepository = movChaveEquipRepository,
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveEquipRepository getIdEquip`() =
        runTest {
            whenever(
                movChaveEquipRepository.getIdEquip(1)
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
                "Failure Repository -> MovChaveEquipRepository.getIdEquip"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getNro`() =
        runTest {
            whenever(
                movChaveEquipRepository.getIdEquip(1)
            ).thenReturn(
                Result.success(100)
            )
            whenever(
                equipRepository.getNro(100)
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
                "Failure Repository -> EquipRepository.getNro"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRepository.getIdEquip(1)
            ).thenReturn(
                Result.success(100)
            )
            whenever(
                equipRepository.getNro(100)
            ).thenReturn(
                Result.success(300)
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "300"
            )
        }
}