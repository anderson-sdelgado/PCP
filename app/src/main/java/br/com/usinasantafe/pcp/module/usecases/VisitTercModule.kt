package br.com.usinasantafe.pcp.module.usecases

import br.com.usinasantafe.pcp.domain.usecases.visitterc.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VisitTercModule {

    @Binds
    @Singleton
    fun bindCheckCPFVisitTerc(usecase: CheckCPFVisitTercImpl): CheckCPFVisitTerc

    @Binds
    @Singleton
    fun bindCheckDataSendMovEquipVisitTerc(usecase: CheckDataSendMovEquipVisitTercImpl): CheckDataSendMovEquipVisitTerc

    @Binds
    @Singleton
    fun bindDeletePassagVisitTerc(usecase: DeletePassagVisitTercImpl): DeletePassagVisitTerc

    @Binds
    @Singleton
    fun bindReceiverSentDataMovEquipVisitTerc(usecase: ReceiverSentDataMovEquipVisitTercImpl): ReceiverSentDataMovEquipVisitTerc

    @Binds
    @Singleton
    fun bindRecoverDataVisitTerc(usecase: RecoverDataVisitTercImpl): RecoverDataVisitTerc

    @Binds
    @Singleton
    fun bindRecoverDetalheMovEquipVisitTerc(usecase: RecoverDetalheMovEquipVisitTercImpl): RecoverDetalheMovEquipVisitTerc

    @Binds
    @Singleton
    fun bindRecoverListMovEquipVisitTercOpen(usecase: RecoverListMovEquipVisitTercInsideImpl): RecoverListMovEquipVisitTercInside

    @Binds
    @Singleton
    fun bindRecoverListMovEquipVisitTercStarted(usecase: RecoverListMovEquipVisitTercOpenImpl): RecoverListMovEquipVisitTercOpen

    @Binds
    @Singleton
    fun bindRecoverListPassagVisitTerc(usecase: RecoverListPassagVisitTercImpl): RecoverListPassagVisitTerc

    @Binds
    @Singleton
    fun bindSaveMovEquipVisitTerc(usecase: SaveMovEquipVisitTercImpl): SaveMovEquipVisitTerc

    @Binds
    @Singleton
    fun bindSendDataMovEquipVisitTerc(usecase: SendDataMovEquipVisitTercImpl): SendDataMovEquipVisitTerc

    @Binds
    @Singleton
    fun bindSetDestinoVisitTerc(usecase: SetDestinoVisitTercImpl): SetDestinoVisitTerc

    @Binds
    @Singleton
    fun bindSetMotoristaPassagVisitTerc(usecase: SetPassagVisitTercImpl): SetPassagVisitTerc

    @Binds
    @Singleton
    fun bindSetObservVisitTerc(usecase: SetObservVisitTercImpl): SetObservVisitTerc

    @Binds
    @Singleton
    fun bindSetPlacaVisitTerc(usecase: SetPlacaVisitTercImpl): SetPlacaVisitTerc

    @Binds
    @Singleton
    fun bindSetStatusSendCloseAllMovVisitTerc(usecase: CloseSendAllMovVisitTercImpl): CloseSendAllMovVisitTerc

    @Binds
    @Singleton
    fun bindSetStatusSendCloseMovVisitTerc(usecase: CloseSendMovVisitTercImpl): CloseSendMovVisitTerc

    @Binds
    @Singleton
    fun bindSetTipoVisitTerc(usecase: SetTipoVisitTercImpl): SetTipoVisitTerc

    @Binds
    @Singleton
    fun bindSetVeiculoVisitTerc(usecase: SetVeiculoVisitTercImpl): SetVeiculoVisitTerc

    @Binds
    @Singleton
    fun bindStartMovEquipVisitTerc(usecase: StartMovEquipVisitTercImpl): StartMovEquipVisitTerc

}