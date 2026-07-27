package br.com.usinasantafe.pcp.di.usecase

import br.com.usinasantafe.pcp.domain.usecases.veiculoVisitTerc.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VeiculoVisitTercModule {

    @Binds
    @Singleton
    fun bindCheckCpfVisitTerc(usecase: ICheckCpfVisitTerc): CheckCpfVisitTerc

    @Binds
    @Singleton
    fun bindCheckSendMovVisitTerc(usecase: ICheckSendMovVisitTerc): CheckSendMovVisitTerc

    @Binds
    @Singleton
    fun bindCleanPassagVisitTerc(usecase: ICleanPassagVisitTerc): CleanPassagVisitTerc

    @Binds
    @Singleton
    fun bindCloseAllMovVisitTerc(usecase: ICloseAllMovVisitTerc): CloseAllMovVisitTerc

    @Binds
    @Singleton
    fun bindCloseMovVisitTerc(usecase: ICloseMovVisitTerc): CloseMovVisitTerc

    @Binds
    @Singleton
    fun bindDeletePassagVisitTerc(usecase: IDeletePassagVisitTerc): DeletePassagVisitTerc

    @Binds
    @Singleton
    fun bindGetCpfVisitTerc(usecase: IGetCpfVisitTerc): GetCpfVisitTerc

    @Binds
    @Singleton
    fun bindGetDestinoVisitTerc(usecase: IGetDestinoVisitTerc): GetDestinoVisitTerc

    @Binds
    @Singleton
    fun bindGetDetalheVisitTerc(usecase: IGetDetalheVisitTerc): GetDetalheVisitTerc

    @Binds
    @Singleton
    fun bindGetMotoristaVisitTerc(usecase: IGetMotoristaVisitTerc): GetMotoristaVisitTerc

    @Binds
    @Singleton
    fun bindGetMovEquipVisitTercInsideList(usecase: IGetMovEquipVisitTercInsideList): GetMovEquipVisitTercInsideList

    @Binds
    @Singleton
    fun bindGetMovEquipVisitTercOpenList(usecase: IGetMovEquipVisitTercOpenList): GetMovEquipVisitTercOpenList

    @Binds
    @Singleton
    fun bindGetNomeVisitTerc(usecase: IGetNomeVisitTerc): GetNomeVisitTerc

    @Binds
    @Singleton
    fun bindGetObservVisitTerc(usecase: IGetObservVisitTerc): GetObservVisitTerc

    @Binds
    @Singleton
    fun bindGetPassagVisitTercList(usecase: IGetPassagVisitTercList): GetPassagVisitTercList

    @Binds
    @Singleton
    fun bindGetPlacaVisitTerc(usecase: IGetPlacaVisitTerc): GetPlacaVisitTerc

    @Binds
    @Singleton
    fun bindGetTitleCpfVisitTerc(usecase: IGetTitleCpfVisitTerc): GetTitleCpfVisitTerc

    @Binds
    @Singleton
    fun bindGetVeiculoVisitTerc(usecase: IGetVeiculoVisitTerc): GetVeiculoVisitTerc

    @Binds
    @Singleton
    fun bindSaveMovEquipVisitTerc(usecase: ISaveMovEquipVisitTerc): SaveMovEquipVisitTerc

    @Binds
    @Singleton
    fun bindSendMovVisitTercList(usecase: ISendMovVisitTercList): SendMovVisitTercList

    @Binds
    @Singleton
    fun bindSetDestinoVisitTerc(usecase: ISetDestinoVisitTerc): SetDestinoVisitTerc

    @Binds
    @Singleton
    fun bindSetIdVisitTerc(usecase: ISetIdVisitTerc): SetIdVisitTerc

    @Binds
    @Singleton
    fun bindSetObservVisitTerc(usecase: ISetObservVisitTerc): SetObservVisitTerc

    @Binds
    @Singleton
    fun bindSetPlacaVisitTerc(usecase: ISetPlacaVisitTerc): SetPlacaVisitTerc

    @Binds
    @Singleton
    fun bindSetStatusSentMovVisitTerc(usecase: ISetStatusSentMovVisitTerc): SetStatusSentMovVisitTerc

    @Binds
    @Singleton
    fun bindSetTipoVisitTerc(usecase: ISetTipoVisitTerc): SetTipoVisitTerc

    @Binds
    @Singleton
    fun bindSetVeiculoVisitTerc(usecase: ISetVeiculoVisitTerc): SetVeiculoVisitTerc

    @Binds
    @Singleton
    fun bindStartInputMovEquipVisitTerc(usecase: IStartInputMovEquipVisitTerc): StartInputMovEquipVisitTerc

    @Binds
    @Singleton
    fun bindStartOutputMovEquipVisitTerc(usecase: IStartOutputMovEquipVisitTerc): StartOutputMovEquipVisitTerc

}
