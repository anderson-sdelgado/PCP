package br.com.usinasantafe.pcp.module.datasource.webservice

import br.com.usinasantafe.pcp.external.webservices.datasource.stable.*
import br.com.usinasantafe.pcp.infra.datasource.webservice.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableWebServiceDatasourceModule {

    @Binds
    @Singleton
    fun bindColabDatasource(dataSource: ColabRetrofitDatasourceImpl): ColabRetrofitDatasource

    @Binds
    @Singleton
    fun bindEquipDatasource(dataSource: EquipRetrofitDatasourceImpl): EquipRetrofitDatasource

    @Binds
    @Singleton
    fun bindLocalDatasource(dataSource: LocalRetrofitDatasourceImpl): LocalRetrofitDatasource

    @Binds
    @Singleton
    fun bindTerceiroDatasource(dataSource: TerceiroRetrofitDatasourceImpl): TerceiroRetrofitDatasource

    @Binds
    @Singleton
    fun bindVisitanteDatasource(dataSource: VisitanteRetrofitDatasourceImpl): VisitanteRetrofitDatasource

}