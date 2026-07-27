package br.com.usinasantafe.pcp.di.usecase

import br.com.usinasantafe.pcp.domain.usecases.veiculoResidencia.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VeiculoResidenciaModule {

    @Binds
    @Singleton
    fun bindCheckSendMovResidencia(usecase: ICheckSendMovResidencia): CheckSendMovResidencia

    @Binds
    @Singleton
    fun bindCloseAllMovResidencia(usecase: ICloseAllMovResidencia): CloseAllMovResidencia

    @Binds
    @Singleton
    fun bindCloseMovResidencia(usecase: ICloseMovResidencia): CloseMovResidencia

    @Binds
    @Singleton
    fun bindGetDetalheResidencia(usecase: IGetDetalheResidencia): GetDetalheResidencia

    @Binds
    @Singleton
    fun bindGetMotoristaResidencia(usecase: IGetMotoristaResidencia): GetMotoristaResidencia

    @Binds
    @Singleton
    fun bindGetMovEquipResidenciaInsideList(usecase: IGetMovEquipResidenciaInsideList): GetMovEquipResidenciaInsideList

    @Binds
    @Singleton
    fun bindGetMovEquipResidenciaOpenList(usecase: IGetMovEquipResidenciaOpenList): GetMovEquipResidenciaOpenList

    @Binds
    @Singleton
    fun bindGetObservResidencia(usecase: IGetObservResidencia): GetObservResidencia

    @Binds
    @Singleton
    fun bindGetPlacaResidencia(usecase: IGetPlacaResidencia): GetPlacaResidencia

    @Binds
    @Singleton
    fun bindGetVeiculoResidencia(usecase: IGetVeiculoResidencia): GetVeiculoResidencia

    @Binds
    @Singleton
    fun bindSaveMovEquipResidencia(usecase: ISaveMovEquipResidencia): SaveMovEquipResidencia

    @Binds
    @Singleton
    fun bindSendMovResidenciaList(usecase: ISendMovResidenciaList): SendMovResidenciaList

    @Binds
    @Singleton
    fun bindSetMotoristaResidencia(usecase: ISetMotoristaResidencia): SetMotoristaResidencia

    @Binds
    @Singleton
    fun bindSetObservResidencia(usecase: ISetObservResidencia): SetObservResidencia

    @Binds
    @Singleton
    fun bindSetPlacaResidencia(usecase: ISetPlacaResidencia): SetPlacaResidencia

    @Binds
    @Singleton
    fun bindSetStatusSentMovResidencia(usecase: ISetStatusSentMovResidencia): SetStatusSentMovResidencia

    @Binds
    @Singleton
    fun bindSetVeiculoResidencia(usecase: ISetVeiculoResidencia): SetVeiculoResidencia

    @Binds
    @Singleton
    fun bindStartInputMovEquipResidencia(usecase: IStartInputMovEquipResidencia): StartInputMovEquipResidencia

    @Binds
    @Singleton
    fun bindStartOutputMovEquipResidencia(usecase: IStartOutputMovEquipResidencia): StartOutputMovEquipResidencia

}
