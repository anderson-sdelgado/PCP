package br.com.usinasantafe.pcp.domain.usecases.chaveequip

import br.com.usinasantafe.pcp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.assertEquals

class ICloseAllMovChaveEquipTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val usecase = ICloseAllMovChaveEquip(
        movChaveEquipRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository listOpen`() =
        runTest {
            whenever(
                movChaveEquipRepository.listOpen()
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
                "ICloseAllMovChaveEquip -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveRepository setClose`() =
        runTest {
            val entity = MovChaveEquip(
                idMovChaveEquip = 1,
                dthrMovChaveEquip = Date(),
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                idEquipMovChaveEquip= 1,
                idLocalMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                matricVigiaMovChaveEquip = 19035,
                observMovChaveEquip = "teste",
            )
            whenever(
                movChaveEquipRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        entity
                    )
                )
            )
            whenever(
                movChaveEquipRepository.setClose(1)
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
                "ICloseAllMovChaveEquip -> Unknown Error"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val entity = MovChaveEquip(
                idMovChaveEquip = 1,
                dthrMovChaveEquip = Date(),
                tipoMovChaveEquip = TypeMovKey.RECEIPT,
                idEquipMovChaveEquip= 1,
                idLocalMovChaveEquip = 1,
                matricColabMovChaveEquip = 19759,
                matricVigiaMovChaveEquip = 19035,
                observMovChaveEquip = "teste",
            )
            whenever(
                movChaveEquipRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        entity
                    )
                )
            )
            whenever(
                movChaveEquipRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}