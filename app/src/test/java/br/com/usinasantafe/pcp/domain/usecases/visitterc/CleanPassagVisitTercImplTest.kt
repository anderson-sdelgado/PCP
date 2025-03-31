package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CleanPassagVisitTercImplTest {

    private val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
    private val usecase = ICleanPassagVisitTerc(movEquipVisitTercPassagRepository)

    @Test
    fun `Check return failure Datasource if have error in cleanPassag`() = runTest {
        whenever(
            movEquipVisitTercPassagRepository.clear()
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
            "ICleanPassagVisitTerc -> Unknown Error"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            Exception().toString()
        )
    }
    
    @Test
    fun `Check return true Datasource if cleanPassag execute success`() = runTest {
        whenever(
            movEquipVisitTercPassagRepository.clear()
        ).thenReturn(
            Result.success(true)
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

}