package br.com.usinasantafe.pcp.util.conHttp;

import br.com.usinasantafe.pcp.PCPContext;

public class UrlsConexaoHttp {

    public static String versao = "versao_" + PCPContext.versaoWS.replace(".", "_");

//    public static String url = "https://www.usinasantafe.com.br/pmmdev/view/";
    public static String url = "https://www.usinasantafe.com.br/pmmqa/view/";
//    public static String url = "https://www.usinasantafe.com.br/pmmprod/" + versao + "/view/";

    public static String localPSTEstatica = "br.com.usinasantafe.pmm.model.bean.estaticas.";
    public static String localUrl = "br.com.usinasantafe.pmm.util.conHttp.UrlsConexaoHttp";

    public static String AtividadeBean = url + "atividade.php";
    public static String BocalBean = url + "bocal.php";
    public static String ComponenteBean = url + "componente.php";
    public static String EquipSegBean = url + "equipseg.php";
    public static String FrenteBean = url + "frente.php";
    public static String FuncBean = url + "funcionario.php";
    public static String ItemCheckListBean = url + "itemchecklist.php";
    public static String ItemOSMecanBean = url + "itemosmecan.php";
    public static String LeiraBean = url + "leira.php";
    public static String MotoMecBean = url + "motomec.php";
    public static String OSBean = url + "os.php";
    public static String ParadaBean = url + "parada.php";
    public static String PressaoBocalBean = url + "pressaobocal.php";
    public static String ProdutoBean = url + "produto.php";
    public static String PropriedadeBean = url + "propriedade.php";
    public static String RAtivParadaBean = url + "rativparada.php";
    public static String RFuncaoAtivParBean = url + "rfuncaoativpar.php";
    public static String ROSAtivBean = url + "rosativ.php";
    public static String ServicoBean = url + "servico.php";
    public static String TurnoBean = url + "turno.php";

    public UrlsConexaoHttp() {
    }

    public String getsInsertCarreg() {
        return url + "inserircarreg.php";
    }

    public String getsInserirCheckList() {
        return url + "inserirchecklist.php";
    }

    public String getsInsertPreCEC() {
        return url + "inserirprecec.php";
    }

    public String getsInsertBolAbertoMMFert() {
        return url + "inserirbolabertommfert.php";
    }

    public String getsInsertBolFechadoMMFert() {
        return url + "inserirbolfechadommfert.php";
    }

    public String urlVerifica(String classe) {
        String retorno = "";
        if (classe.equals("Equip")) {
            retorno = url + "equip.php";
        } else if (classe.equals("OS")) {
            retorno = url + "pesqos.php";
        } else if (classe.equals("Atividade")) {
            retorno = url + "pesqativ.php";
        } else if (classe.equals("AtividadeECM")) {
            retorno = url + "pesqativecm.php";
        } else if (classe.equals("OSMecan")) {
            retorno = url + "pesqosmecan.php";
        } else if (classe.equals("AtualParada")) {
            retorno = url + "atualparada.php";
        } else if (classe.equals("Atualiza")) {
            retorno = url + "atualaplic.php";
        } else if (classe.equals("Operador")) {
            retorno = url + "motorista.php";
        } else if (classe.equals("Turno")) {
            retorno = url + "turno.php";
        } else if (classe.equals("EquipSeg")) {
            retorno = url + "equipseg.php";
        } else if (classe.equals("CheckList")) {
            retorno = url + "atualchecklist.php";
        } else if (classe.equals("Pneu")) {
            retorno = url + "pesqpneu.php";
        } else if (classe.equals("Informativo")) {
            retorno = url + "informativo.php";
        } else if(classe.equals("OrdCarreg")){
            retorno = url + "retcarreg.php";
        } else if (classe.equals("CEC")) {
            retorno = url + "retcec.php";
        }
        return retorno;
    }

}
