package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetDestinoProprioImplTest {

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository getDestino`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            whenever(
                movEquipProprioRepository.getDestino(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = IGetDestinoProprio(
                movEquipProprioRepository
            )
            val result = usecase(id = 1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.getDestino"
            )
        }

    @Test
    fun `Check return destino if GetDestino execute success`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            whenever(
                movEquipProprioRepository.getDestino(
                    id = 1
                )
            ).thenReturn(
                Result.success("Destino")
            )
            val usecase = IGetDestinoProprio(
                movEquipProprioRepository,
            )
            val result = usecase(id = 1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, "Destino")
        }
}