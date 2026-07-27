package br.com.usinasantafe.pcp.di.usecase

import br.com.usinasantafe.pcp.domain.usecases.chave.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ChaveModule {

    @Binds
    @Singleton
    fun bindCheckSendMovChave(usecase: ICheckSendMovChave): CheckSendMovChave

    @Binds
    @Singleton
    fun bindCloseAllMovChave(usecase: ICloseAllMovChave): CloseAllMovChave

    @Binds
    @Singleton
    fun bindCloseMovChave(usecase: ICloseMovChave): CloseMovChave

    @Binds
    @Singleton
    fun bindGetChaveList(usecase: IGetChaveList): GetChaveList

    @Binds
    @Singleton
    fun bindGetDescrFullChave(usecase: IGetDescrFullChave): GetDescrFullChave

    @Binds
    @Singleton
    fun bindGetDetalheMovChave(usecase: IGetDetalheMovChave): GetDetalheMovChave

    @Binds
    @Singleton
    fun bindGetMatricColabMovChave(usecase: IGetMatricColabMovChave): GetMatricColabMovChave

    @Binds
    @Singleton
    fun bindGetMovChaveOpenList(usecase: IGetMovChaveOpenList): GetMovChaveOpenList

    @Binds
    @Singleton
    fun bindGetMovChaveInsideList(usecase: IGetMovChaveInsideList): GetMovChaveInsideList

    @Binds
    @Singleton
    fun bindGetObservMovChave(usecase: IGetObservMovChave): GetObservMovChave

    @Binds
    @Singleton
    fun bindSaveMovChave(usecase: ISaveMovChave): SaveMovChave

    @Binds
    @Singleton
    fun bindSendMovChaveList(usecase: ISendMovChaveList): SendMovChaveList

    @Binds
    @Singleton
    fun bindSetMatricColabMovChave(usecase: ISetMatricColabMovChave): SetMatricColabMovChave

    @Binds
    @Singleton
    fun bindSetIdChaveMovChave(usecase: ISetIdChaveMovChave): SetIdChaveMovChave

    @Binds
    @Singleton
    fun bindSetObservMovChave(usecase: ISetObservMovChave): SetObservMovChave

    @Binds
    @Singleton
    fun bindSetStatusSentMovChave(usecase: ISetStatusSentMovChave): SetStatusSentMovChave

    @Binds
    @Singleton
    fun bindStartReceiptMovChave(usecase: IStartReceiptMovChave): StartReceiptMovChave

    @Binds
    @Singleton
    fun bindStartRemoveMovChave(usecase: IStartRemoveMovChave): StartRemoveMovChave

}