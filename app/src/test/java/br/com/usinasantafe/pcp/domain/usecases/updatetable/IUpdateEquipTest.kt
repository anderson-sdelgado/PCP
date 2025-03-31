package br.com.usinasantafe.pcp.domain.usecases.updatetable

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateEquip
import br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.CleanEquip
import br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver.GetServerEquip
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.SaveEquip
import br.com.usinasantafe.pcp.utils.Errors
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateEquipTest {

    private val cleanEquip = mock<CleanEquip>()
    private val getServerEquip = mock<GetServerEquip>()
    private val saveEquip = mock<SaveEquip>()
    private val usecase = IUpdateEquip(
        cleanEquip = cleanEquip,
        getServerEquip = getServerEquip,
        saveEquip = saveEquip,
    )

    @Test
    fun `check return failure usecase if have error in usecase RecoverEquipServer`() =
        runTest {
            var pos = 0f
            whenever(
                getServerEquip()
            ).thenReturn(
                resultFailure(
                    context = "GetServerEquip",
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
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateEquip -> GetServerEquip -> java.lang.Exception",
                    msgProgress = "IUpdateEquip -> GetServerEquip -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanEquip`() =
        runTest {
            var pos = 0f
            whenever(
                getServerEquip()
            ).thenReturn(
                Result.success(
                    equipList
                )
            )
            whenever(
                cleanEquip()
            ).thenReturn(
                resultFailure(
                    context = "CleanEquip",
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
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateEquip -> CleanEquip -> java.lang.Exception",
                    msgProgress = "IUpdateEquip -> CleanEquip -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllEquip`() =
        runTest {
            var pos = 0f
            whenever(
                getServerEquip()
            ).thenReturn(
                Result.success(
                    equipList
                )
            )
            whenever(
                cleanEquip()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveEquip(equipList)
            ).thenReturn(
                resultFailure(
                    context = "SaveEquip",
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
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateEquip -> SaveEquip -> java.lang.Exception",
                    msgProgress = "IUpdateEquip -> SaveEquip -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateEquip execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getServerEquip()
            ).thenReturn(
                Result.success(
                    equipList
                )
            )
            whenever(
                cleanEquip()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveEquip(equipList)
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
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}

val equipList = listOf(
    Equip(
        idEquip = 1,
        nroEquip = 10,
        descrEquip = "teste"
    )
)
