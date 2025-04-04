package br.com.usinasantafe.pcp.domain.usecases.updatetable

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateVisitante
import br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.CleanVisitante
import br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver.GetServerVisitante
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveVisitante
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateVisitanteTest {

    private val cleanVisitante = mock<CleanVisitante>()
    private val getServerVisitante = mock<GetServerVisitante>()
    private val saveVisitante = mock<SaveVisitante>()
    private val usecase = IUpdateVisitante(
        cleanVisitante = cleanVisitante,
        getServerVisitante = getServerVisitante,
        saveVisitante = saveVisitante,
    )

    @Test
    fun `check return failure usecase if have error in usecase RecoverVisitanteServer`() =
        runTest {
            var pos = 0f
            whenever(
                getServerVisitante()
            ).thenReturn(
                resultFailure(
                    context = "GetServerVisitante",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(result.count(), 2)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateVisitante -> GetServerVisitante -> java.lang.Exception",
                    msgProgress = "IUpdateVisitante -> GetServerVisitante -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanVisitante`() =
        runTest {
            var pos = 0f
            whenever(
                getServerVisitante()
            ).thenReturn(
                Result.success(
                    visitanteList
                )
            )
            whenever(
                cleanVisitante()
            ).thenReturn(
                resultFailure(
                    context = "CleanVisitante",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(result.count(), 3)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateVisitante -> CleanVisitante -> java.lang.Exception",
                    msgProgress = "IUpdateVisitante -> CleanVisitante -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllVisitante`() =
        runTest {
            var pos = 0f
            whenever(
                getServerVisitante()
            ).thenReturn(
                Result.success(
                    visitanteList
                )
            )
            whenever(
                cleanVisitante()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveVisitante(visitanteList)
            ).thenReturn(
                resultFailure(
                    context = "SaveVisitante",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(result.count(), 4)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_visitante",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateVisitante -> SaveVisitante -> java.lang.Exception",
                    msgProgress = "IUpdateVisitante -> SaveVisitante -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateVisitante execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getServerVisitante()
            ).thenReturn(
                Result.success(
                    visitanteList
                )
            )
            whenever(
                cleanVisitante()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveVisitante(visitanteList)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(result.count(), 3)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_visitante",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}

val visitanteList = listOf(
    Visitante(
        idVisitante = 1,
        cpfVisitante = "123.456.789-00",
        nomeVisitante = "Visitante",
        empresaVisitante = "Empresa Visitante"
    )
)
