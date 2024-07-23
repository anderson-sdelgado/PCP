package br.com.usinasantafe.pcp.module.repository

import br.com.usinasantafe.pcp.domain.repositories.variable.*
import br.com.usinasantafe.pcp.infra.repositories.variable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VariableRepositoryModule {

    @Binds
    @Singleton
    fun bindConfigRepository(repository: ConfigRepositoryImpl): ConfigRepository

    @Binds
    @Singleton
    fun bindMovEquipProprioPassagRepository(repository: MovEquipProprioPassagRepositoryImpl): MovEquipProprioPassagRepository

    @Binds
    @Singleton
    fun bindMovEquipProprioRepository(repository: MovEquipProprioRepositoryImpl): MovEquipProprioRepository

    @Binds
    @Singleton
    fun bindMovEquipProprioSegRepository(repository: MovEquipProprioSegRepositoryImpl): MovEquipProprioSegRepository

    @Binds
    @Singleton
    fun bindMovEquipResidenciaRepository(repository: MovEquipResidenciaRepositoryImpl): MovEquipResidenciaRepository

    @Binds
    @Singleton
    fun bindMovEquipVisitTercPassagRepository(repository: MovEquipVisitTercPassagRepositoryImpl): MovEquipVisitTercPassagRepository

    @Binds
    @Singleton
    fun bindMovEquipVisitTercRepository(repository: MovEquipVisitTercRepositoryImpl): MovEquipVisitTercRepository

}