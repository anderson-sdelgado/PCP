package br.com.usinasantafe.pcp.domain.usecases.visitterc

import br.com.usinasantafe.pcp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetNomeVisitTercImplTest {

    private val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
    private val terceiroRepository = mock<TerceiroRepository>()
    private val visitanteRepository = mock<VisitanteRepository>()
    private val usecase = IGetNomeVisitTerc(
        movEquipVisitTercRepository = movEquipVisitTercRepository,
        terceiroRepository = terceiroRepository,
        visitanteRepository = visitanteRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetTypeVisitTerc`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
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
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetNomeVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in VisitanteRepository GetNome`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(TypeVisitTerc.VISITANTE)
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
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetNomeVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in TerceiroRepository GetNome`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(TypeVisitTerc.TERCEIRO)
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
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetNomeVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in VisitanteRepository GetEmpresas`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(TypeVisitTerc.VISITANTE)
            )
            whenever(
                visitanteRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success("Visitante")
            )
            whenever(
                visitanteRepository.getEmpresas(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetNomeVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if have error in TerceiroRepository GetEmpresas`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(TypeVisitTerc.TERCEIRO)
            )
            whenever(
                terceiroRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success("Terceiro")
            )
            whenever(
                terceiroRepository.getEmpresas(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetNomeVisitTerc -> Unknown Error"
            )
        }

    @Test
    fun `Check return failure if GetNomeVisitTercImpl execute successfully Visitante`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(TypeVisitTerc.VISITANTE)
            )
            whenever(
                visitanteRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success("Visitante")
            )
            whenever(
                visitanteRepository.getEmpresas(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success("Empresas")
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val model = result.getOrNull()!!
            assertEquals(
                model.tipo,
                "VISITANTE"
            )
            assertEquals(
                model.nome,
                "Visitante"
            )
            assertEquals(
                model.empresa,
                "Empresas"
            )
        }

    @Test
    fun `Check return failure if GetNomeVisitTercImpl execute successfully Terceiro`() =
        runTest {
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(TypeVisitTerc.TERCEIRO)
            )
            whenever(
                terceiroRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success("Terceiro")
            )
            whenever(
                terceiroRepository.getEmpresas(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success("Empresas")
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            val model = result.getOrNull()!!
            assertEquals(
                model.tipo,
                "TERCEIRO"
            )
            assertEquals(
                model.nome,
                "Terceiro"
            )
            assertEquals(
                model.empresa,
                "Empresas"
            )
        }

}