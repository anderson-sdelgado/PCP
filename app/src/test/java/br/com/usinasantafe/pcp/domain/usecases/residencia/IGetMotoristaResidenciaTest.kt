package br.com.usinasantafe.pcp.domain.usecases.residencia

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipResidenciaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetMotoristaResidenciaTest {

    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
    private val usecase = IGetMotoristaResidencia(
        movEquipResidenciaRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository GetMotorista`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.getMotorista(
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
                "IGetMotoristaResidencia -> Unknown Error"
            )
        }

    @Test
    fun `Check return motorista if GetMotoristaResidenciaImpl execute successfully`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.getMotorista(
                    id = 1
                )
            ).thenReturn(
                Result.success("Motorista")
            )
            val usecase = IGetMotoristaResidencia(
                movEquipResidenciaRepository
            )
            val result = usecase(id = 1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "Motorista"
            )
        }
}