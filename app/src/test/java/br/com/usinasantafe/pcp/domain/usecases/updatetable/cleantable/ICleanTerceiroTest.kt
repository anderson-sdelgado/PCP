package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICleanTerceiroTest {

    private val terceiroRepository = mock<TerceiroRepository>()
    private val usecase = ICleanTerceiro(terceiroRepository)

    @Test
    fun `Check execution correct`() = runTest {
        whenever(
            terceiroRepository.deleteAll()
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

    @Test
    fun `Check execution incorrect`() = runTest {
        whenever(
            terceiroRepository.deleteAll()
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
            "ICleanTerceiro -> Unknown Error"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.Exception"
        )
    }
}