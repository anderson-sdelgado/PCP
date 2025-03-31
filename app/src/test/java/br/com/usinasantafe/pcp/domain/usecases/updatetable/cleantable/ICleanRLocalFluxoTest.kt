package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.repositories.stable.RLocalFluxoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICleanRLocalFluxoTest {

    private val rLocalFluxoRepository = mock<RLocalFluxoRepository>()
    private val usecase = ICleanRLocalFluxo(rLocalFluxoRepository)

    @Test
    fun `Check return failure if have error in RLocalFluxoRepository deleteAll`() =
        runTest {
            whenever(
                rLocalFluxoRepository.deleteAll()
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
                "ICleanRLocalFluxo -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if CleanRLocalFluxoImplTest execute successfully`() =
        runTest {
            whenever(
                rLocalFluxoRepository.deleteAll()
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