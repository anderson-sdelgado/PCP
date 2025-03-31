package br.com.usinasantafe.pcp.domain.usecases.updatetable

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.entities.stable.Colab
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateColab
import br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.CleanColab
import br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver.GetServerColab
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveColab
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateColabTest {

    private val cleanColab = mock<CleanColab>()
    private val getServerColab = mock<GetServerColab>()
    private val saveColab = mock<SaveColab>()
    private val usecase = IUpdateColab(
        cleanColab = cleanColab,
        getServerColab = getServerColab,
        saveColab = saveColab,
    )

    @Test
    fun `Check return failure usecase if have error in usecase RecoverColabServer`() =
        runTest {
            var pos = 0f
            whenever(
                getServerColab()
            ).thenReturn(
                resultFailure(
                    context = "GetServerColab",
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
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateColab -> GetServerColab -> java.lang.Exception",
                    msgProgress = "IUpdateColab -> GetServerColab -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanColab`() =
        runTest {
            var pos = 0f
            whenever(
                getServerColab()
            ).thenReturn(
                Result.success(
                    colabList
                )
            )
            whenever(
                cleanColab()
            ).thenReturn(
                resultFailure(
                    context = "CleanColab",
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
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateColab -> CleanColab -> java.lang.Exception",
                    msgProgress = "IUpdateColab -> CleanColab -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllColab`() =
        runTest {
            var pos = 0f
            whenever(
                getServerColab()
            ).thenReturn(
                Result.success(
                    colabList
                )
            )
            whenever(
                cleanColab()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveColab(colabList)
            ).thenReturn(
                resultFailure(
                    context = "SaveColab",
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
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateColab -> SaveColab -> java.lang.Exception",
                    msgProgress = "IUpdateColab -> SaveColab -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateColab execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getServerColab()
            ).thenReturn(
                Result.success(
                    colabList
                )
            )
            whenever(
                cleanColab()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveColab(colabList)
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
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}

val colabList = listOf(
    Colab(
        matricColab = 19759,
        nomeColab = "ANDERSON DA SILVA DELGADO"
    )
)
