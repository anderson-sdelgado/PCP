package br.com.usinasantafe.pcp.module.usecases

import br.com.usinasantafe.pcp.domain.usecases.initial.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface InitialModule {

    @Singleton
    @Binds
    fun bindCheckAcessApont(usecase: CheckAcessApontImpl): CheckAcessApont

    @Singleton
    @Binds
    fun bindCheckPasswordConfig(usecase: CheckPasswordConfigImpl): CheckPasswordConfig

    @Singleton
    @Binds
    fun bindInitialConfig(usecase: InitialConfigImpl): InitialConfig

    @Singleton
    @Binds
    fun bindRecoverConfig(usecase: RecoverConfigImpl): RecoverConfig

    @Singleton
    @Binds
    fun bindRecoverListLocal(usecase: RecoverListLocalImpl): RecoverListLocal

    @Singleton
    @Binds
    fun bindRecoverNomeVigia(usecase: RecoverNomeVigiaImpl): RecoverNomeVigia

    @Singleton
    @Binds
    fun bindSetCheckUpdateBDConfig(usecase: SetCheckUpdateBDConfigImpl): SetCheckUpdateBDConfig

    @Binds
    @Singleton
    fun bindSetLocal(usecase: SetLocalImpl): SetLocal

    @Binds
    @Singleton
    fun bindSetMatricVigia(usecase: SetMatricVigiaImpl): SetMatricVigia

}