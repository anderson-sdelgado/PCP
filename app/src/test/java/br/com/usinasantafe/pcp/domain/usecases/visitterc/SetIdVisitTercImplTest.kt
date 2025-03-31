package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeOcupante
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetIdVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
    private val terceiroRepository = mock<TerceiroRepository>()
    private val visitanteRepository = mock<VisitanteRepository>()
    private val startProcessSendData = mock<StartProcessSendData>()
    private val usecase = ISetIdVisitTerc(
        movEquipVisitTercRepository,
        movEquipVisitTercPassagRepository,
        terceiroRepository,
        visitanteRepository,
        startProcessSendData
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetTypeVisitTerc`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
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
                "ISetIdVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in VisitanteRepository GetId`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.VISITANTE
                )
            )
            whenever(
                visitanteRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
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
                "ISetIdVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in TerceiroRepository GetId`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                terceiroRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
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
                "ISetIdVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository SetIdVisitTerc`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                terceiroRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercRepository.setIdVisitTerc(
                    idVisitTerc = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
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
                "ISetIdVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository Add`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.VISITANTE
                )
            )
            whenever(
                visitanteRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercPassagRepository.add(
                    idVisitTerc = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
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
                "ISetIdVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository setSend`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.VISITANTE
                )
            )
            whenever(
                visitanteRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercPassagRepository.add(
                    idVisitTerc = 1,
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercRepository.setSend(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
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
                "ISetIdVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return true if SetIdVisitTercImpl execute successfully MOTORISTA`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                terceiroRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercRepository.setIdVisitTerc(
                    idVisitTerc = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                cpf = "123.456.789-00",
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
    fun `Check return true if SetIdVisitTercImpl execute successfully PASSAGEIRO`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.VISITANTE
                )
            )
            whenever(
                visitanteRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercPassagRepository.add(
                    idVisitTerc = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                cpf = "123.456.789-00",
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
    fun `Check return true if SetIdVisitTercImpl execute successfully PASSAGEIRO and FlowApp CHANGE`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.VISITANTE
                )
            )
            whenever(
                visitanteRepository.getId(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                movEquipVisitTercPassagRepository.add(
                    idVisitTerc = 1,
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                movEquipVisitTercRepository.setSend(
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.PASSAGEIRO,
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