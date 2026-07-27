package br.com.usinasantafe.pcp.di.usecase

import br.com.usinasantafe.pcp.domain.usecases.initial.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface InitialModule {

    @Binds
    @Singleton
    fun bindAdjustConfig(usecase: IAdjustConfig): AdjustConfig

    @Binds
    @Singleton
    fun bindCheckMovOpen(usecase: ICheckMovOpen): CheckMovOpen

    @Binds
    @Singleton
    fun bindDeleteMovSent(usecase: IDeleteMovSent): DeleteMovSent

    @Binds
    @Singleton
    fun bindGetFlowList(usecase: IGetFlowList): GetFlowList

    @Binds
    @Singleton
    fun bindGetLocalList(usecase: IGetLocalList): GetLocalList

    @Binds
    @Singleton
    fun bindGetNomeVigia(usecase: IGetNomeVigia): GetNomeVigia

}
