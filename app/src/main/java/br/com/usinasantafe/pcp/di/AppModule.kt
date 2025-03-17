package br.com.usinasantafe.pcp.di

import br.com.usinasantafe.pcp.presenter.chave.chavelist.ChaveListViewModel
import br.com.usinasantafe.pcp.presenter.chave.controlelist.ControleChaveListViewModel
import br.com.usinasantafe.pcp.presenter.chave.matriccolab.MatricColabChaveViewModel
import br.com.usinasantafe.pcp.presenter.chave.nomecolab.NomeColabChaveViewModel
import br.com.usinasantafe.pcp.presenter.chave.observ.ObservChaveViewModel
import br.com.usinasantafe.pcp.presenter.chave.controleeditlist.ControleChaveEditListViewModel
import br.com.usinasantafe.pcp.presenter.chave.detalhe.DetalheChaveViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.controleeditlist.ControleChaveEquipEditListViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.controlelist.ControleChaveEquipListViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.detalhe.DetalheChaveEquipViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.matriccolab.MatricColabChaveEquipViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.nomecolab.NomeColabChaveEquipViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.nroequip.NroEquipChaveEquipViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.observ.ObservChaveEquipViewModel
import br.com.usinasantafe.pcp.presenter.configuration.senha.SenhaViewModel
import br.com.usinasantafe.pcp.presenter.configuration.config.ConfigViewModel
import br.com.usinasantafe.pcp.presenter.configuration.menuinicial.MenuInicialViewModel
import br.com.usinasantafe.pcp.presenter.initial.matricvigia.MatricVigiaViewModel
import br.com.usinasantafe.pcp.presenter.initial.nomevigia.NomeVigiaViewModel
import br.com.usinasantafe.pcp.presenter.initial.local.LocalViewModel
import br.com.usinasantafe.pcp.presenter.initial.menuapont.MenuApontViewModel
import br.com.usinasantafe.pcp.presenter.proprio.movlist.MovEquipProprioListViewModel
import br.com.usinasantafe.pcp.presenter.proprio.matriccolab.MatricColabViewModel
import br.com.usinasantafe.pcp.presenter.proprio.nomecolab.NomeColabViewModel
import br.com.usinasantafe.pcp.presenter.proprio.passaglist.PassagColabListViewModel
import br.com.usinasantafe.pcp.presenter.proprio.nroequip.NroEquipProprioViewModel
import br.com.usinasantafe.pcp.presenter.proprio.equipseglist.EquipSegListViewModel
import br.com.usinasantafe.pcp.presenter.proprio.destino.DestinoProprioViewModel
import br.com.usinasantafe.pcp.presenter.proprio.notafiscal.NotaFiscalViewModel
import br.com.usinasantafe.pcp.presenter.proprio.observ.ObservProprioViewModel
import br.com.usinasantafe.pcp.presenter.proprio.detalhe.DetalheProprioViewModel
import br.com.usinasantafe.pcp.presenter.residencia.detalhe.DetalheResidenciaViewModel
import br.com.usinasantafe.pcp.presenter.residencia.motorista.MotoristaResidenciaViewModel
import br.com.usinasantafe.pcp.presenter.residencia.moveditlist.MovEquipResidenciaEditListViewModel
import br.com.usinasantafe.pcp.presenter.residencia.movlist.MovEquipResidenciaListViewModel
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaViewModel
import br.com.usinasantafe.pcp.presenter.residencia.placa.PlacaResidenciaViewModel
import br.com.usinasantafe.pcp.presenter.residencia.veiculo.VeiculoResidenciaViewModel
import br.com.usinasantafe.pcp.presenter.splash.SplashViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CpfVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.destino.DestinoVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.detalhe.DetalheVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.moveditlist.MovEquipVisitTercEditListViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.movlist.MovEquipVisitTercListViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.nome.NomeVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.observ.ObservVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.passaglist.PassagVisitTercListViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.placa.PlacaVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.tipo.TipoVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.veiculo.VeiculoVisitTercViewModel
import br.com.usinasantafe.pcp.domain.usecases.background.*
import br.com.usinasantafe.pcp.domain.usecases.chave.*
import br.com.usinasantafe.pcp.domain.usecases.chaveequip.*
import br.com.usinasantafe.pcp.domain.usecases.config.*
import br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.*
import br.com.usinasantafe.pcp.domain.usecases.updatetable.savetable.*
import br.com.usinasantafe.pcp.domain.usecases.common.*
import br.com.usinasantafe.pcp.domain.usecases.updatetable.getserver.*
import br.com.usinasantafe.pcp.domain.usecases.initial.*
import br.com.usinasantafe.pcp.domain.usecases.proprio.*
import br.com.usinasantafe.pcp.domain.usecases.visitterc.*
import br.com.usinasantafe.pcp.domain.usecases.residencia.*
import br.com.usinasantafe.pcp.domain.repositories.stable.*
import br.com.usinasantafe.pcp.domain.repositories.variable.*
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateChave
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateColab
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateEquip
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateFluxo
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateLocal
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateLocalTrab
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateRLocalFluxo
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateTerceiro
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.IUpdateVisitante
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateChave
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateColab
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateEquip
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateFluxo
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateLocal
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateLocalTrab
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateRLocalFluxo
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateTerceiro
import br.com.usinasantafe.pcp.domain.usecases.updatetable.update.UpdateVisitante
import br.com.usinasantafe.pcp.infra.repositories.variable.*
import br.com.usinasantafe.pcp.infra.repositories.stable.*
import br.com.usinasantafe.pcp.infra.datasource.room.stable.*
import br.com.usinasantafe.pcp.infra.datasource.room.variable.*
import br.com.usinasantafe.pcp.infra.datasource.sharepreferences.*
import br.com.usinasantafe.pcp.infra.datasource.retrofit.stable.*
import br.com.usinasantafe.pcp.infra.datasource.retrofit.variable.*
import br.com.usinasantafe.pcp.external.sharedpreferences.datasource.*
import br.com.usinasantafe.pcp.external.room.datasource.stable.*
import br.com.usinasantafe.pcp.external.room.datasource.variable.*
import br.com.usinasantafe.pcp.external.retrofit.datasource.variable.*
import br.com.usinasantafe.pcp.external.retrofit.datasource.stable.*
import br.com.usinasantafe.pcp.external.retrofit.api.variable.*
import br.com.usinasantafe.pcp.external.retrofit.api.stable.*
import br.com.usinasantafe.pcp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcp.external.sharedpreferences.providerSharedPreferences
import br.com.usinasantafe.pcp.external.retrofit.provideRetrofit
import br.com.usinasantafe.pcp.external.room.provideRoom
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val viewModelConfigModule = module {
    viewModelOf(::MenuInicialViewModel)
    viewModelOf(::SenhaViewModel)
    viewModelOf(::ConfigViewModel)
}

val viewModelInicialModule = module {
    viewModelOf(::MatricVigiaViewModel)
    viewModelOf(::NomeVigiaViewModel)
    viewModelOf(::LocalViewModel)
    viewModelOf(::MenuApontViewModel)
}

val viewModelSplashModule = module {
    viewModelOf(::SplashViewModel)
}

val viewModelChaveModule = module {
    viewModelOf(::ChaveListViewModel)
    viewModelOf(::ControleChaveListViewModel)
    viewModelOf(::ControleChaveEditListViewModel)
    viewModelOf(::DetalheChaveViewModel)
    viewModelOf(::MatricColabChaveViewModel)
    viewModelOf(::NomeColabChaveViewModel)
    viewModelOf(::ObservChaveViewModel)
}

val viewModelChaveEquipModule = module {
    viewModelOf(::ControleChaveEquipListViewModel)
    viewModelOf(::ControleChaveEquipEditListViewModel)
    viewModelOf(::DetalheChaveEquipViewModel)
    viewModelOf(::MatricColabChaveEquipViewModel)
    viewModelOf(::NomeColabChaveEquipViewModel)
    viewModelOf(::NroEquipChaveEquipViewModel)
    viewModelOf(::ObservChaveEquipViewModel)
}

val viewModelProprioModule = module {
    viewModelOf(::DestinoProprioViewModel)
    viewModelOf(::DetalheProprioViewModel)
    viewModelOf(::EquipSegListViewModel)
    viewModelOf(::MatricColabViewModel)
    viewModelOf(::MovEquipProprioListViewModel)
    viewModelOf(::NomeColabViewModel)
    viewModelOf(::NotaFiscalViewModel)
    viewModelOf(::NroEquipProprioViewModel)
    viewModelOf(::ObservProprioViewModel)
    viewModelOf(::PassagColabListViewModel)
}

val viewModelResidenciaModule = module {
    viewModelOf(::DetalheResidenciaViewModel)
    viewModelOf(::MotoristaResidenciaViewModel)
    viewModelOf(::MovEquipResidenciaEditListViewModel)
    viewModelOf(::MovEquipResidenciaListViewModel)
    viewModelOf(::ObservResidenciaViewModel)
    viewModelOf(::PlacaResidenciaViewModel)
    viewModelOf(::VeiculoResidenciaViewModel)
}

val viewModelVisitTercModule = module {
    viewModelOf(::CpfVisitTercViewModel)
    viewModelOf(::DestinoVisitTercViewModel)
    viewModelOf(::DetalheVisitTercViewModel)
    viewModelOf(::MovEquipVisitTercEditListViewModel)
    viewModelOf(::MovEquipVisitTercListViewModel)
    viewModelOf(::NomeVisitTercViewModel)
    viewModelOf(::ObservVisitTercViewModel)
    viewModelOf(::PassagVisitTercListViewModel)
    viewModelOf(::PlacaVisitTercViewModel)
    viewModelOf(::TipoVisitTercViewModel)
    viewModelOf(::VeiculoVisitTercViewModel)
}

val usecaseBackgroundModule = module {
    singleOf(::IStartProcessSendData) { bind<StartProcessSendData>() }
}

val usecaseChaveModule = module {
    singleOf(::ICheckSendMovChave) { bind<CheckSendMovChave>() }
    singleOf(::ICloseAllMovChave) { bind<CloseAllMovChave>() }
    singleOf(::ICloseMovChave) { bind<CloseMovChave>() }
    singleOf(::IGetChaveList) { bind<GetChaveList>() }
    singleOf(::IGetDescrFullChave) { bind<GetDescrFullChave>() }
    singleOf(::IGetDetalheMovChave) { bind<GetDetalheMovChave>() }
    singleOf(::IGetMatricColabMovChave) { bind<GetMatricColabMovChave>() }
    singleOf(::IGetMovChaveOpenList) { bind<GetMovChaveOpenList>() }
    singleOf(::IGetMovChaveInsideList) { bind<GetMovChaveInsideList>() }
    singleOf(::IGetObservMovChave) { bind<GetObservMovChave>() }
    singleOf(::ISaveMovChave) { bind<SaveMovChave>() }
    singleOf(::ISendMovChaveList) { bind<SendMovChaveList>() }
    singleOf(::ISetMatricColabMovChave) { bind<SetMatricColabMovChave>() }
    singleOf(::ISetIdChaveMovChave) { bind<SetIdChaveMovChave>() }
    singleOf(::ISetObservMovChave) { bind<SetObservMovChave>() }
    singleOf(::ISetStatusSentMovChave) { bind<SetStatusSentMovChave>() }
    singleOf(::IStartReceiptMovChave) { bind<StartReceiptMovChave>() }
    singleOf(::IStartRemoveMovChave) { bind<StartRemoveMovChave>() }
}

val usecaseChaveEquipModule = module {
    singleOf(::ICheckSendMovChaveEquip) { bind<CheckSendMovChaveEquip>() }
    singleOf(::ICloseAllMovChaveEquip) { bind<CloseAllMovChaveEquip>() }
    singleOf(::ICloseMovChaveEquip) { bind<CloseMovChaveEquip>() }
    singleOf(::IGetDetalheMovChaveEquip) { bind<GetDetalheMovChaveEquip>() }
    singleOf(::IGetMatricColabMovChaveEquip) { bind<GetMatricColabMovChaveEquip>() }
    singleOf(::IGetMovChaveEquipInsideList) { bind<GetMovChaveEquipInsideList>() }
    singleOf(::IGetMovChaveEquipOpenList) { bind<GetMovChaveEquipOpenList>() }
    singleOf(::IGetNroEquipMovChaveEquip) { bind<GetNroEquipMovChaveEquip>() }
    singleOf(::IGetObservMovChaveEquip) { bind<GetObservMovChaveEquip>() }
    singleOf(::ISaveMovChaveEquip) { bind<SaveMovChaveEquip>() }
    singleOf(::ISendMovChaveEquipList) { bind<SendMovChaveEquipList>() }
    singleOf(::ISetIdEquipMovChaveEquip) { bind<SetIdEquipMovChaveEquip>() }
    singleOf(::ISetMatricColabMovChaveEquip) { bind<SetMatricColabMovChaveEquip>() }
    singleOf(::ISetObservMovChaveEquip) { bind<SetObservMovChaveEquip>() }
    singleOf(::ISetStatusSentMovChaveEquip) { bind<SetStatusSentMovChaveEquip>() }
    singleOf(::IStartReceiptMovChaveEquip) { bind<StartReceiptMovChaveEquip>() }
    singleOf(::IStartRemoveMovChaveEquip) { bind<StartRemoveMovChaveEquip>() }
}

val usecaseCommonModule = module {
    singleOf(::ICheckMatricColab) { bind<CheckMatricColab>() }
    singleOf(::ICheckNroEquip) { bind<CheckNroEquip>() }
    singleOf(::ICloseAllMov) { bind<CloseAllMov>() }
    singleOf(::IGetHeader) { bind<GetHeader>() }
    singleOf(::IGetNomeColab) { bind<GetNomeColab>() }
    singleOf(::IGetStatusSend) { bind<GetStatusSend>() }
    singleOf(::IGetToken) { bind<GetToken>() }
    singleOf(::ISetStatusSend) { bind<SetStatusSend>() }
}

val usecaseConfigModule = module {
    singleOf(::ICheckPassword) { bind<CheckPassword>() }
    singleOf(::IGetConfigInternal) { bind<GetConfigInternal>() }
    singleOf(::ISendDataConfig) { bind<SendDataConfig>() }
    singleOf(::ISaveDataConfig) { bind<SaveDataConfig>() }
    singleOf(::ISetCheckUpdateAllTable) { bind<SetCheckUpdateAllTable>() }
    singleOf(::ISetIdLocalConfig) { bind<SetIdLocalConfig>() }
    singleOf(::ISetMatricVigiaConfig) { bind<SetMatricVigiaConfig>() }
}

val usecaseInitialModule = module {
    singleOf(::IAdjustConfig) { bind<AdjustConfig>() }
    singleOf(::ICheckAccessMain) { bind<CheckAccessMain>() }
    singleOf(::ICheckMovOpen) { bind<CheckMovOpen>() }
    singleOf(::IDeleteMovSent) { bind<DeleteMovSent>() }
    singleOf(::IGetFlowList) { bind<GetFlowList>() }
    singleOf(::IGetLocalList) { bind<GetLocalList>() }
    singleOf(::IGetNomeVigia) { bind<GetNomeVigia>() }
}

val usecaseProprioModule = module {
    singleOf(::ICheckSendMovProprio) { bind<CheckSendMovProprio>() }
    singleOf(::ICleanEquipSeg) { bind<CleanEquipSeg>() }
    singleOf(::ICleanPassagColab) { bind<CleanPassagColab>() }
    singleOf(::ICloseAllMovProprio) { bind<CloseAllMovProprio>() }
    singleOf(::ICloseMovProprio) { bind<CloseMovProprio>() }
    singleOf(::IDeleteEquipSeg) { bind<DeleteEquipSeg>() }
    singleOf(::IDeletePassagColab) { bind<DeletePassagColab>() }
    singleOf(::IGetDestinoProprio) { bind<GetDestinoProprio>() }
    singleOf(::IGetDetalheProprio) { bind<GetDetalheProprio>() }
    singleOf(::IGetEquipSegList) { bind<GetEquipSegList>() }
    singleOf(::IGetMatricColab) { bind<GetMatricColab>() }
    singleOf(::IGetMovEquipProprioOpenList) { bind<GetMovEquipProprioOpenList>() }
    singleOf(::IGetNotaFiscalProprio) { bind<GetNotaFiscalProprio>() }
    singleOf(::IGetNroEquipProprio) { bind<GetNroEquipProprio>() }
    singleOf(::IGetObservProprio) { bind<GetObservProprio>() }
    singleOf(::IGetPassagColabList) { bind<GetPassagColabList>() }
    singleOf(::IGetTypeMov) { bind<GetTypeMov>() }
    singleOf(::ISaveMovEquipProprio) { bind<SaveMovEquipProprio>() }
    singleOf(::ISendMovProprioList) { bind<SendMovProprioList>() }
    singleOf(::ISetDestinoProprio) { bind<SetDestinoProprio>() }
    singleOf(::ISetMatricColab) { bind<SetMatricColab>() }
    singleOf(::ISetNotaFiscalProprio) { bind<SetNotaFiscalProprio>() }
    singleOf(::ISetIdEquipProprio) { bind<SetIdEquipProprio>() }
    singleOf(::ISetObservProprio) { bind<SetObservProprio>() }
    singleOf(::ISetStatusSentMovProprio) { bind<SetStatusSentMovProprio>() }
    singleOf(::IStartMovEquipProprio) { bind<StartMovEquipProprio>() }
}

val usecaseVisitTercModule = module {
    singleOf(::ICheckCpfVisitTerc) { bind<CheckCpfVisitTerc>() }
    singleOf(::ICheckSendMovVisitTerc) { bind<CheckSendMovVisitTerc>() }
    singleOf(::ICleanPassagVisitTerc) { bind<CleanPassagVisitTerc>() }
    singleOf(::ICloseAllMovVisitTerc) { bind<CloseAllMovVisitTerc>() }
    singleOf(::ICloseMovVisitTerc) { bind<CloseMovVisitTerc>() }
    singleOf(::IDeletePassagVisitTerc) { bind<DeletePassagVisitTerc>() }
    singleOf(::IGetCpfVisitTerc) { bind<GetCpfVisitTerc>() }
    singleOf(::IGetDestinoVisitTerc) { bind<GetDestinoVisitTerc>() }
    singleOf(::IGetDetalheVisitTerc) { bind<GetDetalheVisitTerc>() }
    singleOf(::IGetMotoristaVisitTerc) { bind<GetMotoristaVisitTerc>() }
    singleOf(::IGetMovEquipVisitTercInsideList) { bind<GetMovEquipVisitTercInsideList>() }
    singleOf(::IGetMovEquipVisitTercOpenList) { bind<GetMovEquipVisitTercOpenList>() }
    singleOf(::IGetNomeVisitTerc) { bind<GetNomeVisitTerc>() }
    singleOf(::IGetObservVisitTerc) { bind<GetObservVisitTerc>() }
    singleOf(::IGetPassagVisitTercList) { bind<GetPassagVisitTercList>() }
    singleOf(::IGetPlacaVisitTerc) { bind<GetPlacaVisitTerc>() }
    singleOf(::IGetTitleCpfVisitTerc) { bind<GetTitleCpfVisitTerc>() }
    singleOf(::IGetVeiculoVisitTerc) { bind<GetVeiculoVisitTerc>() }
    singleOf(::ISaveMovEquipVisitTerc) { bind<SaveMovEquipVisitTerc>() }
    singleOf(::ISendMovVisitTercList) { bind<SendMovVisitTercList>() }
    singleOf(::ISetDestinoVisitTerc) { bind<SetDestinoVisitTerc>() }
    singleOf(::ISetIdVisitTerc) { bind<SetIdVisitTerc>() }
    singleOf(::ISetObservVisitTerc) { bind<SetObservVisitTerc>() }
    singleOf(::ISetPlacaVisitTerc) { bind<SetPlacaVisitTerc>() }
    singleOf(::ISetStatusSentMovVisitTerc) { bind<SetStatusSentMovVisitTerc>() }
    singleOf(::ISetTipoVisitTerc) { bind<SetTipoVisitTerc>() }
    singleOf(::ISetVeiculoVisitTerc) { bind<SetVeiculoVisitTerc>() }
    singleOf(::IStartInputMovEquipVisitTerc) { bind<StartInputMovEquipVisitTerc>() }
    singleOf(::IStartOutputMovEquipVisitTerc) { bind<StartOutputMovEquipVisitTerc>() }
}

val usecaseResidenciaModule = module {
    singleOf(::ICheckSendMovResidencia) { bind<CheckSendMovResidencia>() }
    singleOf(::ICloseAllMovResidencia) { bind<CloseAllMovResidencia>() }
    singleOf(::ICloseMovResidencia) { bind<CloseMovResidencia>() }
    singleOf(::IGetDetalheResidencia) { bind<GetDetalheResidencia>() }
    singleOf(::IGetMotoristaResidencia) { bind<GetMotoristaResidencia>() }
    singleOf(::IGetMovEquipResidenciaInsideList) { bind<GetMovEquipResidenciaInsideList>() }
    singleOf(::IGetMovEquipResidenciaOpenList) { bind<GetMovEquipResidenciaOpenList>() }
    singleOf(::IGetObservResidencia) { bind<GetObservResidencia>() }
    singleOf(::IGetPlacaResidencia) { bind<GetPlacaResidencia>() }
    singleOf(::IGetVeiculoResidencia) { bind<GetVeiculoResidencia>() }
    singleOf(::ISaveMovEquipResidencia) { bind<SaveMovEquipResidencia>() }
    singleOf(::ISendMovResidenciaList) { bind<SendMovResidenciaList>() }
    singleOf(::ISetMotoristaResidencia) { bind<SetMotoristaResidencia>() }
    singleOf(::ISetObservResidencia) { bind<SetObservResidencia>() }
    singleOf(::ISetPlacaResidencia) { bind<SetPlacaResidencia>() }
    singleOf(::ISetStatusSentMovResidencia) { bind<SetStatusSentMovResidencia>() }
    singleOf(::ISetVeiculoResidencia) { bind<SetVeiculoResidencia>() }
    singleOf(::IStartInputMovEquipResidencia) { bind<StartInputMovEquipResidencia>() }
    singleOf(::IStartOutputMovEquipResidencia) { bind<StartOutputMovEquipResidencia>() }
}

/////////////////////////////////////////////////////////////////////////////////////////////

val usecaseUpdateModule = module {
    singleOf(::IUpdateChave) { bind<UpdateChave>() }
    singleOf(::IUpdateColab) { bind<UpdateColab>() }
    singleOf(::IUpdateEquip) { bind<UpdateEquip>() }
    singleOf(::IUpdateFluxo) { bind<UpdateFluxo>() }
    singleOf(::IUpdateLocal) { bind<UpdateLocal>() }
    singleOf(::IUpdateLocalTrab) { bind<UpdateLocalTrab>() }
    singleOf(::IUpdateRLocalFluxo) { bind<UpdateRLocalFluxo>() }
    singleOf(::IUpdateTerceiro) { bind<UpdateTerceiro>() }
    singleOf(::IUpdateVisitante) { bind<UpdateVisitante>() }
}

val usecaseCleanTableModule = module {
    singleOf(::ICleanChave) { bind<br.com.usinasantafe.pcp.domain.usecases.updatetable.cleantable.CleanChave>() }
    singleOf(::ICleanColab) { bind<CleanColab>() }
    singleOf(::ICleanEquip) { bind<CleanEquip>() }
    singleOf(::ICleanFluxo) { bind<CleanFluxo>() }
    singleOf(::ICleanLocal) { bind<CleanLocal>() }
    singleOf(::ICleanLocalTrab) { bind<CleanLocalTrab>() }
    singleOf(::ICleanRLocalFluxo) { bind<CleanRLocalFluxo>() }
    singleOf(::ICleanTerceiro) { bind<CleanTerceiro>() }
    singleOf(::ICleanVisitante) { bind<CleanVisitante>() }
}

val usecaseRecoverServerModule = module {
    singleOf(::IGetServerChave) { bind<GetServerChave>() }
    singleOf(::IGetServerColab) { bind<GetServerColab>() }
    singleOf(::IGetServerEquip) { bind<GetServerEquip>() }
    singleOf(::IGetServerFluxo) { bind<GetServerFluxo>() }
    singleOf(::IGetServerLocal) { bind<GetServerLocal>() }
    singleOf(::IGetServerLocalTrab) { bind<GetServerLocalTrab>() }
    singleOf(::IGetServerRLocalFluxo) { bind<GetServerRLocalFluxo>() }
    singleOf(::IGetServerTerceiro) { bind<GetServerTerceiro>() }
    singleOf(::IGetServerVisitante) { bind<GetServerVisitante>() }
}

val usecaseSaveAllTableModule = module {
    singleOf(::ISaveChave) { bind<SaveChave>() }
    singleOf(::ISaveColab) { bind<SaveColab>() }
    singleOf(::ISaveEquip) { bind<SaveEquip>() }
    singleOf(::ISaveFluxo) { bind<SaveFluxo>() }
    singleOf(::ISaveLocal) { bind<SaveLocal>() }
    singleOf(::ISaveLocalTrab) { bind<SaveLocalTrab>() }
    singleOf(::ISaveRLocalFluxo) { bind<SaveRLocalFluxo>() }
    singleOf(::ISaveTerceiro) { bind<SaveTerceiro>() }
    singleOf(::ISaveVisitante) { bind<SaveVisitante>() }
}

/////////////////////////////////////////////////////////////////////////////////////////////

val repositoryModule = module {

    singleOf(::IConfigRepository) { bind<ConfigRepository>() }
    singleOf(::IMovChaveRepository) { bind<MovChaveRepository>() }
    singleOf(::IMovChaveEquipRepository) { bind<MovChaveEquipRepository>() }
    singleOf(::IMovEquipProprioRepository) { bind<MovEquipProprioRepository>() }
    singleOf(::IMovEquipProprioPassagRepository) { bind<MovEquipProprioPassagRepository>() }
    singleOf(::IMovEquipProprioEquipSegRepository) { bind<MovEquipProprioEquipSegRepository>() }
    singleOf(::IMovEquipVisitTercRepository) { bind<MovEquipVisitTercRepository>() }
    singleOf(::IMovEquipVisitTercPassagRepository) { bind<MovEquipVisitTercPassagRepository>() }
    singleOf(::IMovEquipResidenciaRepository) { bind<MovEquipResidenciaRepository>() }

    singleOf(::IChaveRepository) { bind<ChaveRepository>() }
    singleOf(::IColabRepository) { bind<ColabRepository>() }
    singleOf(::IEquipRepository) { bind<EquipRepository>() }
    singleOf(::IFluxoRepository) { bind<FluxoRepository>() }
    singleOf(::ILocalRepository) { bind<LocalRepository>() }
    singleOf(::ILocalTrabRepository) { bind<LocalTrabRepository>() }
    singleOf(::IRLocalFluxoRepository) { bind<RLocalFluxoRepository>() }
    singleOf(::ITerceiroRepository) { bind<TerceiroRepository>() }
    singleOf(::IVisitanteRepository) { bind<VisitanteRepository>() }

}

val datasourceSharedPreferencesModule = module {

    singleOf(::IConfigSharedPreferencesDatasource) { bind<ConfigSharedPreferencesDatasource>() }
    singleOf(::IMovChaveSharedPreferencesDatasource) { bind<MovChaveSharedPreferencesDatasource>() }
    singleOf(::IMovChaveEquipSharedPreferencesDatasource) { bind<MovChaveEquipSharedPreferencesDatasource>() }
    singleOf(::IMovEquipProprioSharedPreferencesDatasource) { bind<MovEquipProprioSharedPreferencesDatasource>() }
    singleOf(::IMovEquipProprioEquipSegSharedPreferencesDatasource) { bind<MovEquipProprioEquipSegSharedPreferencesDatasource>() }
    singleOf(::IMovEquipProprioPassagSharedPreferencesDatasource) { bind<MovEquipProprioPassagSharedPreferencesDatasource>() }
    singleOf(::IMovEquipVisitTercSharedPreferencesDatasource) { bind<MovEquipVisitTercSharedPreferencesDatasource>() }
    singleOf(::IMovEquipVisitTercPassagSharedPreferencesDatasource) { bind<MovEquipVisitTercPassagSharedPreferencesDatasource>() }
    singleOf(::IMovEquipResidenciaSharedPreferencesDatasource) { bind<MovEquipResidenciaSharedPreferencesDatasource>() }

}

val datasourceRoomModule = module {

    singleOf(::IMovChaveRoomDatasource) { bind<MovChaveRoomDatasource>() }
    singleOf(::IMovChaveEquipRoomDatasource) { bind<MovChaveEquipRoomDatasource>() }
    singleOf(::IMovEquipProprioPassagRoomDatasource) { bind<MovEquipProprioPassagRoomDatasource>() }
    singleOf(::IMovEquipProprioEquipSegRoomDatasource) { bind<MovEquipProprioEquipSegRoomDatasource>() }
    singleOf(::IMovEquipProprioRoomDatasource) { bind<MovEquipProprioRoomDatasource>() }
    singleOf(::IMovEquipVisitTercRoomDatasource) { bind<MovEquipVisitTercRoomDatasource>() }
    singleOf(::IMovEquipResidenciaRoomDatasource) { bind<MovEquipResidenciaRoomDatasource>() }
    singleOf(::IMovEquipVisitTercPassagRoomDatasource) { bind<MovEquipVisitTercPassagRoomDatasource>() }

    singleOf(::IChaveRoomDatasource) { bind<ChaveRoomDatasource>() }
    singleOf(::IColabRoomDatasource) { bind<ColabRoomDatasource>() }
    singleOf(::IEquipRoomDatasource) { bind<EquipRoomDatasource>() }
    singleOf(::IFluxoRoomDatasource) { bind<FluxoRoomDatasource>() }
    singleOf(::ILocalRoomDatasource) { bind<LocalRoomDatasource>() }
    singleOf(::ILocalTrabRoomDatasource) { bind<LocalTrabRoomDatasource>() }
    singleOf(::IRLocalFluxoRoomDatasource) { bind<RLocalFluxoRoomDatasource>() }
    singleOf(::ITerceiroRoomDatasource) { bind<TerceiroRoomDatasource>() }
    singleOf(::IVisitanteRoomDatasource) { bind<VisitanteRoomDatasource>() }

}

val datasourceRetrofitModule = module {

    singleOf(::IConfigRetrofitDatasource) { bind<ConfigRetrofitDatasource>() }
    singleOf(::IMovChaveRetrofitDatasource) { bind<MovChaveRetrofitDatasource>() }
    singleOf(::IMovChaveEquipRetrofitDatasource) { bind<MovChaveEquipRetrofitDatasource>() }
    singleOf(::IMovEquipProprioRetrofitDatasource) { bind<MovEquipProprioRetrofitDatasource>() }
    singleOf(::IMovEquipVisitTercRetrofitDatasource) { bind<MovEquipVisitTercRetrofitDatasource>() }
    singleOf(::IMovEquipResidenciaRetrofitDatasource) { bind<MovEquipResidenciaRetrofitDatasource>() }

    singleOf(::IChaveRetrofitDatasource) { bind<ChaveRetrofitDatasource>() }
    singleOf(::IColabRetrofitDatasource) { bind<ColabRetrofitDatasource>() }
    singleOf(::IEquipRetrofitDatasource) { bind<EquipRetrofitDatasource>() }
    singleOf(::IFluxoRetrofitDatasource) { bind<FluxoRetrofitDatasource>() }
    singleOf(::ILocalRetrofitDatasource) { bind<LocalRetrofitDatasource>() }
    singleOf(::ILocalTrabRetrofitDatasource) { bind<LocalTrabRetrofitDatasource>() }
    singleOf(::IRLocalFluxoRetrofitDatasource) { bind<RLocalFluxoRetrofitDatasource>() }
    singleOf(::ITerceiroRetrofitDatasource) { bind<TerceiroRetrofitDatasource>() }
    singleOf(::IVisitanteRetrofitDatasource) { bind<VisitanteRetrofitDatasource>() }

}

val apiRetrofitModule = module {

    single { get<Retrofit>().create(ConfigApi::class.java) }
    single { get<Retrofit>().create(MovChaveApi::class.java) }
    single { get<Retrofit>().create(MovChaveEquipApi::class.java) }
    single { get<Retrofit>().create(MovEquipProprioApi::class.java) }
    single { get<Retrofit>().create(MovEquipVisitTercApi::class.java) }
    single { get<Retrofit>().create(MovEquipResidenciaApi::class.java) }

    single { get<Retrofit>().create(ChaveApi::class.java) }
    single { get<Retrofit>().create(ColabApi::class.java) }
    single { get<Retrofit>().create(EquipApi::class.java) }
    single { get<Retrofit>().create(FluxoApi::class.java) }
    single { get<Retrofit>().create(LocalApi::class.java) }
    single { get<Retrofit>().create(LocalTrabApi::class.java) }
    single { get<Retrofit>().create(RLocalFluxoApi::class.java) }
    single { get<Retrofit>().create(TerceiroApi::class.java) }
    single { get<Retrofit>().create(VisitanteApi::class.java) }

}

val apiRoomModule = module {

    single { get<AppDatabaseRoom>().chaveDao() }
    single { get<AppDatabaseRoom>().colabDao() }
    single { get<AppDatabaseRoom>().equipDao() }
    single { get<AppDatabaseRoom>().fluxoDao() }
    single { get<AppDatabaseRoom>().localDao() }
    single { get<AppDatabaseRoom>().localTrabDao() }
    single { get<AppDatabaseRoom>().rLocalFluxoDao() }
    single { get<AppDatabaseRoom>().terceiroDao() }
    single { get<AppDatabaseRoom>().visitanteDao() }

    single { get<AppDatabaseRoom>().movChaveEquipDao() }
    single { get<AppDatabaseRoom>().movChaveDao() }
    single { get<AppDatabaseRoom>().movEquipProprioDao() }
    single { get<AppDatabaseRoom>().movEquipProprioPassagDao() }
    single { get<AppDatabaseRoom>().movEquipProprioEquipSegDao() }
    single { get<AppDatabaseRoom>().movEquipVisitTercDao() }
    single { get<AppDatabaseRoom>().movEquipVisitTercPassagDao() }
    single { get<AppDatabaseRoom>().movEquipResidenciaDao() }

}

val sharedPreferencesModule = module {
    single { providerSharedPreferences(androidContext()) }
}

val retrofitModule = module {
    single { provideRetrofit(androidContext()) }
}

val roomModule = module {
    single { provideRoom(androidContext()) }
}

val workManagerModule = module {
    single { providerWorkManager(androidContext()) }
    workerOf(::ProcessWorkManager) { named<ProcessWorkManager>() }
}