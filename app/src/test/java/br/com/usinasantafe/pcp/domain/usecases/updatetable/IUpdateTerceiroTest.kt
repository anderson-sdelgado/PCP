package br.com.usinasantafe.pcp.domain.usecases.updatetable

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateTerceiro
import br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.CleanTerceiro
import br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver.GetServerTerceiro
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveTerceiro
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateTerceiroTest {

    private val cleanTerceiro = mock<CleanTerceiro>()
    private val getServerTerceiro = mock<GetServerTerceiro>()
    private val saveTerceiro = mock<SaveTerceiro>()
    private val usecase = IUpdateTerceiro(
        cleanTerceiro = cleanTerceiro,
        getServerTerceiro = getServerTerceiro,
        saveTerceiro = saveTerceiro,
    )

    @Test
    fun `check return failure usecase if have error in usecase RecoverTerceiroServer`() =
        runTest {
            var pos = 0f
            whenever(
                getServerTerceiro()
            ).thenReturn(
                resultFailure(
                    context = "GetServerTerceiro",
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
                    msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTerceiro -> GetServerTerceiro -> java.lang.Exception",
                    msgProgress = "IUpdateTerceiro -> GetServerTerceiro -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanTerceiro`() =
        runTest {
            var pos = 0f
            whenever(
                getServerTerceiro()
            ).thenReturn(
                Result.success(
                    terceiroList
                )
            )
            whenever(
                cleanTerceiro()
            ).thenReturn(
                resultFailure(
                    context = "CleanLocal",
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
                    msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_terceiro",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTerceiro -> CleanLocal -> java.lang.Exception",
                    msgProgress = "IUpdateTerceiro -> CleanLocal -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllTerceiro`() =
        runTest {
            var pos = 0f
            whenever(
                getServerTerceiro()
            ).thenReturn(
                Result.success(
                    terceiroList
                )
            )
            whenever(
                cleanTerceiro()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveTerceiro(terceiroList)
            ).thenReturn(
                resultFailure(
                    context = "SaveTerceiro",
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
                    msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_terceiro",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_terceiro",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTerceiro -> SaveTerceiro -> java.lang.Exception",
                    msgProgress = "IUpdateTerceiro -> SaveTerceiro -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateTerceiro execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getServerTerceiro()
            ).thenReturn(
                Result.success(
                    terceiroList
                )
            )
            whenever(
                cleanTerceiro()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveTerceiro(terceiroList)
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
                    msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_terceiro",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_terceiro",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}

val terceiroList = listOf(
    Terceiro(
        idTerceiro = 1,
        idBDTerceiro = 1,
        cpfTerceiro = "123.456.789-00",
        nomeTerceiro = "Terceiro",
        empresaTerceiro = "Empresa Terceiro"
    )
)
