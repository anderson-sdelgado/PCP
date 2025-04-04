package br.com.usinasantafe.pcp.domain.usecases.updatetable

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.entities.stable.Chave
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.CleanChave
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateChave
import br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver.GetServerChave
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveChave
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateChaveTest {

    private val cleanChave = mock<CleanChave>()
    private val getServerChave = mock<GetServerChave>()
    private val saveChave = mock<SaveChave>()
    private val usecase = IUpdateChave(
        cleanChave = cleanChave,
        getServerChave = getServerChave,
        saveChave = saveChave,
    )

    @Test
    fun `Check return failure usecase if have error in usecase RecoverChaveServer`() =
        runTest {
            var pos = 0f
            whenever(
                getServerChave()
            ).thenReturn(
                resultFailure(
                    context = "GetServerChave",
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
                    msgProgress = "Recuperando dados da tabela tb_chave do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateChave -> GetServerChave -> java.lang.Exception",
                    msgProgress = "IUpdateChave -> GetServerChave -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanChave`() =
        runTest {
            var pos = 0f
            whenever(
                getServerChave()
            ).thenReturn(
                Result.success(
                    chaveList
                )
            )
            whenever(
                cleanChave()
            ).thenReturn(
                resultFailure(
                    context = "CleanChave",
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
                    msgProgress = "Recuperando dados da tabela tb_chave do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_chave",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateChave -> CleanChave -> java.lang.Exception",
                    msgProgress = "IUpdateChave -> CleanChave -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllChave`() =
        runTest {
            var pos = 0f
            whenever(
                getServerChave()
            ).thenReturn(
                Result.success(
                    chaveList
                )
            )
            whenever(
                cleanChave()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveChave(chaveList)
            ).thenReturn(
                resultFailure(
                    context = "SaveChave",
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
                    msgProgress = "Recuperando dados da tabela tb_chave do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_chave",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_chave",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateChave -> SaveChave -> java.lang.Exception",
                    msgProgress = "IUpdateChave -> SaveChave -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateChave execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getServerChave()
            ).thenReturn(
                Result.success(
                    chaveList
                )
            )
            whenever(
                cleanChave()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveChave(chaveList)
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
                    msgProgress = "Recuperando dados da tabela tb_chave do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_chave",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_chave",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}

val chaveList = listOf (
    Chave(
        idChave = 1,
        descrChave = "20",
        idLocalTrab = 1
    )
)