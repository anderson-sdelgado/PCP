package br.com.usinasantafe.pcp.di.repository

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
    fun bindConfigRepository(usecase: IConfigRepository): ConfigRepository

    @Binds
    @Singleton
    fun bindMovChaveRepository(usecase: IMovChaveRepository): MovChaveRepository

    @Binds
    @Singleton
    fun bindMovChaveEquipRepository(usecase: IMovChaveEquipRepository): MovChaveEquipRepository

    @Binds
    @Singleton
    fun bindMovEquipProprioRepository(usecase: IMovEquipProprioRepository): MovEquipProprioRepository

    @Binds
    @Singleton
    fun bindMovEquipProprioPassagRepository(usecase: IMovEquipProprioPassagRepository): MovEquipProprioPassagRepository

    @Binds
    @Singleton
    fun bindMovEquipProprioEquipSegRepository(usecase: IMovEquipProprioEquipSegRepository): MovEquipProprioEquipSegRepository

    @Binds
    @Singleton
    fun bindMovEquipVisitTercRepository(usecase: IMovEquipVisitTercRepository): MovEquipVisitTercRepository

    @Binds
    @Singleton
    fun bindMovEquipVisitTercPassagRepository(usecase: IMovEquipVisitTercPassagRepository): MovEquipVisitTercPassagRepository

    @Binds
    @Singleton
    fun bindMovEquipResidenciaRepository(usecase: IMovEquipResidenciaRepository): MovEquipResidenciaRepository

}
