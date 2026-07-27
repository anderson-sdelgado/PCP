package br.com.usinasantafe.pcp.di.repository

import br.com.usinasantafe.pcp.domain.repositories.stable.*
import br.com.usinasantafe.pcp.infra.repositories.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRepositoryModule {

    @Binds
    @Singleton
    fun bindChaveRepository(usecase: IChaveRepository): ChaveRepository

    @Binds
    @Singleton
    fun bindColabRepository(usecase: IColabRepository): ColabRepository

    @Binds
    @Singleton
    fun bindEquipRepository(usecase: IEquipRepository): EquipRepository

    @Binds
    @Singleton
    fun bindFluxoRepository(usecase: IFluxoRepository): FluxoRepository

    @Binds
    @Singleton
    fun bindLocalRepository(usecase: ILocalRepository): LocalRepository

    @Binds
    @Singleton
    fun bindLocalTrabRepository(usecase: ILocalTrabRepository): LocalTrabRepository

    @Binds
    @Singleton
    fun bindRLocalFluxoRepository(usecase: IRLocalFluxoRepository): RLocalFluxoRepository

    @Binds
    @Singleton
    fun bindTerceiroRepository(usecase: ITerceiroRepository): TerceiroRepository

    @Binds
    @Singleton
    fun bindVisitanteRepository(usecase: IVisitanteRepository): VisitanteRepository

}
