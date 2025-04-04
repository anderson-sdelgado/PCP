package br.com.usinasantafe.pcp.domain.usecases.updatetable

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateFluxo
import br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.CleanFluxo
import br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver.GetServerFluxo
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveFluxo
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateFluxoTest {

    private val cleanFluxo = mock<CleanFluxo>()
    private val getServerFluxo = mock<GetServerFluxo>()
    private val saveFluxo = mock<SaveFluxo>()
    private val usecase = IUpdateFluxo(
        cleanFluxo = cleanFluxo,
        getServerFluxo = getServerFluxo,
        saveFluxo = saveFluxo,
    )

    @Test
    fun `check return failure usecase if have error in usecase RecoverFluxoServer`() =
        runTest {
            var pos = 0f
            whenever(
                getServerFluxo()
            ).thenReturn(
                resultFailure(
                    context = "GetServerFluxo",
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
                    msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateFluxo -> GetServerFluxo -> java.lang.Exception",
                    msgProgress = "IUpdateFluxo -> GetServerFluxo -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanFluxo`() =
        runTest {
            var pos = 0f
            whenever(
                getServerFluxo()
            ).thenReturn(
                Result.success(
                    fluxoList
                )
            )
            whenever(
                cleanFluxo()
            ).thenReturn(
                resultFailure(
                    context = "CleanFluxo",
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
                    msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateFluxo -> CleanFluxo -> java.lang.Exception",
                    msgProgress = "IUpdateFluxo -> CleanFluxo -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllFluxo`() =
        runTest {
            var pos = 0f
            whenever(
                getServerFluxo()
            ).thenReturn(
                Result.success(
                    fluxoList
                )
            )
            whenever(
                cleanFluxo()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveFluxo(fluxoList)
            ).thenReturn(
                resultFailure(
                    context = "SaveFluxo",
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
                    msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateFluxo -> SaveFluxo -> java.lang.Exception",
                    msgProgress = "IUpdateFluxo -> SaveFluxo -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateFluxo execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getServerFluxo()
            ).thenReturn(
                Result.success(
                    fluxoList
                )
            )
            whenever(
                cleanFluxo()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveFluxo(fluxoList)
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
                    msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}

val fluxoList = listOf(
    Fluxo(
        idFluxo = 1,
        descrFluxo = "MOV. EQUIP. PRÓPRIO"
    )
)
