package br.com.usinasantafe.pcp.domain.usecases.updateTable

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.entities.stable.Equip
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.IUpdateTableEquip
import br.com.usinasantafe.pcp.domain.usecases.updateTable.cleantable.CleanEquip
import br.com.usinasantafe.pcp.domain.usecases.updateTable.getserver.GetServerEquip
import br.com.usinasantafe.pcp.domain.usecases.updateTable.savetable.SaveEquip
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateTableEquipTest {

}
