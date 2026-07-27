package br.com.usinasantafe.pcp.di.usecase

import br.com.usinasantafe.pcp.domain.usecases.common.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CommonModule {

    @Binds
    @Singleton
    fun bindCheckMatricColab(usecase: ICheckMatricColab): CheckMatricColab

    @Binds
    @Singleton
    fun bindCheckNroEquip(usecase: ICheckNroEquip): CheckNroEquip

    @Binds
    @Singleton
    fun bindCloseAllMov(usecase: ICloseAllMov): CloseAllMov

    @Binds
    @Singleton
    fun bindGetHeader(usecase: IGetHeader): GetHeader

    @Binds
    @Singleton
    fun bindGetNomeColab(usecase: IGetNomeColab): GetNomeColab

    @Binds
    @Singleton
    fun bindGetStatusSend(usecase: IGetStatusSend): GetStatusSend

    @Binds
    @Singleton
    fun bindGetToken(usecase: IGetToken): GetToken

    @Binds
    @Singleton
    fun bindSetStatusSend(usecase: ISetStatusSend): SetStatusSend

}