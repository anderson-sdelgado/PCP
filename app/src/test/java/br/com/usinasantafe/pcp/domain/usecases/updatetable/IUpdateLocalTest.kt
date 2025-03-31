package br.com.usinasantafe.pcp.domain.usecases.updatetable

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateLocal
import br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.CleanLocal
import br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver.GetServerLocal
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveLocal
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateLocalTest {

    private val cleanLocal = mock<CleanLocal>()
    private val getServerLocal = mock<GetServerLocal>()
    private val saveLocal = mock<SaveLocal>()
    private val usecase = IUpdateLocal(
        cleanLocal = cleanLocal,
        getServerLocal = getServerLocal,
        saveLocal = saveLocal,
    )

    @Test
    fun `check return failure usecase if have error in usecase RecoverLocalServer`() =
        runTest {
            var pos = 0f
            whenever(
                getServerLocal()
            ).thenReturn(
                resultFailure(
                    context = "GetServerLocal",
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
                    msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateLocal -> GetServerLocal -> java.lang.Exception",
                    msgProgress = "IUpdateLocal -> GetServerLocal -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanLocal`() =
        runTest {
            var pos = 0f
            whenever(
                getServerLocal()
            ).thenReturn(
                Result.success(
                    localList
                )
            )
            whenever(
                cleanLocal()
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
                    msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateLocal -> CleanLocal -> java.lang.Exception",
                    msgProgress = "IUpdateLocal -> CleanLocal -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllLocal`() =
        runTest {
            var pos = 0f
            whenever(
                getServerLocal()
            ).thenReturn(
                Result.success(
                    localList
                )
            )
            whenever(
                cleanLocal()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveLocal(localList)
            ).thenReturn(
                resultFailure(
                    context = "SaveLocal",
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
                    msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_local",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateLocal -> SaveLocal -> java.lang.Exception",
                    msgProgress = "IUpdateLocal -> SaveLocal -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateLocal execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getServerLocal()
            ).thenReturn(
                Result.success(
                    localList
                )
            )
            whenever(
                cleanLocal()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveLocal(localList)
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
                    msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_local",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}

val localList = listOf(
    Local(
        idLocal = 1,
        descrLocal = "USINA"
    )
)
