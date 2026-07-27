package br.com.usinasantafe.pcp.di.usecase

import br.com.usinasantafe.pcp.domain.usecases.veiculoProprio.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VeiculoProprioModule {

    @Binds
    @Singleton
    fun bindCheckSendMovProprio(usecase: ICheckSendMovProprio): CheckSendMovProprio

    @Binds
    @Singleton
    fun bindCleanEquipSeg(usecase: ICleanEquipSeg): CleanEquipSeg

    @Binds
    @Singleton
    fun bindCleanPassagColab(usecase: ICleanPassagColab): CleanPassagColab

    @Binds
    @Singleton
    fun bindCloseAllMovProprio(usecase: ICloseAllMovProprio): CloseAllMovProprio

    @Binds
    @Singleton
    fun bindCloseMovProprio(usecase: ICloseMovProprio): CloseMovProprio

    @Binds
    @Singleton
    fun bindDeleteEquipSeg(usecase: IDeleteEquipSeg): DeleteEquipSeg

    @Binds
    @Singleton
    fun bindDeletePassagColab(usecase: IDeletePassagColab): DeletePassagColab

    @Binds
    @Singleton
    fun bindGetDestinoProprio(usecase: IGetDestinoProprio): GetDestinoProprio

    @Binds
    @Singleton
    fun bindGetDetalheProprio(usecase: IGetDetalheProprio): GetDetalheProprio

    @Binds
    @Singleton
    fun bindGetEquipSegList(usecase: IGetEquipSegList): GetEquipSegList

    @Binds
    @Singleton
    fun bindGetMatricColab(usecase: IGetMatricColab): GetMatricColab

    @Binds
    @Singleton
    fun bindGetMovEquipProprioOpenList(usecase: IGetMovEquipProprioOpenList): GetMovEquipProprioOpenList

    @Binds
    @Singleton
    fun bindGetNotaFiscalProprio(usecase: IGetNotaFiscalProprio): GetNotaFiscalProprio

    @Binds
    @Singleton
    fun bindGetNroEquipProprio(usecase: IGetNroEquipProprio): GetNroEquipProprio

    @Binds
    @Singleton
    fun bindGetObservProprio(usecase: IGetObservProprio): GetObservProprio

    @Binds
    @Singleton
    fun bindGetPassagColabList(usecase: IGetPassagColabList): GetPassagColabList

    @Binds
    @Singleton
    fun bindGetTypeMov(usecase: IGetTypeMov): GetTypeMov

    @Binds
    @Singleton
    fun bindSaveMovEquipProprio(usecase: ISaveMovEquipProprio): SaveMovEquipProprio

    @Binds
    @Singleton
    fun bindSendMovProprioList(usecase: ISendMovProprioList): SendMovProprioList

    @Binds
    @Singleton
    fun bindSetDestinoProprio(usecase: ISetDestinoProprio): SetDestinoProprio

    @Binds
    @Singleton
    fun bindSetMatricColab(usecase: ISetMatricColab): SetMatricColab

    @Binds
    @Singleton
    fun bindSetNotaFiscalProprio(usecase: ISetNotaFiscalProprio): SetNotaFiscalProprio

    @Binds
    @Singleton
    fun bindSetIdEquipProprio(usecase: ISetIdEquipProprio): SetIdEquipProprio

    @Binds
    @Singleton
    fun bindSetObservProprio(usecase: ISetObservProprio): SetObservProprio

    @Binds
    @Singleton
    fun bindSetStatusSentMovProprio(usecase: ISetStatusSentMovProprio): SetStatusSentMovProprio

    @Binds
    @Singleton
    fun bindStartMovEquipProprio(usecase: IStartMovEquipProprio): StartMovEquipProprio

}
