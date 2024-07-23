package br.com.usinasantafe.pcp.module.datasource.room

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
    fun bindMovEquipProprioPassagDatasourceRoom(dataSource: MovEquipProprioPassagDatasourceRoomImpl): MovEquipProprioPassagDatasourceRoom

    @Binds
    @Singleton
    fun bindMovEquipProprioDatasourceRoom(dataSource: MovEquipProprioDatasourceRoomImpl): MovEquipProprioDatasourceRoom

    @Binds
    @Singleton
    fun bindMovEquipProprioSegDatasourceRoom(dataSource: MovEquipProprioSegDatasourceRoomImpl): MovEquipProprioSegDatasourceRoom

    @Binds
    @Singleton
    fun bindMovEquipResidenciaDatasourceRoom(dataSource: MovEquipResidenciaDatasourceRoomImpl): MovEquipResidenciaDatasourceRoom

    @Binds
    @Singleton
    fun bindMovEquipVisitTercDatasourceRoom(dataSource: MovEquipVisitTercDatasourceRoomImpl): MovEquipVisitTercDatasourceRoom

    @Binds
    @Singleton
    fun bindMovEquipVisitTercPassagDatasourceRoom(dataSource: MovEquipVisitTercPassagDatasourceRoomImpl): MovEquipVisitTercPassagDatasourceRoom

}