package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.errors.UsecaseException
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IGetObservMovChaveEquipTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val usecase = IGetObservMovChaveEquip(
        movChaveEquipRepository = movChaveEquipRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveEquipRepository getObserv`() =
        runTest {
            whenever(
                movChaveEquipRepository.getObserv(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "IGetObservMovChaveEquip",
                        cause = Exception()
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> IGetObservMovChaveEquip"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRepository.getObserv(1)
            ).thenReturn(
                Result.success("OBSERV")
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "OBSERV"
            )
        }

}