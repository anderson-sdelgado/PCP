package br.com.usinasantafe.pcp.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.usinasantafe.pcp.presenter.Args.CPF_VISIT_TERC_ARGS
import br.com.usinasantafe.pcp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.MATRIC_COLAB_ARGS
import br.com.usinasantafe.pcp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_EQUIP_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcp.presenter.Routes.CHAVE_LIST_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.CONFIG_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.CONTROLE_CHAVE_EDIT_LIST_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.CONTROLE_CHAVE_EQUIP_EDIT_LIST_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.CONTROLE_CHAVE_EQUIP_LIST_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.CONTROLE_CHAVE_LIST_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.CPF_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.DESTINO_PROPRIO_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.DESTINO_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.DETALHE_CHAVE_EQUIP_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.DETALHE_CHAVE_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.DETALHE_MOV_PROPRIO_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.DETALHE_RESIDENCIA_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.DETALHE_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.EQUIP_CHAVE_EQUIP_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.EQUIP_SEG_LIST_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.INITIAL_TEST_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.NRO_EQUIP_PROPRIO_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.PASSAG_COLAB_LIST_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.LOCAL_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.MATRIC_COLAB_CHAVE_EQUIP_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.MATRIC_COLAB_CHAVE_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.MATRIC_COLAB_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.MATRIC_VIGIA_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.MENU_APONT_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.MENU_INICIAL_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.MOTORISTA_RESIDENCIA_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.MOV_EQUIP_PROPRIO_LIST_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.MOV_EQUIP_RESIDENCIA_EDIT_LIST_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.MOV_EQUIP_RESIDENCIA_LIST_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.MOV_EQUIP_VISIT_TERC_EDIT_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.MOV_EQUIP_VISIT_TERC_LIST_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.NOME_COLAB_CHAVE_EQUIP_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.NOME_COLAB_CHAVE_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.NOME_COLAB_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.NOME_VIGIA_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.NOME_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.NOTA_FISCAL_PROPRIO_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.OBSERV_CHAVE_EQUIP_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.OBSERV_CHAVE_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.OBSERV_PROPRIO_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.OBSERV_RESIDENCIA_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.OBSERV_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.PASSAG_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.PLACA_RESIDENCIA_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.PLACA_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.SENHA_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.SPLASH_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.TIPO_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.VEICULO_RESIDENCIA_ROUTE
import br.com.usinasantafe.pcp.presenter.Routes.VEICULO_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcp.presenter.chave.chavelist.ChaveListScreen
import br.com.usinasantafe.pcp.presenter.chave.chavelist.ChaveListViewModel
import br.com.usinasantafe.pcp.presenter.chave.controleeditlist.ControleChaveEditListScreen
import br.com.usinasantafe.pcp.presenter.chave.controleeditlist.ControleChaveEditListViewModel
import br.com.usinasantafe.pcp.presenter.chave.controlelist.ControleChaveListScreen
import br.com.usinasantafe.pcp.presenter.chave.controlelist.ControleChaveListViewModel
import br.com.usinasantafe.pcp.presenter.chave.detalhe.DetalheChaveScreen
import br.com.usinasantafe.pcp.presenter.chave.detalhe.DetalheChaveViewModel
import br.com.usinasantafe.pcp.presenter.chave.matriccolab.MatricColabChaveScreen
import br.com.usinasantafe.pcp.presenter.chave.matriccolab.MatricColabChaveViewModel
import br.com.usinasantafe.pcp.presenter.chave.nomecolab.NomeColabChaveScreen
import br.com.usinasantafe.pcp.presenter.chave.nomecolab.NomeColabChaveViewModel
import br.com.usinasantafe.pcp.presenter.chave.observ.ObservChaveScreen
import br.com.usinasantafe.pcp.presenter.chave.observ.ObservChaveViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.controleeditlist.ControleChaveEquipEditListScreen
import br.com.usinasantafe.pcp.presenter.chaveequip.controleeditlist.ControleChaveEquipEditListViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.controlelist.ControleChaveEquipListScreen
import br.com.usinasantafe.pcp.presenter.chaveequip.controlelist.ControleChaveEquipListViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.detalhe.DetalheChaveEquipScreen
import br.com.usinasantafe.pcp.presenter.chaveequip.detalhe.DetalheChaveEquipViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.matriccolab.MatricColabChaveEquipScreen
import br.com.usinasantafe.pcp.presenter.chaveequip.matriccolab.MatricColabChaveEquipViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.nomecolab.NomeColabChaveEquipScreen
import br.com.usinasantafe.pcp.presenter.chaveequip.nomecolab.NomeColabChaveEquipViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.nroequip.NroEquipChaveEquipScreen
import br.com.usinasantafe.pcp.presenter.chaveequip.nroequip.NroEquipChaveEquipViewModel
import br.com.usinasantafe.pcp.presenter.chaveequip.observ.ObservChaveEquipScreen
import br.com.usinasantafe.pcp.presenter.chaveequip.observ.ObservChaveEquipViewModel
import br.com.usinasantafe.pcp.presenter.configuration.config.ConfigScreen
import br.com.usinasantafe.pcp.presenter.configuration.config.ConfigViewModel
import br.com.usinasantafe.pcp.presenter.initial.local.LocalScreen
import br.com.usinasantafe.pcp.presenter.initial.local.LocalViewModel
import br.com.usinasantafe.pcp.presenter.initial.matricvigia.MatricVigiaScreen
import br.com.usinasantafe.pcp.presenter.initial.matricvigia.MatricVigiaViewModel
import br.com.usinasantafe.pcp.presenter.initial.menuapont.MenuApontScreen
import br.com.usinasantafe.pcp.presenter.initial.menuapont.MenuApontViewModel
import br.com.usinasantafe.pcp.presenter.configuration.senha.SenhaScreen
import br.com.usinasantafe.pcp.presenter.configuration.menuinicial.MenuInicialScreen
import br.com.usinasantafe.pcp.presenter.configuration.menuinicial.MenuInicialViewModel
import br.com.usinasantafe.pcp.presenter.initial.nomevigia.NomeVigiaScreen
import br.com.usinasantafe.pcp.presenter.initial.nomevigia.NomeVigiaViewModel
import br.com.usinasantafe.pcp.presenter.configuration.senha.SenhaViewModel
import br.com.usinasantafe.pcp.presenter.proprio.destino.DestinoProprioScreen
import br.com.usinasantafe.pcp.presenter.proprio.destino.DestinoProprioViewModel
import br.com.usinasantafe.pcp.presenter.proprio.passaglist.PassagColabListScreen
import br.com.usinasantafe.pcp.presenter.proprio.detalhe.DetalheMovProprioScreen
import br.com.usinasantafe.pcp.presenter.proprio.detalhe.DetalheProprioViewModel
import br.com.usinasantafe.pcp.presenter.proprio.equipseglist.EquipSegListScreen
import br.com.usinasantafe.pcp.presenter.proprio.equipseglist.EquipSegListViewModel
import br.com.usinasantafe.pcp.presenter.proprio.nroequip.NroEquipScreen
import br.com.usinasantafe.pcp.presenter.proprio.nroequip.NroEquipProprioViewModel
import br.com.usinasantafe.pcp.presenter.proprio.matriccolab.MatricColabScreen
import br.com.usinasantafe.pcp.presenter.proprio.matriccolab.MatricColabViewModel
import br.com.usinasantafe.pcp.presenter.proprio.movlist.MovEquipProprioListScreen
import br.com.usinasantafe.pcp.presenter.proprio.movlist.MovEquipProprioListViewModel
import br.com.usinasantafe.pcp.presenter.proprio.nomecolab.NomeColabScreen
import br.com.usinasantafe.pcp.presenter.proprio.nomecolab.NomeColabViewModel
import br.com.usinasantafe.pcp.presenter.proprio.notafiscal.NotaFiscalProprioScreen
import br.com.usinasantafe.pcp.presenter.proprio.notafiscal.NotaFiscalViewModel
import br.com.usinasantafe.pcp.presenter.proprio.observ.ObservProprioScreen
import br.com.usinasantafe.pcp.presenter.proprio.observ.ObservProprioViewModel
import br.com.usinasantafe.pcp.presenter.proprio.passaglist.PassagColabListViewModel
import br.com.usinasantafe.pcp.presenter.residencia.detalhe.DetalheResidenciaScreen
import br.com.usinasantafe.pcp.presenter.residencia.detalhe.DetalheResidenciaViewModel
import br.com.usinasantafe.pcp.presenter.residencia.motorista.MotoristaResidenciaScreen
import br.com.usinasantafe.pcp.presenter.residencia.motorista.MotoristaResidenciaViewModel
import br.com.usinasantafe.pcp.presenter.residencia.moveditlist.MovEquipResidenciaEditListScreen
import br.com.usinasantafe.pcp.presenter.residencia.moveditlist.MovEquipResidenciaEditListViewModel
import br.com.usinasantafe.pcp.presenter.residencia.movlist.MovEquipResidenciaListScreen
import br.com.usinasantafe.pcp.presenter.residencia.movlist.MovEquipResidenciaListViewModel
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaScreen
import br.com.usinasantafe.pcp.presenter.residencia.observ.ObservResidenciaViewModel
import br.com.usinasantafe.pcp.presenter.residencia.placa.PlacaResidenciaScreen
import br.com.usinasantafe.pcp.presenter.residencia.placa.PlacaResidenciaViewModel
import br.com.usinasantafe.pcp.presenter.residencia.veiculo.VeiculoResidenciaScreen
import br.com.usinasantafe.pcp.presenter.residencia.veiculo.VeiculoResidenciaViewModel
import br.com.usinasantafe.pcp.presenter.splash.SplashScreen
import br.com.usinasantafe.pcp.presenter.splash.SplashViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CpfVisitTercScreen
import br.com.usinasantafe.pcp.presenter.visitterc.cpf.CpfVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.destino.DestinoVisitTercScreen
import br.com.usinasantafe.pcp.presenter.visitterc.destino.DestinoVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.detalhe.DetalheVisitTercScreen
import br.com.usinasantafe.pcp.presenter.visitterc.detalhe.DetalheVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.moveditlist.MovEquipVisitTercEditListScreen
import br.com.usinasantafe.pcp.presenter.visitterc.moveditlist.MovEquipVisitTercEditListViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.movlist.MovEquipVisitTercListScreen
import br.com.usinasantafe.pcp.presenter.visitterc.movlist.MovEquipVisitTercListViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.nome.NomeVisitTercScreen
import br.com.usinasantafe.pcp.presenter.visitterc.nome.NomeVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.observ.ObservVisitTercScreen
import br.com.usinasantafe.pcp.presenter.visitterc.observ.ObservVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.passaglist.PassagVisitTercListScreen
import br.com.usinasantafe.pcp.presenter.visitterc.passaglist.PassagVisitTercListViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.placa.PlacaVisitTercScreen
import br.com.usinasantafe.pcp.presenter.visitterc.placa.PlacaVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.tipo.TipoVisitTercScreen
import br.com.usinasantafe.pcp.presenter.visitterc.tipo.TipoVisitTercViewModel
import br.com.usinasantafe.pcp.presenter.visitterc.veiculo.VeiculoVisitTercScreen
import br.com.usinasantafe.pcp.presenter.visitterc.veiculo.VeiculoVisitTercViewModel
import br.com.usinasantafe.pcp.utils.FlowApp
import br.com.usinasantafe.pcp.utils.TypeEquip
import br.com.usinasantafe.pcp.utils.TypeMovEquip
import br.com.usinasantafe.pcp.utils.TypeMovKey
import br.com.usinasantafe.pcp.utils.TypeOcupante
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = INITIAL_TEST_ROUTE,
    navActions: NavigationActions = remember(navHostController) {
        NavigationActions(navHostController)
    }
) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(INITIAL_TEST_ROUTE) {
            InititalTestScreen(
                onNavSplash = { navActions.navigateToSplash() }
            )
        }
        composable(SPLASH_ROUTE) {
            SplashScreen(
                viewModel = koinViewModel<SplashViewModel>(),
                onNavMenuInicial = { navActions.navigateToMenuInicial() },
                onNavMenuApont = { navActions.navigateToMenuApont() }
            )
        }

        ///////////////////////// Config //////////////////////////////////

        composable(MENU_INICIAL_ROUTE) {
            MenuInicialScreen(
                viewModel = koinViewModel<MenuInicialViewModel>(),
                onNavMatricVigia = { navActions.navigateToMatricVigia() },
                onNavSenha = { navActions.navigateToSenha() }
            )
        }
        composable(SENHA_ROUTE) {
            SenhaScreen(
                viewModel = koinViewModel<SenhaViewModel>(),
                onNavMenuInicial = { navActions.navigateToMenuInicial() },
                onNavConfig = { navActions.navigateToConfig() }
            )
        }
        composable(CONFIG_ROUTE) {
            ConfigScreen(
                viewModel = koinViewModel<ConfigViewModel>(),
                onNavMenuInicial = { navActions.navigateToMenuInicial() }
            )
        }

        ////////////////////////////////////////////////////////////////////

        ///////////////////////// Initial //////////////////////////////////

        composable(MATRIC_VIGIA_ROUTE) {
            MatricVigiaScreen(
                viewModel = koinViewModel<MatricVigiaViewModel>(),
                onNavMenuInicial = { navActions.navigateToMenuInicial() },
                onNavNomeVigia = { navActions.navigateToNomeVigia() }
            )
        }
        composable(NOME_VIGIA_ROUTE) {
            NomeVigiaScreen(
                viewModel = koinViewModel<NomeVigiaViewModel>(),
                onNavMatricVigia = { navActions.navigateToMatricVigia() },
                onNavLocal = { navActions.navigateToLocal() }
            )
        }
        composable(LOCAL_ROUTE) {
            LocalScreen(
                viewModel = koinViewModel<LocalViewModel>(),
                onNavNomeVigia = { navActions.navigateToNomeVigia() },
                onNavMenuApont = { navActions.navigateToMenuApont() }
            )
        }
        composable(MENU_APONT_ROUTE) {
            MenuApontScreen(
                viewModel = koinViewModel<MenuApontViewModel>(),
                onNavMovVeicProprio = { navActions.navigateToMovEquipProprioList() },
                onNavMovVeicVisitTerc = { navActions.navigationToMovEquipVisitTercList() },
                onNavMovVeicResidencia = { navActions.navigationToMovEquipResidenciaList() },
                onNavMovChave = { navActions.navigationToControleChaveList() },
                onNavMovChaveEquip = { navActions.navigationToControleChaveEquipList() },
                onNavSplashScreen = { navActions.navigateToSplash() }
            )
        }

        ////////////////////////////////////////////////////////////////////

        ///////////////////////// Proprio //////////////////////////////////

        composable(MOV_EQUIP_PROPRIO_LIST_ROUTE) {
            MovEquipProprioListScreen(
                viewModel = koinViewModel<MovEquipProprioListViewModel>(),
                onNavNroEquip = {
                    navActions.navigationToNroEquip(
                        flowApp = FlowApp.ADD.ordinal,
                        typeEquip = TypeEquip.VEICULO.ordinal,
                        id = 0
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheProprio(it)
                },
                onNavMenuApont = { navActions.navigateToMenuApont() },
                onNavSplashScreen = { navActions.navigateToSplash() }
            )
        }
        composable(
            MATRIC_COLAB_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            MatricColabScreen(
                viewModel = koinViewModel<MatricColabViewModel>(),
                onNavEquipSegList = {
                    navActions.navigationToEquipSegList(
                        flowApp = FlowApp.ADD.ordinal,
                        typeEquip = TypeEquip.VEICULOSEG.ordinal,
                        id = 0
                    )
                },
                onNavPassagColabList = {
                    navActions.navigationToPassagColabList(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavNomeColab = {
                    navActions.navigateToNomeColab(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!,
                        matricColab = it
                    )
                }
            )
        }
        composable(
            NOME_COLAB_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType },
                navArgument(MATRIC_COLAB_ARGS) { type = NavType.StringType }
            )
        ) { entry ->
            NomeColabScreen(
                viewModel = koinViewModel<NomeColabViewModel>(),
                onNavMatricColab = {
                    navActions.navigateToMatricColab(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavPassagColabList = {
                    navActions.navigationToPassagColabList(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheProprio(
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
            )
        }
        composable(
            DETALHE_MOV_PROPRIO_ROUTE,
            arguments = listOf(
                navArgument(ID_ARGS) { type = NavType.IntType },
            )
        ) { entry ->
            DetalheMovProprioScreen(
                viewModel = koinViewModel<DetalheProprioViewModel>(),
                onNavMovProprioList = { navActions.navigateToMovEquipProprioList() },
                onNavNroEquip = {
                    navActions.navigationToNroEquip(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeEquip = TypeEquip.VEICULO.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavEquipSegList = {
                    navActions.navigationToEquipSegList(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeEquip = TypeEquip.VEICULOSEG.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavMatricColab = {
                    navActions.navigateToMatricColab(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeOcupante = TypeOcupante.MOTORISTA.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavPassagList = {
                    navActions.navigationToPassagColabList(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeOcupante = TypeOcupante.PASSAGEIRO.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDestino = {
                    navActions.navigationToDestinoProprio(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavNotaFiscal = {
                    navActions.navigationToNotaFiscalProprio(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservProprio(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }
        composable(
            PASSAG_COLAB_LIST_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            PassagColabListScreen(
                viewModel = koinViewModel<PassagColabListViewModel>(),
                onNavMatricMotorista = {
                    navActions.navigateToMatricColab(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMatricPassag = {
                    navActions.navigateToMatricColab(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = TypeOcupante.PASSAGEIRO.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDestino = {
                    navActions.navigationToDestinoProprio(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }
        composable(
            NRO_EQUIP_PROPRIO_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_EQUIP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            NroEquipScreen(
                viewModel = koinViewModel<NroEquipProprioViewModel>(),
                onNavMovProprioList = {
                    navActions.navigateToMovEquipProprioList()
                },
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavEquipSegList = {
                    navActions.navigationToEquipSegList(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeEquip = entry.arguments?.getInt(TYPE_EQUIP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }
        composable(
            EQUIP_SEG_LIST_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_EQUIP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            EquipSegListScreen(
                viewModel = koinViewModel<EquipSegListViewModel>(),
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavNroEquip = {
                    navActions.navigationToNroEquip(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeEquip = it,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavMatricColab = {
                    navActions.navigateToMatricColab(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = TypeOcupante.MOTORISTA.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }
        composable(
            DESTINO_PROPRIO_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            DestinoProprioScreen(
                viewModel = koinViewModel<DestinoProprioViewModel>(),
                onNavPassagList = {
                    navActions.navigationToPassagColabList(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = TypeOcupante.PASSAGEIRO.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavNotaFiscal = {
                    navActions.navigationToNotaFiscalProprio(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservProprio(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }
        composable(
            NOTA_FISCAL_PROPRIO_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            NotaFiscalProprioScreen(
                viewModel = koinViewModel<NotaFiscalViewModel>(),
                onNavDestino = {
                    navActions.navigationToDestinoProprio(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservProprio(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
            )
        }
        composable(
            OBSERV_PROPRIO_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            ObservProprioScreen(
                viewModel = koinViewModel<ObservProprioViewModel>(),
                onNavDestino = {
                    navActions.navigationToDestinoProprio(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavNotaFiscal = {
                    navActions.navigationToNotaFiscalProprio(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMovList = { navActions.navigateToMovEquipProprioList() },
            )
        }

        ////////////////////////////////////////////////////////////////////

        ///////////////////////// VisitTerc ////////////////////////////////

        composable(
            MOV_EQUIP_VISIT_TERC_LIST_ROUTE
        ) {
            MovEquipVisitTercListScreen(
                viewModel = koinViewModel<MovEquipVisitTercListViewModel>(),
                onNavVeiculo = {
                    navActions.navigationToVeiculoVisitTerc(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
                onNavMovEquipEditList = {
                    navActions.navigationToMovEquipVisitTercEditList()
                },
                onNavMenuApont = { navActions.navigateToMenuApont() },
                onNavObserv = {
                    navActions.navigationToObservVisitTerc(
                        flowApp = FlowApp.ADD.ordinal,
                        typeMov = TypeMovEquip.OUTPUT.ordinal,
                        id = it
                    )
                },
            )
        }
        composable(
            VEICULO_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            VeiculoVisitTercScreen(
                viewModel = koinViewModel<VeiculoVisitTercViewModel>(),
                onNavPlaca = {
                    navActions.navigationToPlacaVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavMovList = {
                    navActions.navigationToMovEquipVisitTercList()
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheVisitTerc(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                }
            )
        }
        composable(
            PLACA_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            PlacaVisitTercScreen(
                viewModel = koinViewModel<PlacaVisitTercViewModel>(),
                onNavTipo = {
                    navActions.navigationToTipoVisitTerc()
                },
                onNavVeiculo = {
                    navActions.navigationToVeiculoVisitTerc(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheVisitTerc(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                }
            )
        }
        composable(
            TIPO_VISIT_TERC_ROUTE
        ) {
            TipoVisitTercScreen(
                viewModel = koinViewModel<TipoVisitTercViewModel>(),
                onNavPlacaVisitTerc = {
                    navActions.navigationToPlacaVisitTerc(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
                onNavCpfVisitTerc = {
                    navActions.navigationToCpfVisitTerc(
                        flowApp = FlowApp.ADD.ordinal,
                        typeOcupante = TypeOcupante.MOTORISTA.ordinal,
                        id = 0
                    )
                }
            )
        }
        composable(
            CPF_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            CpfVisitTercScreen(
                viewModel = koinViewModel<CpfVisitTercViewModel>(),
                onNavTipo = {
                    navActions.navigationToTipoVisitTerc()
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheVisitTerc(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavNome = {
                    navActions.navigationToNomeVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!,
                        cpfVisitTerc = it
                    )
                },
                onNavPassagList = {
                    navActions.navigationToPassagVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!,
                    )
                }
            )
        }
        composable(
            NOME_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType },
                navArgument(CPF_VISIT_TERC_ARGS) { type = NavType.StringType }
            )
        ) { entry ->
            NomeVisitTercScreen(
                viewModel = koinViewModel<NomeVisitTercViewModel>(),
                onNavCpf = {
                    navActions.navigationToCpfVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!,
                    )
                },
                onNavPassag = {
                    navActions.navigationToPassagVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!,
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheVisitTerc(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                }
            )
        }
        composable(
            PASSAG_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            PassagVisitTercListScreen(
                viewModel = koinViewModel<PassagVisitTercListViewModel>(),
                onNavCpf = {
                    navActions.navigationToCpfVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheVisitTerc(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavCpfPassag = {
                    navActions.navigationToCpfVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = TypeOcupante.PASSAGEIRO.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDestino = {
                    navActions.navigationToDestinoVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
            )
        }
        composable(
            DESTINO_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            DestinoVisitTercScreen(
                viewModel = koinViewModel<DestinoVisitTercViewModel>(),
                onNavPassagList = {
                    navActions.navigationToPassagVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = TypeOcupante.PASSAGEIRO.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheVisitTerc(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeMov = TypeMovEquip.INPUT.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }
        composable(
            OBSERV_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_MOV_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            ObservVisitTercScreen(
                viewModel = koinViewModel<ObservVisitTercViewModel>(),
                onNavDestino = {
                    navActions.navigationToDestinoVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = 0
                    )
                },
                onNavMovEquipList = {
                    navActions.navigationToMovEquipVisitTercList()
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheVisitTerc(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
            )
        }
        composable(
            MOV_EQUIP_VISIT_TERC_EDIT_ROUTE
        ) {
            MovEquipVisitTercEditListScreen(
                viewModel = koinViewModel<MovEquipVisitTercEditListViewModel>(),
                onNavMovEquipList = {
                    navActions.navigationToMovEquipVisitTercList()
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheVisitTerc(
                        id = it
                    )
                }
            )
        }
        composable(
            DETALHE_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(ID_ARGS) { type = NavType.IntType },
            )
        ) { entry ->
            DetalheVisitTercScreen(
                viewModel = koinViewModel<DetalheVisitTercViewModel>(),
                onNavMovEquipEditList = {
                    navActions.navigationToMovEquipVisitTercEditList()
                },
                onNavVeiculo = {
                    navActions.navigationToVeiculoVisitTerc(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavPlaca = {
                    navActions.navigationToPlacaVisitTerc(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavCpf = {
                    navActions.navigationToCpfVisitTerc(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeOcupante = TypeOcupante.MOTORISTA.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavPassagList = {
                    navActions.navigationToPassagVisitTerc(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeOcupante = TypeOcupante.PASSAGEIRO.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavDestino = {
                    navActions.navigationToDestinoVisitTerc(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservVisitTerc(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeMov = TypeMovEquip.OUTPUT.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
            )
        }

        ////////////////////////////////////////////////////////////////////

        ///////////////////////// Residencia ///////////////////////////////

        composable(
            MOV_EQUIP_RESIDENCIA_LIST_ROUTE
        ) {
            MovEquipResidenciaListScreen(
                viewModel = koinViewModel<MovEquipResidenciaListViewModel>(),
                onNavVeiculo = {
                    navActions.navigationToVeiculoResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
                onNavMovEquipEditList = {
                    navActions.navigationToMovEquipResidenciaEditList()
                },
                onNavObserv = {
                    navActions.navigationToObservResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        typeMov = TypeMovEquip.OUTPUT.ordinal,
                        id = it
                    )
                },
                onNavMenuApont = {
                    navActions.navigateToMenuApont()
                },
            )
        }
        composable(
            VEICULO_RESIDENCIA_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            VeiculoResidenciaScreen(
                viewModel = koinViewModel<VeiculoResidenciaViewModel>(),
                onNavMovEquipList = {
                    navActions.navigationToMovEquipResidenciaList()
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheResidencia(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavPlaca = {
                    navActions.navigationToPlacaResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
            )
        }
        composable(
            PLACA_RESIDENCIA_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            PlacaResidenciaScreen(
                viewModel = koinViewModel<PlacaResidenciaViewModel>(),
                onNavVeiculo = {
                    navActions.navigationToVeiculoResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheResidencia(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMotorista = {
                    navActions.navigationToMotoristaResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
            )
        }
        composable(
            MOTORISTA_RESIDENCIA_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType },
            )
        ) { entry ->
            MotoristaResidenciaScreen(
                viewModel = koinViewModel<MotoristaResidenciaViewModel>(),
                onNavPlaca = {
                    navActions.navigationToPlacaResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheResidencia(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        typeMov = TypeMovEquip.INPUT.ordinal,
                        id = 0
                    )
                },
            )
        }
        composable(
            OBSERV_RESIDENCIA_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_MOV_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            ObservResidenciaScreen(
                viewModel = koinViewModel<ObservResidenciaViewModel>(),
                onNavMotorista = {
                    navActions.navigationToMotoristaResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
                onNavMovEquipList = {
                    navActions.navigationToMovEquipResidenciaList()
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheResidencia(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
            )
        }
        composable(
            MOV_EQUIP_RESIDENCIA_EDIT_LIST_ROUTE
        ) {
            MovEquipResidenciaEditListScreen(
                viewModel = koinViewModel<MovEquipResidenciaEditListViewModel>(),
                onNavMovEquipList = {
                    navActions.navigationToMovEquipResidenciaList()
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheResidencia(
                        id = it
                    )
                },
            )
        }
        composable(
            DETALHE_RESIDENCIA_ROUTE,
            arguments = listOf(
                navArgument(ID_ARGS) { type = NavType.IntType },
            )
        ) { entry ->
            DetalheResidenciaScreen(
                viewModel = koinViewModel<DetalheResidenciaViewModel>(),
                onNavMovEquipEditList = {
                    navActions.navigationToMovEquipResidenciaEditList()
                },
                onNavVeiculo = {
                    navActions.navigationToVeiculoResidencia(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavPlaca = {
                    navActions.navigationToPlacaResidencia(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMotorista = {
                    navActions.navigationToMotoristaResidencia(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservResidencia(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeMov = TypeMovEquip.INPUT.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
            )
        }

        ////////////////////////////////////////////////////////////////////

        /////////////////////////// Chave //////////////////////////////////

        composable(
            CONTROLE_CHAVE_LIST_ROUTE
        ) {
            ControleChaveListScreen(
                viewModel = koinViewModel<ControleChaveListViewModel>(),
                onNavControleChaveEditList = {
                    navActions.navigationToControleChaveEditList()
                },
                onNavMatricColab = {
                    navActions.navigationToMatriColabChave(
                        flowApp = FlowApp.ADD.ordinal,
                        typeMov = TypeMovKey.RECEIPT.ordinal,
                        id = it
                    )
                },
                onNavMenuApont = {
                    navActions.navigateToMenuApont()
                },
                onNavChaveList = {
                    navActions.navigationToChaveList(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
            )
        }

        composable(
            CHAVE_LIST_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            ChaveListScreen(
                viewModel = koinViewModel<ChaveListViewModel>(),
                onNavMatricColab = {
                    navActions.navigationToMatriColabChave(
                        flowApp = FlowApp.ADD.ordinal,
                        typeMov = TypeMovKey.REMOVE.ordinal,
                        id = 0
                    )
                },
                onNavMenuControleList = { navActions.navigationToControleChaveList() },
                onNavDetalhe = {
                    navActions.navigationToDetalheChave(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                }
            )
        }

        composable(
            MATRIC_COLAB_CHAVE_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_MOV_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            MatricColabChaveScreen(
                viewModel = koinViewModel<MatricColabChaveViewModel>(),
                onNavChaveList = {
                    navActions.navigationToChaveList(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheChave(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavNomeColab = {
                    navActions.navigationToNomeColabChave(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeMov = entry.arguments?.getInt(TYPE_MOV_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!,
                        matricColab = it
                    )
                },
                onNavMovList = {
                    navActions.navigationToControleChaveList()
                }
            )
        }

        composable(
            NOME_COLAB_CHAVE_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_MOV_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType },
                navArgument(MATRIC_COLAB_ARGS) { type = NavType.StringType }
            )
        ) { entry ->
            NomeColabChaveScreen(
                viewModel = koinViewModel<NomeColabChaveViewModel>(),
                onNavDetalhe = {
                    navActions.navigationToDetalheChave(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMatricColab = {
                    navActions.navigationToMatriColabChave(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeMov =  entry.arguments?.getInt(TYPE_MOV_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservChave(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeMov =  entry.arguments?.getInt(TYPE_MOV_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }

        composable(
            OBSERV_CHAVE_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_MOV_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            ObservChaveScreen(
                viewModel = koinViewModel<ObservChaveViewModel>(),
                onNavDetalhe = {
                    navActions.navigationToDetalheChave(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMatricColab = {
                    navActions.navigationToMatriColabChave(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeMov =  entry.arguments?.getInt(TYPE_MOV_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavControleList = { navActions.navigationToControleChaveList() }
            )
        }

        composable(
            CONTROLE_CHAVE_EDIT_LIST_ROUTE
        ) {
            ControleChaveEditListScreen(
                viewModel = koinViewModel<ControleChaveEditListViewModel>(),
                onNavControleList = { navActions.navigationToControleChaveList() },
                onNavDetalhe = {
                    navActions.navigationToDetalheChave(
                        id = it
                    )
                }
            )
        }

        composable(
            DETALHE_CHAVE_ROUTE,
            arguments = listOf(
                navArgument(ID_ARGS) { type = NavType.IntType },
            )
        ) { entry ->
            DetalheChaveScreen(
                viewModel = koinViewModel<DetalheChaveViewModel>(),
                onNavControleChaveEditList = { navActions.navigationToControleChaveEditList() },
                onNavMatricColab = {
                    navActions.navigationToMatriColabChave(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeMov = TypeMovKey.RECEIPT.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservChave(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeMov = TypeMovKey.RECEIPT.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                }
            )
        }

        ////////////////////////////////////////////////////////////////////

        /////////////////////////// Chave Equip //////////////////////////////////

        composable(
            CONTROLE_CHAVE_EQUIP_LIST_ROUTE
        ) {
            ControleChaveEquipListScreen(
                viewModel = koinViewModel<ControleChaveEquipListViewModel>(),
                onNavControleChaveEquipEditList = {
                    navActions.navigationToControleChaveEquipEditList()
                },
                onNavMatricColab = {
                    navActions.navigationToMatriColabChaveEquip(
                        flowApp = FlowApp.ADD.ordinal,
                        typeMov = TypeMovKey.REMOVE.ordinal,
                        id = it
                    )
                },
                onNavMenuApont = {
                    navActions.navigateToMenuApont()
                },
                onNavEquip = {
                    navActions.navigationToEquipChave(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
            )
        }

        composable(
            EQUIP_CHAVE_EQUIP_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            NroEquipChaveEquipScreen(
                viewModel = koinViewModel<NroEquipChaveEquipViewModel>(),
                onNavMatricColab = {
                    navActions.navigationToMatriColabChaveEquip(
                        flowApp = FlowApp.ADD.ordinal,
                        typeMov = TypeMovKey.RECEIPT.ordinal,
                        id = 0
                    )
                },
                onNavControleList = { navActions.navigationToControleChaveEquipList() },
                onNavDetalhe = {
                    navActions.navigationToDetalheChaveEquip(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                }
            )
        }

        composable(
            MATRIC_COLAB_CHAVE_EQUIP_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_MOV_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            MatricColabChaveEquipScreen(
                viewModel = koinViewModel<MatricColabChaveEquipViewModel>(),
                onNavEquip = {
                    navActions.navigationToEquipChave(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheChaveEquip(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavNomeColab = {
                    navActions.navigationToNomeColabChaveEquip(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeMov = entry.arguments?.getInt(TYPE_MOV_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!,
                        matricColab = it
                    )
                },
                onNavMovList = {
                    navActions.navigationToControleChaveEquipList()
                }
            )
        }

        composable(
            NOME_COLAB_CHAVE_EQUIP_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_MOV_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType },
                navArgument(MATRIC_COLAB_ARGS) { type = NavType.StringType }
            )
        ) { entry ->
            NomeColabChaveEquipScreen(
                viewModel = koinViewModel<NomeColabChaveEquipViewModel>(),
                onNavDetalhe = {
                    navActions.navigationToDetalheChaveEquip(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMatricColab = {
                    navActions.navigationToMatriColabChaveEquip(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeMov =  entry.arguments?.getInt(TYPE_MOV_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservChaveEquip(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeMov =  entry.arguments?.getInt(TYPE_MOV_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }

        composable(
            OBSERV_CHAVE_EQUIP_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_MOV_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            ObservChaveEquipScreen(
                viewModel = koinViewModel<ObservChaveEquipViewModel>(),
                onNavDetalhe = {
                    navActions.navigationToDetalheChaveEquip(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMatricColab = {
                    navActions.navigationToMatriColabChaveEquip(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeMov =  entry.arguments?.getInt(TYPE_MOV_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavControleList = { navActions.navigationToControleChaveEquipList() }
            )
        }

        composable(
            CONTROLE_CHAVE_EQUIP_EDIT_LIST_ROUTE
        ) {
            ControleChaveEquipEditListScreen(
                viewModel = koinViewModel<ControleChaveEquipEditListViewModel>(),
                onNavControleList = { navActions.navigationToControleChaveEquipList() },
                onNavDetalhe = {
                    navActions.navigationToDetalheChaveEquip(
                        id = it
                    )
                }
            )
        }

        composable(
            DETALHE_CHAVE_EQUIP_ROUTE,
            arguments = listOf(
                navArgument(ID_ARGS) { type = NavType.IntType },
            )
        ) { entry ->
            DetalheChaveEquipScreen(
                viewModel = koinViewModel<DetalheChaveEquipViewModel>(),
                onNavControleChaveEquipEditList = { navActions.navigationToControleChaveEquipEditList() },
                onNavMatricColab = {
                    navActions.navigationToMatriColabChaveEquip(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeMov = TypeMovKey.REMOVE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservChaveEquip(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeMov = TypeMovKey.REMOVE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                }
            )
        }

        ////////////////////////////////////////////////////////////////////

    }
}
