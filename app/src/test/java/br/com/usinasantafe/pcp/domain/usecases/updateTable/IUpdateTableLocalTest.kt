package br.com.usinasantafe.pcp.domain.usecases.updateTable

import br.com.usinasantafe.pcp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcp.domain.entities.stable.Local
import br.com.usinasantafe.pcp.domain.errors.resultFailure
import br.com.usinasantafe.pcp.domain.usecases.updateTable.update.IUpdateLocal
import br.com.usinasantafe.pcp.domain.usecases.updateTable.cleantable.CleanLocal
import br.com.usinasantafe.pcp.domain.usecases.updateTable.getserver.GetServerLocal
import br.com.usinasantafe.pcp.domain.usecases.updateTable.savetable.SaveLocal
import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateTableLocalTest {

}
