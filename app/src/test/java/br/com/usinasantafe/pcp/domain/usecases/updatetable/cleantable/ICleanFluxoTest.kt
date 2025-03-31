package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.repositories.stable.FluxoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICleanFluxoTest {

    private val fluxoRepository = mock<FluxoRepository>()
    private val usecase = ICleanFluxo(fluxoRepository)

    @Test
    fun `Check return failure if have error in FluxoRepository DeleteAll`() =
        runTest {
            whenever(
                fluxoRepository.deleteAll()
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
                "ICleanFluxo -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if CleanFluxoImplTest execute successfully`() =
        runTest {
            whenever(
                fluxoRepository.deleteAll()
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