package br.com.usinasantafe.pcp.module.datasource.sharedpreferences

import br.com.usinasantafe.pcp.external.sharedpreferences.*
import br.com.usinasantafe.pcp.infra.datasource.sharedpreferences.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SharedPreferencesDatasourceModule {

    @Binds
    @Singleton
    fun bindConfigDatasourceSharedPreferences(dataSource: ConfigSharedPreferencesDatasourceImpl): ConfigSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovEquipProprioDatasourceSharedPreferences(dataSource: MovEquipProprioSharedPreferencesDatasourceImpl): MovEquipProprioSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovEquipProprioPassagDatasourceSharedPreferences(dataSource: MovEquipProprioPassagSharedPreferencesDatasourceImpl): MovEquipProprioPassagSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovEquipProprioSegDatasourceSharedPreferences(dataSource: MovEquipProprioSegSharedPreferencesDatasourceImpl): MovEquipProprioSegSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovEquipResidenciaDatasourceSharedPreferences(dataSource: MovEquipResidenciaSharedPreferencesDatasourceImpl): MovEquipResidenciaSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovEquipVisitTercDatasourceSharedPreferences(dataSource: MovEquipVisitTercSharedPreferencesDatasourceImpl): MovEquipVisitTercSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovEquipVisitTercPassagDatasourceSharedPreferences(dataSource: MovEquipVisitTercPassagSharedPreferencesDatasourceImpl): MovEquipVisitTercPassagSharedPreferencesDatasource

}