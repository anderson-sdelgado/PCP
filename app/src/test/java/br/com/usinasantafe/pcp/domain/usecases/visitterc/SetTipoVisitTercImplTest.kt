package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetTipoVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val usecase = ISetTipoVisitTerc(
        movEquipVisitTercRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository setPlaca`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.setTipoVisitTerc(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                typeVisitTerc = TypeVisitTerc.TERCEIRO
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetTipoVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SetTipoVisitTercImpl execute successfully`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.setTipoVisitTerc(
                    typeVisitTerc = TypeVisitTerc.TERCEIRO
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                typeVisitTerc = TypeVisitTerc.TERCEIRO
            )
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