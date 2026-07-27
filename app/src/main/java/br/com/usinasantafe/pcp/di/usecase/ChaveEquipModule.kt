package br.com.usinasantafe.pcp.di.usecase

import br.com.usinasantafe.pcp.domain.usecases.chaveEquip.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ChaveEquipModule {

    @Binds
    @Singleton
    fun bindCheckSendMovChaveEquip(usecase: ICheckSendMovChaveEquip): CheckSendMovChaveEquip

    @Binds
    @Singleton
    fun bindCloseAllMovChaveEquip(usecase: ICloseAllMovChaveEquip): CloseAllMovChaveEquip

    @Binds
    @Singleton
    fun bindCloseMovChaveEquip(usecase: ICloseMovChaveEquip): CloseMovChaveEquip

    @Binds
    @Singleton
    fun bindGetDetalheMovChaveEquip(usecase: IGetDetalheMovChaveEquip): GetDetalheMovChaveEquip

    @Binds
    @Singleton
    fun bindGetMatricColabMovChaveEquip(usecase: IGetMatricColabMovChaveEquip): GetMatricColabMovChaveEquip

    @Binds
    @Singleton
    fun bindGetMovChaveEquipInsideList(usecase: IGetMovChaveEquipInsideList): GetMovChaveEquipInsideList

    @Binds
    @Singleton
    fun bindGetMovChaveEquipOpenList(usecase: IGetMovChaveEquipOpenList): GetMovChaveEquipOpenList

    @Binds
    @Singleton
    fun bindGetNroEquipMovChaveEquip(usecase: IGetNroEquipMovChaveEquip): GetNroEquipMovChaveEquip

    @Binds
    @Singleton
    fun bindGetObservMovChaveEquip(usecase: IGetObservMovChaveEquip): GetObservMovChaveEquip

    @Binds
    @Singleton
    fun bindSaveMovChaveEquip(usecase: ISaveMovChaveEquip): SaveMovChaveEquip

    @Binds
    @Singleton
    fun bindSendMovChaveEquipList(usecase: ISendMovChaveEquipList): SendMovChaveEquipList

    @Binds
    @Singleton
    fun bindSetIdEquipMovChaveEquip(usecase: ISetIdEquipMovChaveEquip): SetIdEquipMovChaveEquip

    @Binds
    @Singleton
    fun bindSetMatricColabMovChaveEquip(usecase: ISetMatricColabMovChaveEquip): SetMatricColabMovChaveEquip

    @Binds
    @Singleton
    fun bindSetObservMovChaveEquip(usecase: ISetObservMovChaveEquip): SetObservMovChaveEquip

    @Binds
    @Singleton
    fun bindSetStatusSentMovChaveEquip(usecase: ISetStatusSentMovChaveEquip): SetStatusSentMovChaveEquip

    @Binds
    @Singleton
    fun bindStartReceiptMovChaveEquip(usecase: IStartReceiptMovChaveEquip): StartReceiptMovChaveEquip

    @Binds
    @Singleton
    fun bindStartRemoveMovChaveEquip(usecase: IStartRemoveMovChaveEquip): StartRemoveMovChaveEquip

}
