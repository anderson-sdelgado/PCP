package br.com.usinasantafe.pcp.module.repository

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
    fun bindColabRepository(repository: ColabRepositoryImpl): ColabRepository

    @Binds
    @Singleton
    fun bindEquipRepository(repository: EquipRepositoryImpl): EquipRepository

    @Binds
    @Singleton
    fun bindLocalRepository(repository: LocalRepositoryImpl): LocalRepository

    @Binds
    @Singleton
    fun bindTerceiroRepository(repository: TerceiroRepositoryImpl): TerceiroRepository

    @Binds
    @Singleton
    fun bindVisitanteRepository(repository: VisitanteRepositoryImpl): VisitanteRepository

}