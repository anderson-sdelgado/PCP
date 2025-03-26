package br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcp.domain.repositories.stable.LocalTrabRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class ISaveLocalTrabTest {

    @Test
    fun `Check execution correct`() = runTest {
        val localTrabList = listOf(
            LocalTrab(
                idLocalTrab = 1,
                descrLocalTrab = "02 - Sala TI"
            )
        )
        val localTrabRepository = Mockito.mock<LocalTrabRepository>()
        whenever(
            localTrabRepository.addAll(localTrabList)
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ISaveLocalTrab(localTrabRepository)
        val result = usecase(localTrabList)
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
        val localTrabList = listOf(
            LocalTrab(
                idLocalTrab = 1,
                descrLocalTrab = "02 - Sala TI"
            )
        )
        val localTrabRepository = Mockito.mock<LocalTrabRepository>()
        whenever(
            localTrabRepository.addAll(localTrabList)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = ISaveLocalTrab(localTrabRepository)
        val result = usecase(localTrabList)
        assertEquals(
            result.isFailure,
            true)

        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> LocalTrabRepository.addAll"
        )
    }
}