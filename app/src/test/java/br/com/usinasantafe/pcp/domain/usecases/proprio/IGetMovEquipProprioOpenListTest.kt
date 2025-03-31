package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Date

class IGetMovEquipProprioOpenListTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val equipRepository = mock<EquipRepository>()
    private val colabRepository = mock<ColabRepository>()
    private val usecase = IGetMovEquipProprioOpenList(
        movEquipProprioRepository,
        equipRepository,
        colabRepository
    )

    @Test
    fun `Check return failure if have failure in MovEquipProprioRepository listOpen`() = runTest {
        whenever(
            movEquipProprioRepository.listOpen()
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
            "IGetMovEquipProprioOpenList -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if not have all fields in MovEquipProprio`() = runTest {
        val movEquipProprioList = listOf(
            MovEquipProprio(
                idMovEquipProprio = 1
            )
        )
        whenever(
            movEquipProprioRepository.listOpen()
        ).thenReturn(
            Result.success(movEquipProprioList)
        )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "IGetMovEquipProprioOpenList"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NullPointerException"
        )
    }

    @Test
    fun `Check return failure if have failure in EquipRepository getNro`() = runTest {
        val movEquipProprioList = listOf(
            MovEquipProprio(
                idMovEquipProprio = 1,
                idEquipMovEquipProprio = 1
            )
        )
        whenever(
            movEquipProprioRepository.listOpen()
        ).thenReturn(
            Result.success(movEquipProprioList)
        )
        whenever(
            equipRepository.getDescr(1)
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
            "IGetMovEquipProprioOpenList -> Unknown Error"
        )
    }

    @Test
    fun `Check return failure if have failure in ColabRepository getNome`() = runTest {
        val movEquipProprioList = listOf(
            MovEquipProprio(
                idMovEquipProprio = 1,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759
            )
        )
        whenever(
            movEquipProprioRepository.listOpen()
        ).thenReturn(
            Result.success(movEquipProprioList)
        )
        whenever(
            equipRepository.getDescr(1)
        ).thenReturn(
            Result.success("Teste")
        )
        whenever(
            colabRepository.getNome(19759)
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
            "IGetMovEquipProprioOpenList -> Unknown Error"
        )
    }

    @Test
    fun `Check return list if all process execute successfully`() = runTest {
        val movEquipProprioList = listOf(
            MovEquipProprio(
                idMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date(1723213270250),
                idEquipMovEquipProprio = 1,
            )
        )
        whenever(
            movEquipProprioRepository.listOpen()
        ).thenReturn(
            Result.success(movEquipProprioList)
        )
        whenever(
            equipRepository.getDescr(1)
        ).thenReturn(
            Result.success("Teste")
        )
        whenever(
            colabRepository.getNome(19759)
        ).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.size,
            1
        )
        assertEquals(
            result.getOrNull()!![0].typeMov,
            "ENTRADA"
        )
        assertEquals(
            result.getOrNull()!![0].dthr,
            "09/08/2024 11:21"
        )
    }
}