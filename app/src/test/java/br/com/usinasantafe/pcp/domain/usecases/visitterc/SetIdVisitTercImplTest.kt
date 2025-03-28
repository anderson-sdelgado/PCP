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

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetTypeVisitTerc`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
            val usecase = ISetIdVisitTerc(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.getTypeVisitTerc"
            )
        }

    @Test
    fun `Check return failure if have error in VisitanteRepository GetId`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
            val usecase = ISetIdVisitTerc(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepository.getId"
            )
        }

    @Test
    fun `Check return failure if have error in TerceiroRepository GetId`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
            val usecase = ISetIdVisitTerc(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepository.getId"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository SetIdVisitTerc`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
            val usecase = ISetIdVisitTerc(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.setIdVisitTerc"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository Add`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
            val usecase = ISetIdVisitTerc(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.add"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository setSend`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
            val usecase = ISetIdVisitTerc(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.setSend"
            )
        }

    @Test
    fun `Check return true if SetIdVisitTercImpl execute successfully MOTORISTA`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
            val usecase = ISetIdVisitTerc(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if SetIdVisitTercImpl execute successfully PASSAGEIRO`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
            val usecase = ISetIdVisitTerc(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return true if SetIdVisitTercImpl execute successfully PASSAGEIRO and FlowApp CHANGE`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
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
            val usecase = ISetIdVisitTerc(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                terceiroRepository,
                visitanteRepository,
                startProcessSendData
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}