package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcp.domain.repositories.stable.RLocalFluxoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISaveRLocalFluxoTest {

    private val rLocalFluxoRepository = mock<RLocalFluxoRepository>()
    private val usecase = ISaveRLocalFluxo(
        rLocalFluxoRepository
    )

    @Test
    fun `Check return failure if have error in RLocalFluxoRepository addAll`() =
        runTest {
            val rLocalFluxoList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idFluxo = 1,
                    idLocal = 1
                )
            )
            whenever(
                rLocalFluxoRepository.addAll(rLocalFluxoList)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(rLocalFluxoList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveRLocalFluxo -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SaveAllRLocalFluxoImplTest execute successfully`() =
        runTest {
            val rLocalFluxoList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idFluxo = 1,
                    idLocal = 1
                )
            )
            whenever(
                rLocalFluxoRepository.addAll(rLocalFluxoList)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(rLocalFluxoList)
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