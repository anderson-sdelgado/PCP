package br.com.usinasantafe.pcp.di.datasource.room

import br.com.usinasantafe.pcp.external.room.datasource.variable.*
import br.com.usinasantafe.pcp.infra.datasource.room.variable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VariableRoomDatasourceModule {

    @Binds
    @Singleton
    fun bindMovChaveRoomDatasource(usecase: IMovChaveRoomDatasource): MovChaveRoomDatasource

    @Binds
    @Singleton
    fun bindMovChaveEquipRoomDatasource(usecase: IMovChaveEquipRoomDatasource): MovChaveEquipRoomDatasource

    @Binds
    @Singleton
    fun bindMovEquipProprioPassagRoomDatasource(usecase: IMovEquipProprioPassagRoomDatasource): MovEquipProprioPassagRoomDatasource

    @Binds
    @Singleton
    fun bindMovEquipProprioEquipSegRoomDatasource(usecase: IMovEquipProprioEquipSegRoomDatasource): MovEquipProprioEquipSegRoomDatasource

    @Binds
    @Singleton
    fun bindMovEquipProprioRoomDatasource(usecase: IMovEquipProprioRoomDatasource): MovEquipProprioRoomDatasource

    @Binds
    @Singleton
    fun bindMovEquipVisitTercRoomDatasource(usecase: IMovEquipVisitTercRoomDatasource): MovEquipVisitTercRoomDatasource

    @Binds
    @Singleton
    fun bindMovEquipResidenciaRoomDatasource(usecase: IMovEquipResidenciaRoomDatasource): MovEquipResidenciaRoomDatasource

    @Binds
    @Singleton
    fun bindMovEquipVisitTercPassagRoomDatasource(usecase: IMovEquipVisitTercPassagRoomDatasource): MovEquipVisitTercPassagRoomDatasource

}
