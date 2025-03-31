package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.domain.repositories.stable.FluxoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISaveFluxoTest {

    private val fluxoRepository = mock<FluxoRepository>()
    private val usecase = ISaveFluxo(
        fluxoRepository
    )

    @Test
    fun `Check return failure if have error in FluxoRepository addAll`() =
        runTest {
            val colabList = listOf(
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "MOV. EQUIP. PRÓPRIO"
                )
            )
            whenever(
                fluxoRepository.addAll(colabList)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(colabList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveFluxo -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SaveAllFluxoImplTest execute successfully`() =
        runTest {
            val colabList = listOf(
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "MOV. EQUIP. PRÓPRIO"
                )
            )
            whenever(
                fluxoRepository.addAll(colabList)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(colabList)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(true)
            )
        }

}