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
import br.com.usinasantafe.pcp.presenter.view.chave.chavelist.ChaveListScreen
import br.com.usinasantafe.pcp.presenter.view.chave.controleeditlist.ControleChaveEditListScreen
import br.com.usinasantafe.pcp.presenter.view.chave.controlelist.ControleChaveListScreen
import br.com.usinasantafe.pcp.presenter.view.chave.detalhe.DetalheChaveScreen
import br.com.usinasantafe.pcp.presenter.view.chave.matriccolab.MatricColabChaveScreen
import br.com.usinasantafe.pcp.presenter.view.chave.nomecolab.NomeColabChaveScreen
import br.com.usinasantafe.pcp.presenter.view.chave.observ.ObservChaveScreen
import br.com.usinasantafe.pcp.presenter.view.chaveEquip.controleeditlist.ControleChaveEquipEditListScreen
import br.com.usinasantafe.pcp.presenter.view.chaveEquip.controlelist.ControleChaveEquipListScreen
import br.com.usinasantafe.pcp.presenter.view.chaveEquip.detalhe.DetalheChaveEquipScreen
import br.com.usinasantafe.pcp.presenter.view.chaveEquip.matriccolab.MatricColabChaveEquipScreen
import br.com.usinasantafe.pcp.presenter.view.chaveEquip.nomecolab.NomeColabChaveEquipScreen
import br.com.usinasantafe.pcp.presenter.view.chaveEquip.nroequip.NroEquipChaveEquipScreen
import br.com.usinasantafe.pcp.presenter.view.chaveEquip.observ.ObservChaveEquipScreen
import br.com.usinasantafe.pcp.presenter.view.configuration.config.ConfigScreen
import br.com.usinasantafe.pcp.presenter.view.configuration.menuinicial.MenuInicialScreen
import br.com.usinasantafe.pcp.presenter.view.configuration.senha.SenhaScreen
import br.com.usinasantafe.pcp.presenter.view.initial.local.LocalScreen
import br.com.usinasantafe.pcp.presenter.view.initial.matricvigia.MatricVigiaScreen
import br.com.usinasantafe.pcp.presenter.view.initial.menuapont.MenuApontScreen
import br.com.usinasantafe.pcp.presenter.view.initial.nomevigia.NomeVigiaScreen
import br.com.usinasantafe.pcp.presenter.view.splash.SplashScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.destino.DestinoProprioScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.detalhe.DetalheMovProprioScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.equipseglist.EquipSegListScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.matriccolab.MatricColabScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.movlist.MovEquipProprioListScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.nomecolab.NomeColabScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.notafiscal.NotaFiscalProprioScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.nroequip.NroEquipScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.observ.ObservProprioScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoProprio.passaglist.PassagColabListScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.detalhe.DetalheResidenciaScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.motorista.MotoristaResidenciaScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.moveditlist.MovEquipResidenciaEditListScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.movlist.MovEquipResidenciaListScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.observ.ObservResidenciaScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.placa.PlacaResidenciaScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoResidencia.veiculo.VeiculoResidenciaScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.cpf.CpfVisitTercScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.destino.DestinoVisitTercScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.detalhe.DetalheVisitTercScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.moveditlist.MovEquipVisitTercEditListScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.movlist.MovEquipVisitTercListScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.nome.NomeVisitTercScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.observ.ObservVisitTercScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.passaglist.PassagVisitTercListScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.placa.PlacaVisitTercScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.tipo.TipoVisitTercScreen
import br.com.usinasantafe.pcp.presenter.view.veiculoVisitTerc.veiculo.VeiculoVisitTercScreen
import br.com.usinasantafe.pcp.lib.FlowApp
import br.com.usinasantafe.pcp.lib.TypeEquip
import br.com.usinasantafe.pcp.lib.TypeMovEquip
import br.com.usinasantafe.pcp.lib.TypeMovKey
import br.com.usinasantafe.pcp.lib.TypeOcupante

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = SPLASH_ROUTE,
    navActions: NavigationActions = remember(navHostController) {
        NavigationActions(navHostController)
    }
) {
    NavHost(navController = navHostController, startDestination = startDestination) {

        composable(SPLASH_ROUTE) {
            SplashScreen(
                onNavMenuInicial = { navActions.navigateToMenuInicial() },
                onNavMenuApont = { navActions.navigateToMenuApont() }
            )
        }

        ///////////////////////// Config //////////////////////////////////

        composable(MENU_INICIAL_ROUTE) {
            MenuInicialScreen(
                onNavMatricVigia = { navActions.navigateToMatricVigia() },
                onNavSenha = { navActions.navigateToSenha() }
            )
        }
        composable(SENHA_ROUTE) {
            SenhaScreen(
                onNavMenuInicial = { navActions.navigateToMenuInicial() },
                onNavConfig = { navActions.navigateToConfig() }
            )
        }
        composable(CONFIG_ROUTE) {
            ConfigScreen(
                onNavMenuInicial = { navActions.navigateToMenuInicial() }
            )
        }

        ////////////////////////////////////////////////////////////////////

        ///////////////////////// Initial //////////////////////////////////

        composable(MATRIC_VIGIA_ROUTE) {
            MatricVigiaScreen(
                onNavMenuInicial = { navActions.navigateToMenuInicial() },
                onNavNomeVigia = { navActions.navigateToNomeVigia() }
            )
        }
        composable(NOME_VIGIA_ROUTE) {
            NomeVigiaScreen(
                onNavMatricVigia = { navActions.navigateToMatricVigia() },
                onNavLocal = { navActions.navigateToLocal() }
            )
        }
        composable(LOCAL_ROUTE) {
            LocalScreen(
                onNavNomeVigia = { navActions.navigateToNomeVigia() },
                onNavMenuApont = { navActions.navigateToMenuApont() }
            )
        }
        composable(MENU_APONT_ROUTE) {
            MenuApontScreen(
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
