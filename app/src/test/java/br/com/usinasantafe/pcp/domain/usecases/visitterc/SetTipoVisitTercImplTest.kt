package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetTipoVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository setPlaca`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            whenever(
                movEquipVisitTercRepository.setTipoVisitTerc(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ISetTipoVisitTerc(
                movEquipVisitTercRepository
            )
            val result = usecase(
                typeVisitTerc = TypeVisitTerc.TERCEIRO
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.setTipoVisitTerc"
            )
        }

    @Test
    fun `Check return true if SetTipoVisitTercImpl execute successfully`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            whenever(
                movEquipVisitTercRepository.setTipoVisitTerc(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISetTipoVisitTerc(
                movEquipVisitTercRepository
            )
            val result = usecase(
                typeVisitTerc = TypeVisitTerc.TERCEIRO
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

}