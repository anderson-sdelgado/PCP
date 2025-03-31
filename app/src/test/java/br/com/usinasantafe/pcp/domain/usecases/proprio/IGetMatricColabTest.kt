package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetMatricColabTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val usecase = IGetMatricColab(
        movEquipProprioRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository get`() = runTest {
        whenever(
            movEquipProprioRepository.getMatricColab(id = 1)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(id = 1)
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetMatricColab -> Unknown Error"
        )
    }

    @Test
    fun `Check return matricColab if GetMatricColabImpl execute success`() = runTest {
        whenever(
            movEquipProprioRepository.getMatricColab(id = 1)
        ).thenReturn(
            Result.success(19759)
        )
        val result = usecase(id = 1)
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            "19759"
        )
    }
}