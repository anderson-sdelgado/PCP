package br.com.usinasantafe.pcp.domain.usecases.proprio

import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeOcupante
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ISetMatricColabTest {

    private val movEquipProprioRepository = mock<MovEquipProprioRepository>()
    private val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISetMatricColab(
        movEquipProprioRepository,
        movEquipProprioPassagRepository,
        startProcessSendData
    )

    @Test
    fun `Check return failure Usecase if matric is numeric invalid`() = runTest {
        val result = usecase(
            matricColab = "19759a",
            flowApp = FlowApp.ADD,
            typeOcupante = TypeOcupante.MOTORISTA,
            id = 0
        )
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "ISetMatricColab"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NumberFormatException: For input string: \"19759a\""
        )
    }

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioRepository setMatricMotorista in FlowApp ADD and TypeOcupante MOTORISTA`() =
        runTest {
            whenever(
                movEquipProprioRepository.setMatricColab(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetMatricColab -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                Exception().toString()
            )
        }

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioRepository setMatricMotorista in FlowApp CHANGE and TypeOcupante MOTORISTA`() =
        runTest {
            whenever(
                movEquipProprioRepository.setMatricColab(
                    matricColab = 19759,
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetMatricColab -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                Exception().toString()
            )
        }

    @Test
    fun `Check return true if MovEquipProprioRepository setMatricMotorista execute success in FlowApp ADD and TypeOcupante MOTORISTA`() =
        runTest {
            whenever(
                movEquipProprioRepository.setMatricColab(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
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
    fun `Check return true if MovEquipProprioRepository setMatricMotorista execute success in FlowApp CHANGE and TypeOcupante MOTORISTA`() =
        runTest {
            whenever(
                movEquipProprioRepository.setMatricColab(
                    matricColab = 19759,
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISetMatricColab(
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                startProcessSendData
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.MOTORISTA,
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

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioPassagRepository add in FlowApp ADD and TypeOcupante PASSAGEIRO`() =
        runTest {
            whenever(
                movEquipProprioPassagRepository.add(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetMatricColab -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                Exception().toString()
            )
        }

    @Test
    fun `Check return failure Datasource if have error in MovEquipProprioPassagRepository add in FlowApp CHANGE and TypeOcupante PASSAGEIRO`() =
        runTest {
            whenever(
                movEquipProprioPassagRepository.add(
                    matricColab = 19759,
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val usecase = ISetMatricColab(
                movEquipProprioRepository,
                movEquipProprioPassagRepository,
                startProcessSendData
            )
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetMatricColab -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                Exception().toString()
            )
        }

    @Test
    fun `Check return failure if have error in setSend in MovEquipProprioModelRoom in in FlowApp CHANGE and TypeOcupante PASSAGEIRO`() =
        runTest {
            whenever(
                movEquipProprioPassagRepository.add(
                    matricColab = 19759,
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
                matricColab = "19759",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetMatricColab -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                Exception().toString()
            )
        }

    @Test
    fun `Check return true if MovEquipProprioPassagRepository add execute success in FlowApp ADD and TypeOcupante PASSAGEIRO`() = runTest {
        whenever(
            movEquipProprioPassagRepository.add(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ISetMatricColab(
            movEquipProprioRepository,
            movEquipProprioPassagRepository,
            startProcessSendData
        )
        val result = usecase(
            matricColab = "19759",
            flowApp = FlowApp.ADD,
            typeOcupante = TypeOcupante.PASSAGEIRO,
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
    fun `Check return true if MovEquipProprioPassagRepository add execute success in FlowApp CHANGE and TypeOcupante PASSAGEIRO`() = runTest {
        whenever(
            movEquipProprioPassagRepository.add(
                matricColab = 19759,
                flowApp = FlowApp.CHANGE,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            movEquipProprioRepository.setSend(1)
        ).thenReturn(
            Result.success(true)
        )
        val result = usecase(
            matricColab = "19759",
            flowApp = FlowApp.CHANGE,
            typeOcupante = TypeOcupante.PASSAGEIRO,
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

}