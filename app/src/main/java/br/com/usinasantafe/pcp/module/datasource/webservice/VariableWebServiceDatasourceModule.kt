package br.com.usinasantafe.pcp.module.datasource.webservice

import br.com.usinasantafe.pcp.external.webservices.datasource.variable.*
import br.com.usinasantafe.pcp.infra.datasource.webservice.variable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VariableWebServiceDatasourceModule {

    @Binds
    @Singleton
    fun bindConfigWebServiceDatasource(dataSource: ConfigWebServiceDatasourceImpl): ConfigWebServiceDatasource

    @Binds
    @Singleton
    fun bindMovEquipProprioWebServiceDatasource(dataSource: MovEquipProprioDatasourceWebServiceImpl): MovEquipProprioDatasourceWebService

    @Binds
    @Singleton
    fun bindMovEquipVisitTercWebServiceDatasource(dataSource: MovEquipVisitTercDatasourceWebServiceImpl): MovEquipVisitTercDatasourceWebService

    @Binds
    @Singleton
    fun bindMovEquipResidenciaWebServiceDatasource(dataSource: MovEquipResidenciaDatasourceWebServiceImpl): MovEquipResidenciaDatasourceWebService

}