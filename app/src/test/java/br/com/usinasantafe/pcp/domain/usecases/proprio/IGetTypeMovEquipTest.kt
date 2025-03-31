package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetTypeMovEquipTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val usecase = IGetTypeMov(movEquipProprioRepository)

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioRepository getTipo`() =
        runTest {
            whenever(
                movEquipProprioRepository.getTipoMov()
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
                "IGetObservProprio -> Unknown Error"
            )
    }

    @Test
    fun `Check return typeMov if MovEquipProprioRepository getTipo execute success`() =
        runTest {
            whenever(
                movEquipProprioRepository.getTipoMov()
            ).thenReturn(
                Result.success(TypeMovEquip.INPUT)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                TypeMovEquip.INPUT
            )
        }
}