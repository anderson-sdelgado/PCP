package br.com.usinasantafe.pcp.module.usecases

import br.com.usinasantafe.pcp.domain.usecases.proprio.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProprioModule {

    @Binds
    @Singleton
    fun bindCheckDataSendEquipProprio(usecase: CheckDataSendMovEquipProprioImpl): CheckDataSendMovEquipProprio

    @Binds
    @Singleton
    fun bindCheckNroEquip(usecase: CheckNroEquipImpl): CheckNroEquip

    @Binds
    @Singleton
    fun bindClearEquipSeg(usecase: ClearEquipSegImpl): ClearEquipSeg

    @Binds
    @Singleton
    fun bindCloseSendMovProprio(usecase: CloseSendMovProprioImpl): CloseSendMovProprio

    @Binds
    @Singleton
    fun bindCloseSendAllMovProprio(usecase: CloseSendAllMovProprioImpl): CloseSendAllMovProprio

    @Binds
    @Singleton
    fun bindDeleteColabPassag(usecase: DeletePassagColabImpl): DeletePassagColab

    @Binds
    @Singleton
    fun bindDeleteEquipProprioSeg(usecase: DeleteEquipSegImpl): DeleteEquipSeg

    @Binds
    @Singleton
    fun bindGetTipoMov(usecase: GetTipoMovImpl): GetTipoMov

    @Binds
    @Singleton
    fun bindReceiverSentDataMovEquipProprio(usecase: ReceiverSentDataMovEquipProprioImpl): ReceiverSentDataMovEquipProprio

    @Binds
    @Singleton
    fun bindRecoverDetalheMovEquipProprio(usecase: RecoverDetalheMovEquipProprioImpl): RecoverDetalheMovEquipProprio


    @Binds
    @Singleton
    fun bindRecoverListEquipProprioSeg(usecase: RecoverListEquipProprioSegImpl): RecoverListEquipProprioSeg

    @Binds
    @Singleton
    fun bindRecoverListMovEquipProprioOpen(usecase: RecoverListMovEquipProprioOpenImpl): RecoverListMovEquipProprioOpen

    @Binds
    @Singleton
    fun bindRecoverListPassag(usecase: RecoverListColabPassagImpl): RecoverListColabPassag

    @Binds
    @Singleton
    fun bindRecoverNomeColabMotoristaPassag(usecase: RecoverNomeColabImpl): RecoverNomeColab

    @Binds
    @Singleton
    fun bindSaveMovEquipProprio(usecase: SaveMovEquipProprioOpenImpl): SaveMovEquipProprioOpen

    @Binds
    @Singleton
    fun bindSendDataMovEquipProprio(usecase: SendDataMovEquipProprioImpl): SendDataMovEquipProprio

    @Binds
    @Singleton
    fun bindSetDestinoProprio(usecase: SetDestinoProprioImpl): SetDestinoProprio

    @Binds
    @Singleton
    fun bindSetMatricMotoristaPassag(usecase: SetMatricMotoristaPassagImpl): SetMatricMotoristaPassag

    @Binds
    @Singleton
    fun bindSetNotaFiscalProprio(usecase: SetNotaFiscalProprioImpl): SetNotaFiscalProprio

    @Binds
    @Singleton
    fun bindSetNroVeiculoEquipSeg(usecase: SetNroEquipImpl): SetNroEquip

    @Binds
    @Singleton
    fun bindSetObservProprio(usecase: SetObservProprioImpl): SetObservProprio

    @Binds
    @Singleton
    fun bindStartMovEquipProprio(usecase: StartMovEquipProprioImpl): StartMovEquipProprio

}