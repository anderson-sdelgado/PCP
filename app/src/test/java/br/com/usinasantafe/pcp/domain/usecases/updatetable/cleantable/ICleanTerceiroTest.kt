package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICleanTerceiroTest {

    @Test
    fun `Check execution correct`() = runTest {
        val terceiroRepository = mock<TerceiroRepository>()
        whenever(
            terceiroRepository.deleteAll()
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ICleanTerceiro(terceiroRepository)
        val result = usecase()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val terceiroRepository = mock<TerceiroRepository>()
        whenever(
            terceiroRepository.deleteAll()
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ICleanTerceiro(terceiroRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> TerceiroRepository.deleteAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.Exception")
    }
}