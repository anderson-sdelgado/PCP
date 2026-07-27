package br.com.usinasantafe.pcp.di.datasource.sharedpreferences

import br.com.usinasantafe.pcp.external.sharedpreferences.datasource.*
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.*
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
    fun bindConfigSharedPreferencesDatasource(usecase: IConfigSharedPreferencesDatasource): ConfigSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovChaveSharedPreferencesDatasource(usecase: IMovChaveSharedPreferencesDatasource): MovChaveSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovChaveEquipSharedPreferencesDatasource(usecase: IMovChaveEquipSharedPreferencesDatasource): MovChaveEquipSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovEquipProprioSharedPreferencesDatasource(usecase: IMovEquipProprioSharedPreferencesDatasource): MovEquipProprioSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovEquipProprioEquipSegSharedPreferencesDatasource(usecase: IMovEquipProprioEquipSegSharedPreferencesDatasource): MovEquipProprioEquipSegSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovEquipProprioPassagSharedPreferencesDatasource(usecase: IMovEquipProprioPassagSharedPreferencesDatasource): MovEquipProprioPassagSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovEquipVisitTercSharedPreferencesDatasource(usecase: IMovEquipVisitTercSharedPreferencesDatasource): MovEquipVisitTercSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovEquipVisitTercPassagSharedPreferencesDatasource(usecase: IMovEquipVisitTercPassagSharedPreferencesDatasource): MovEquipVisitTercPassagSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindMovEquipResidenciaSharedPreferencesDatasource(usecase: IMovEquipResidenciaSharedPreferencesDatasource): MovEquipResidenciaSharedPreferencesDatasource

}
