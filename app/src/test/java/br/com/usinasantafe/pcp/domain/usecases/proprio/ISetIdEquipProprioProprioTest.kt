package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ISetIdEquipProprioProprioTest {

    private val equipRepository = mock<EquipRepository>()
    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISetIdEquipProprio(
        equipRepository,
        movEquipProprioRepository,
        movEquipProprioEquipSegRepository,
        startProcessSendData
    )

    @Test
    fun `Check return failure Usecase if matric is numeric invalid in FlowApp ADD and TypeEquip VEICULO`() = runTest {
        val result = usecase(
            nroEquip = "10a",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULO,
            id = 0
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ISetIdEquipProprio"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NumberFormatException: For input string: \"10a\""
        )
    }

    @Test
    fun `Check return failure Usecase if matric is numeric invalid in FlowApp CHANGE and TypeEquip VEICULO`() = runTest {
        val result = usecase(
            nroEquip = "10a",
            flowApp = FlowApp.CHANGE,
            typeEquip = TypeEquip.VEICULO,
            id = 0
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ISetIdEquipProprio"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NumberFormatException: For input string: \"10a\""
        )
    }

    @Test
    fun `Check return failure Datasource if have error in EquipRepository getNro`() = runTest {
        whenever(
            equipRepository.getId(100)
        ).thenReturn(
            Result.failure(
                Exception()
            )
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULO,
            id = 0
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ISetIdEquipProprio -> Unknown Error"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            Exception().toString()
        )
    }

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioRepository setNroEquip in FlowApp ADD and TypeEquip VEICULO`() =
        runTest {
            whenever(
                equipRepository.getId(100)
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                movEquipProprioRepository.setIdEquip(
                    idEquip = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                nroEquip = "100",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdEquipProprio -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                Exception().toString()
            )
        }

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioRepository setNroEquip in FlowApp CHANGE and TypeEquip VEICULO`() =
        runTest {
            whenever(equipRepository.getId(100)).thenReturn(
                Result.success(10)
            )
            whenever(
                movEquipProprioRepository.setIdEquip(
                    idEquip = 10,
                    flowApp = FlowApp.CHANGE,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                nroEquip = "100",
                flowApp = FlowApp.CHANGE,
                typeEquip = TypeEquip.VEICULO,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdEquipProprio -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                Exception().toString()
            )
        }

    @Test
    fun `Check return true if MovEquipProprioRepository setNroEquip execute success in FlowApp ADD and TypeEquip VEICULO`() = runTest {
        whenever(
            equipRepository.getId(100)
        ).thenReturn(
            Result.success(10)
        )
        whenever(
            movEquipProprioRepository.setIdEquip(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULO,
            id = 0
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }

    @Test
    fun `Check return true if MovEquipProprioRepository setNroEquip execute success in FlowApp CHANGE and TypeEquip VEICULO`() = runTest {
        whenever(
            equipRepository.getId(100)
        ).thenReturn(
            Result.success(10)
        )
        whenever(
            movEquipProprioRepository.setIdEquip(
                idEquip = 10,
                flowApp = FlowApp.CHANGE,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.CHANGE,
            typeEquip = TypeEquip.VEICULO,
            id = 0
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioEquipSegRepository add in FlowApp ADD and TypeEquip VEICULOSEG`() =
        runTest {
            whenever(
                equipRepository.getId(100)
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                movEquipProprioEquipSegRepository.add(
                    idEquip = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                nroEquip = "100",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULOSEG,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdEquipProprio -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                Exception().toString()
            )
        }

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioEquipSegRepository add in FlowApp CHANGE and TypeEquip VEICULOSEG`() =
        runTest {
            whenever(
                equipRepository.getId(100)
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                movEquipProprioEquipSegRepository.add(
                    idEquip = 10,
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                nroEquip = "100",
                flowApp = FlowApp.CHANGE,
                typeEquip = TypeEquip.VEICULOSEG,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdEquipProprio -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                Exception().toString()
            )
        }

    @Test
    fun `Check return failure if have error in setSend in MovEquipProprioModelRoom in FlowApp CHANGE and TypeEquip VEICULOSEG`() =
        runTest {
            whenever(
                equipRepository.getId(100)
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                movEquipProprioEquipSegRepository.add(
                    idEquip = 10,
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipProprioRepository.setSend(1)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                nroEquip = "100",
                flowApp = FlowApp.CHANGE,
                typeEquip = TypeEquip.VEICULOSEG,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdEquipProprio -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                Exception().toString()
            )
        }

    @Test
    fun `Check return true if MovEquipProprioEquipSegRepository add execute success in FlowApp ADD and TypeEquip VEICULOSEG`() = runTest {
        whenever(
            equipRepository.getId(100)
        ).thenReturn(
            Result.success(10)
        )
        whenever(
            movEquipProprioEquipSegRepository.add(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULOSEG,
            id = 0
        )
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }

    @Test
    fun `Check return true if MovEquipProprioEquipSegRepository add execute success in FlowApp CHANGE and TypeEquip VEICULOSEG`() = runTest {
        whenever(
            equipRepository.getId(100)
        ).thenReturn(
            Result.success(10)
        )
        whenever(
            movEquipProprioEquipSegRepository.add(
                idEquip = 10,
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(movEquipProprioRepository.setSend(1)).thenReturn(
            Result.success(true)
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.CHANGE,
            typeEquip = TypeEquip.VEICULOSEG,
            id = 1
        )
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