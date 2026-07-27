package br.com.usinasantafe.pcp.di.datasource.room

import br.com.usinasantafe.pcp.external.room.datasource.stable.*
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
    fun bindChaveRoomDatasource(usecase: IChaveRoomDatasource): ChaveRoomDatasource

    @Binds
    @Singleton
    fun bindColabRoomDatasource(usecase: IColabRoomDatasource): ColabRoomDatasource

    @Binds
    @Singleton
    fun bindEquipRoomDatasource(usecase: IEquipRoomDatasource): EquipRoomDatasource

    @Binds
    @Singleton
    fun bindFluxoRoomDatasource(usecase: IFluxoRoomDatasource): FluxoRoomDatasource

    @Binds
    @Singleton
    fun bindLocalRoomDatasource(usecase: ILocalRoomDatasource): LocalRoomDatasource

    @Binds
    @Singleton
    fun bindLocalTrabRoomDatasource(usecase: ILocalTrabRoomDatasource): LocalTrabRoomDatasource

    @Binds
    @Singleton
    fun bindRLocalFluxoRoomDatasource(usecase: IRLocalFluxoRoomDatasource): RLocalFluxoRoomDatasource

    @Binds
    @Singleton
    fun bindTerceiroRoomDatasource(usecase: ITerceiroRoomDatasource): TerceiroRoomDatasource

    @Binds
    @Singleton
    fun bindVisitanteRoomDatasource(usecase: IVisitanteRoomDatasource): VisitanteRoomDatasource

}
