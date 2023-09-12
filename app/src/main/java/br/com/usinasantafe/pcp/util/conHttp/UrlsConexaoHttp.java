package br.com.usinasantafe.pcp.util.conHttp;

import br.com.usinasantafe.pcp.PCPContext;

public class UrlsConexaoHttp {

    public static String versao = "versao_" + PCPContext.versaoWS.replace(".", "_");

    public static String url = "https://www.usinasantafe.com.br/pcpdev/view/";
//    public static String url = "https://www.usinasantafe.com.br/pcpqa/view/";
//    public static String url = "https://www.usinasantafe.com.br/pcpprod/" + versao + "/view/";

    public static String localPSTEstatica = "br.com.usinasantafe.pcp.model.bean.estaticas.";
    public static String localUrl = "br.com.usinasantafe.pcp.util.conHttp.UrlsConexaoHttp";

    public static String ColabBean = url + "colab.php";
    public static String EquipBean = url + "equip.php";
    public static String LocalBean = url + "local.php";
    public static String TerceiroBean = url + "terceiro.php";
    public static String VisitanteBean = url + "visitante.php";

    public UrlsConexaoHttp() {
    }

    public String urlVerifica(String classe) {
        String retorno = "";
        if (classe.equals("AtualAplic")) {
            retorno = url + "atualaplic.php";
        }
        return retorno;
    }

}
