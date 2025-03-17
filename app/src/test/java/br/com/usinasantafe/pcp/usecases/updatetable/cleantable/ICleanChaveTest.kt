package br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcp.domain.errors.DatasourceException
import br.com.usinasantafe.pcp.domain.repositories.stable.ChaveRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICleanChaveTest {

    @Test
    fun `Check execution correct`() = runTest {
        val chaveRepository = mock<ChaveRepository>()
        whenever(
            chaveRepository.deleteAll()
        ).thenReturn(
            Result.success(true)
        )
        val usecase =
            br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.ICleanChave(
                chaveRepository
            )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull(),
            true
        )
    }

    @Test
    fun `Check execution incorrect`() = runTest {
        val colabRepository = mock<ChaveRepository>()
        whenever(
            colabRepository.deleteAll()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ChaveRepository.deleteAll",
                    cause = Exception()
                )
            )
        )
        val usecase =
            br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.ICleanChave(
                colabRepository
            )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> ChaveRepository.deleteAll"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.Exception"
        )
    }

}