package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetPassagVisitTercListImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
    private val terceiroRepository = mock<TerceiroRepository>()
    private val visitanteRepository = mock<VisitanteRepository>()
    private val usecase = IGetPassagVisitTercList(
        movEquipVisitTercRepository = movEquipVisitTercRepository,
        movEquipVisitTercPassagRepository = movEquipVisitTercPassagRepository,
        terceiroRepository = terceiroRepository,
        visitanteRepository = visitanteRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository list Visitante`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetPassagVisitTercList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetTypeVisitTerc`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idVisitTerc = 1
                        )
                    )
                )
            )
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
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetPassagVisitTercList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in VisitanteRepository getCpf`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idVisitTerc = 1
                        )
                    )
                )
            )
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
                visitanteRepository.getCpf(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetPassagVisitTercList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in VisitanteRepository getNome`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idVisitTerc = 1
                        )
                    )
                )
            )
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
                visitanteRepository.getCpf(
                    id = 1
                )
            ).thenReturn(
                Result.success("123.456.789-00")
            )
            whenever(
                visitanteRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetPassagVisitTercList -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if GetPassagVisitTercListImpl execute successfully Visitante`() =
        runTest {
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idVisitTerc = 1
                        )
                    )
                )
            )
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
                visitanteRepository.getCpf(
                    id = 1
                )
            ).thenReturn(
                Result.success("123.456.789-00")
            )
            whenever(
                visitanteRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(
                    "ANDERSON DA SILVA DELGADO"
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val ret = result.getOrNull()!!
            assertEquals(
                ret.size,
                1
            )
            assertEquals(
                ret[0].id,
                1
            )
            assertEquals(
                ret[0].cpf,
                "123.456.789-00"
            )
            assertEquals(
                ret[0].nome,
                "ANDERSON DA SILVA DELGADO"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository list Terceiro`() =
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
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetPassagVisitTercList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in TerceiroRepository getCpf`() =
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
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idVisitTerc = 1
                        )
                    )
                )
            )
            whenever(
                terceiroRepository.getCpf(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetPassagVisitTercList -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in TerceiroRepository getNome`() =
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
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idVisitTerc = 1
                        )
                    )
                )
            )
            whenever(
                terceiroRepository.getCpf(
                    id = 1
                )
            ).thenReturn(
                Result.success("123.456.789-00")
            )
            whenever(
                terceiroRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetPassagVisitTercList -> Unknown Error"
            )
        }

    @Test
    fun `Check return list if GetPassagVisitTercListImpl execute successfully Terceiro`() =
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
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idVisitTerc = 1
                        )
                    )
                )
            )
            whenever(
                terceiroRepository.getCpf(
                    id = 1
                )
            ).thenReturn(
                Result.success("123.456.789-00")
            )
            whenever(
                terceiroRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(
                    "ANDERSON DA SILVA DELGADO"
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val ret = result.getOrNull()!!
            assertEquals(
                ret.size, 
                1
            )
            assertEquals(
                ret[0].id, 
                1
            )
            assertEquals(
                ret[0].cpf, 
                "123.456.789-00"
            )
            assertEquals(
                ret[0].nome, 
                "ANDERSON DA SILVA DELGADO"
            )
        }
}