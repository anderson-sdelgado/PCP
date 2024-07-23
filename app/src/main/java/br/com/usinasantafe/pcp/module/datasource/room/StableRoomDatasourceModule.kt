package br.com.usinasantafe.pcp.module.datasource.room

import br.com.usinasantafe.pcp.external.room.datasource.stable.ColabRoomDatasourceImpl
import br.com.usinasantafe.pcp.external.room.datasource.stable.EquipRoomDatasourceImpl
import br.com.usinasantafe.pcp.external.room.datasource.stable.LocalRoomDatasourceImpl
import br.com.usinasantafe.pcp.external.room.datasource.stable.TerceiroRoomDatasourceImpl
import br.com.usinasantafe.pcp.external.room.datasource.stable.VisitanteRoomDatasourceImpl
import br.com.usinasantafe.pcp.infra.datasource.room.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRoomDatasourceModule {

    @Binds
    @Singleton
    fun bindColabDatasource(dataSource: ColabRoomDatasourceImpl): ColabRoomDatasource

    @Binds
    @Singleton
    fun bindEquipDatasource(dataSource: EquipRoomDatasourceImpl): EquipRoomDatasource

    @Binds
    @Singleton
    fun bindLocalDatasource(dataSource: LocalRoomDatasourceImpl): LocalRoomDatasource

    @Binds
    @Singleton
    fun bindTerceiroDatasource(dataSource: TerceiroRoomDatasourceImpl): TerceiroRoomDatasource

    @Binds
    @Singleton
    fun bindVisitanteDatasource(dataSource: VisitanteRoomDatasourceImpl): VisitanteRoomDatasource

}