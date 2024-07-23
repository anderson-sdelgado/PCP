package br.com.usinasantafe.pcp.infra.datasource.webservice.variable

import br.com.usinasantafe.pcp.infra.models.webservice.ConfigWebServiceModelInput
import br.com.usinasantafe.pcp.infra.models.webservice.ConfigWebServiceModelOutput
import kotlinx.coroutines.flow.Flow

interface ConfigWebServiceDatasource {

    suspend fun recoverToken(config: ConfigWebServiceModelOutput): Flow<Result<ConfigWebServiceModelInput>>

}