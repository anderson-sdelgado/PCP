package br.com.usinasantafe.pcp.util.conHttp;

import br.com.usinasantafe.pcp.PCPContext;

public class UrlsConexaoHttp {

    public static String versao = "versao_" + PCPContext.versaoWS.replace(".", "_");

//    public static String url = "https://www.usinasantafe.com.br/pcpdev/view/";
    public static String url = "https://www.usinasantafe.com.br/pmmqa/view/";
//    public static String url = "https://www.usinasantafe.com.br/pmmprod/" + versao + "/view/";

    public static String localPSTEstatica = "br.com.usinasantafe.pcp.model.bean.estaticas.";
    public static String localUrl = "br.com.usinasantafe.pcp.util.conHttp.UrlsConexaoHttp";

    public static String ColabBean = url + "colab.php";
    public static String EquipBean = url + "equip.php";
    public static String TerceiroBean = url + "terceiro.php";
    public static String VisitanteBean = url + "visitante.php";

    public UrlsConexaoHttp() {
    }

    public String getsInserirMovEquipProprio() {
        return url + "inserirmovequipproprio.php";
    }

    public String getsInserirMovEquipVisitTerc() {
        return url + "inserirmovequipvisitterc.php";
    }

    public String urlVerifica(String classe) {
        String retorno = "";
        if (classe.equals("Equip")) {
            retorno = url + "equip.php";
        }
        return retorno;
    }

}
