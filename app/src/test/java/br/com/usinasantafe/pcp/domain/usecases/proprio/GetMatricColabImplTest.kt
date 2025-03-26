package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetMatricColabImplTest {

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository get`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(
            movEquipProprioRepository.getMatricColab(id = 1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val usecase = IGetMatricColab(
            movEquipProprioRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> MovEquipProprioRepository.get")
    }

    @Test
    fun `Check return matricColab if GetMatricColabImpl execute success`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        whenever(
            movEquipProprioRepository.getMatricColab(id = 1)
        ).thenReturn(
            Result.success(19759)
        )
        val usecase = IGetMatricColab(
            movEquipProprioRepository
        )
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "19759")
    }
}