package br.com.usinasantafe.pcp.module.usecases

import br.com.usinasantafe.pcp.domain.usecases.residencia.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ResidenciaModule {

    @Binds
    @Singleton
    fun bindCheckDataSendMovEquipResidencia (usecase: CheckDataSendMovEquipResidenciaImpl): CheckDataSendMovEquipResidencia

    @Binds
    @Singleton
    fun bindReceiverSentDataMovEquipResidencia (usecase: ReceiverSentDataMovEquipResidenciaImpl): ReceiverSentDataMovEquipResidencia

    @Binds
    @Singleton
    fun bindRecoverDetalheMovEquipResidencia (usecase: RecoverDetalheMovEquipResidenciaImpl): RecoverDetalheMovEquipResidencia

    @Binds
    @Singleton
    fun bindRecoverListMovEquipResidenciaOpen (usecase: RecoverListMovEquipResidenciaInsideImpl): RecoverListMovEquipResidenciaInside

    @Binds
    @Singleton
    fun bindRecoverListMovEquipResidenciaStarted (usecase: RecoverListMovEquipResidenciaOpenImpl): RecoverListMovEquipResidenciaOpen

    @Binds
    @Singleton
    fun bindSaveMovEquipResidencia (usecase: SaveMovEquipResidenciaImpl): SaveMovEquipResidencia

    @Binds
    @Singleton
    fun bindSendDataMovEquipResidencia (usecase: SendDataMovEquipResidenciaImpl): SendDataMovEquipResidencia

    @Binds
    @Singleton
    fun bindSetMotoristaResidencia (usecase: SetMotoristaResidenciaImpl): SetMotoristaResidencia

    @Binds
    @Singleton
    fun bindSetObservResidencia (usecase: SetObservResidenciaImpl): SetObservResidencia

    @Binds
    @Singleton
    fun bindSetPlacaResidencia (usecase: SetPlacaResidenciaImpl): SetPlacaResidencia

    @Binds
    @Singleton
    fun bindSetStatusSendCloseAllMovResidencia (usecase: CloseSendAllMovResidenciaImpl): CloseSendAllMovResidencia

    @Binds
    @Singleton
    fun bindSetStatusSendCloseMovResidencia (usecase: CloseSendMovResidenciaImpl): CloseSendMovResidencia

    @Binds
    @Singleton
    fun bindSetVeiculoResidencia (usecase: SetVeiculoResidenciaImpl): SetVeiculoResidencia

    @Binds
    @Singleton
    fun bindStartMovEquipResidencia (usecase: StartMovEquipResidenciaImpl): StartMovEquipResidencia

}