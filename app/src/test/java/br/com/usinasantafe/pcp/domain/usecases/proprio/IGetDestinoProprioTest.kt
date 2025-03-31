package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetDestinoProprioTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val usecase = IGetDestinoProprio(
        movEquipProprioRepository = movEquipProprioRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipProprioRepository getDestino`() =
        runTest {
            whenever(
                movEquipProprioRepository.getDestino(
                    id = 1
                )
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
                "IGetDestinoProprio -> Unknown Error"
            )
        }

    @Test
    fun `Check return destino if GetDestino execute success`() =
        runTest {
            whenever(
                movEquipProprioRepository.getDestino(
                    id = 1
                )
            ).thenReturn(
                Result.success("Destino")
            )
            val result = usecase(id = 1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "Destino"
            )
        }
}