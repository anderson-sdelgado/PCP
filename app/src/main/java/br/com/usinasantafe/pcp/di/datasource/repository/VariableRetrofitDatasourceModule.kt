package br.com.usinasantafe.pcp.di.datasource.repository

import br.com.usinasantafe.pcp.external.retrofit.datasource.variable.*
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VariableRetrofitDatasourceModule {

    @Binds
    @Singleton
    fun bindConfigRetrofitDatasource(usecase: IConfigRetrofitDatasource): ConfigRetrofitDatasource

    @Binds
    @Singleton
    fun bindMovChaveRetrofitDatasource(usecase: IMovChaveRetrofitDatasource): MovChaveRetrofitDatasource

    @Binds
    @Singleton
    fun bindMovChaveEquipRetrofitDatasource(usecase: IMovChaveEquipRetrofitDatasource): MovChaveEquipRetrofitDatasource

    @Binds
    @Singleton
    fun bindMovEquipProprioRetrofitDatasource(usecase: IMovEquipProprioRetrofitDatasource): MovEquipProprioRetrofitDatasource

    @Binds
    @Singleton
    fun bindMovEquipVisitTercRetrofitDatasource(usecase: IMovEquipVisitTercRetrofitDatasource): MovEquipVisitTercRetrofitDatasource

    @Binds
    @Singleton
    fun bindMovEquipResidenciaRetrofitDatasource(usecase: IMovEquipResidenciaRetrofitDatasource): MovEquipResidenciaRetrofitDatasource

}
