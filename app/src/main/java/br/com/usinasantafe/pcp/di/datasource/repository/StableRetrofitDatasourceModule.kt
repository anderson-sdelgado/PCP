package br.com.usinasantafe.pcp.di.datasource.repository

import br.com.usinasantafe.pcp.external.retrofit.datasource.stable.*
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRetrofitDatasourceModule {

    @Binds
    @Singleton
    fun bindChaveRetrofitDatasource(usecase: IChaveRetrofitDatasource): ChaveRetrofitDatasource

    @Binds
    @Singleton
    fun bindColabRetrofitDatasource(usecase: IColabRetrofitDatasource): ColabRetrofitDatasource

    @Binds
    @Singleton
    fun bindEquipRetrofitDatasource(usecase: IEquipRetrofitDatasource): EquipRetrofitDatasource

    @Binds
    @Singleton
    fun bindFluxoRetrofitDatasource(usecase: IFluxoRetrofitDatasource): FluxoRetrofitDatasource

    @Binds
    @Singleton
    fun bindLocalRetrofitDatasource(usecase: ILocalRetrofitDatasource): LocalRetrofitDatasource

    @Binds
    @Singleton
    fun bindLocalTrabRetrofitDatasource(usecase: ILocalTrabRetrofitDatasource): LocalTrabRetrofitDatasource

    @Binds
    @Singleton
    fun bindRLocalFluxoRetrofitDatasource(usecase: IRLocalFluxoRetrofitDatasource): RLocalFluxoRetrofitDatasource

    @Binds
    @Singleton
    fun bindRLocalTerceiroRetrofitDatasource(usecase: ITerceiroRetrofitDatasource): TerceiroRetrofitDatasource

    @Binds
    @Singleton
    fun bindVisitanteRetrofitDatasource(usecase: IVisitanteRetrofitDatasource): VisitanteRetrofitDatasource

}
