package br.com.usinasantafe.pcp.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import br.com.usinasantafe.pcp.control.MovEquipProprioCTR;
import br.com.usinasantafe.pcp.control.MovEquipResidenciaCTR;
import br.com.usinasantafe.pcp.control.MovEquipVisitTercCTR;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcp.util.conHttp.PostCadGenerico;
import br.com.usinasantafe.pcp.util.conHttp.UrlsConexaoHttp;
import br.com.usinasantafe.pcp.util.retrofit.MovEquipProprioEnvio;
import br.com.usinasantafe.pcp.util.retrofit.MovEquipResidenciaEnvio;
import br.com.usinasantafe.pcp.util.retrofit.MovEquipVisitTercEnvio;
import br.com.usinasantafe.pcp.view.ActivityGeneric;

public class EnvioDadosServ {

    private static EnvioDadosServ instance = null;
    private UrlsConexaoHttp urlsConexaoHttp;
    public static int status; //1 - Existe Dados para Enviar; 2 - Enviado; 3 - Todos os Dados Foram Enviados;

    public EnvioDadosServ() {
        urlsConexaoHttp = new UrlsConexaoHttp();
    }

    public static EnvioDadosServ getInstance() {
        if (instance == null) {
            instance = new EnvioDadosServ();
        }
        return instance;
    }

    ////////////////////////////////// ENVIAR DADOS ///////////////////////////////////////////////

    public void enviarMovEquipProprio(String activity) {
        LogProcessoDAO.getInstance().insertLogProcesso("public void enviarMovEquipProprio(String activity) {\n" +
                "        MovVeicProprioCTR movVeicProprioCTR = new MovVeicProprioCTR();\n" +
                "        MovEquipProprioEnvio movEquipProprioEnvio = new MovEquipProprioEnvio();\n" +
                "        movEquipProprioEnvio.envioDadosMovEquipProprio(movVeicProprioCTR.dadosEnvioMovEquipProprio(), activity);", activity);
        MovEquipProprioCTR movEquipProprioCTR = new MovEquipProprioCTR();
        MovEquipProprioEnvio movEquipProprioEnvio = new MovEquipProprioEnvio();
        movEquipProprioEnvio.envioDadosMovEquipProprio(movEquipProprioCTR.dadosEnvioMovEquipProprio(), activity);
    }

    public void enviarMovVeicVisitTerc(String activity) {
        LogProcessoDAO.getInstance().insertLogProcesso("public void enviarMovVeicVisitTerc(String activity) {\n" +
            "        MovEquipVisitTercCTR movEquipVisitTercCTR = new MovEquipVisitTercCTR();\n" +
            "        MovEquipVisitTercEnvio movEquipVisitTercEnvio = new MovEquipVisitTercEnvio();\n" +
            "        movEquipVisitTercEnvio.envioDadosMovEquipVisitTerc(movEquipVisitTercCTR.dadosEnvioMovEquipVisitTerc(), activity);", activity);
        MovEquipVisitTercCTR movEquipVisitTercCTR = new MovEquipVisitTercCTR();
        MovEquipVisitTercEnvio movEquipVisitTercEnvio = new MovEquipVisitTercEnvio();
        movEquipVisitTercEnvio.envioDadosMovEquipVisitTerc(movEquipVisitTercCTR.dadosEnvioMovEquipVisitTerc(), activity);
    }

    public void enviarMovVeicResidencia(String activity) {LogProcessoDAO.getInstance().insertLogProcesso("public void enviarMovVeicResidencia(String activity) {\n" +
            "        MovEquipResidenciaCTR movEquipResidenciaCTR = new MovEquipResidenciaCTR();\n" +
            "        MovEquipResidenciaEnvio movEquipResidenciaEnvio = new MovEquipResidenciaEnvio();\n" +
            "        movEquipResidenciaEnvio.envioDadosMovEquipResidencia(movEquipResidenciaCTR.dadosEnvioMovEquipResidencia(), activity);", activity);
        MovEquipResidenciaCTR movEquipResidenciaCTR = new MovEquipResidenciaCTR();
        MovEquipResidenciaEnvio movEquipResidenciaEnvio = new MovEquipResidenciaEnvio();
        movEquipResidenciaEnvio.envioDadosMovEquipResidencia(movEquipResidenciaCTR.dadosEnvioMovEquipResidencia(), activity);
    }

    public void envio(String url, String dados, String activity){

        String[] strings = {url, activity};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        Log.i("PCP", "URL = " + url + " - Dados de Envio = " + dados);
        LogProcessoDAO.getInstance().insertLogProcesso("postCadGenerico.execute('" + url + "'); - Dados de Envio = " + dados, activity);
        PostCadGenerico postCadGenerico = new PostCadGenerico();
        postCadGenerico.setParametrosPost(parametrosPost);
        postCadGenerico.execute(strings);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////VERIFICAÇÃO DE DADOS/////////////////////////////////////////

    private Boolean verifMovEquipProprioEnviar() {
        MovEquipProprioCTR movEquipProprioCTR = new MovEquipProprioCTR();
        return movEquipProprioCTR.verEnvioMovEquipProprioEnviar();
    }

    private Boolean verifMovEquipVisitTercEnviar() {
        MovEquipVisitTercCTR movEquipVisitTercCTR = new MovEquipVisitTercCTR();
        return movEquipVisitTercCTR.verEnvioMovEquipVisitTercEnviar();
    }

    private Boolean verifMovEquipResidenciaEnviar() {
        MovEquipResidenciaCTR movEquipResidenciaCTR = new MovEquipResidenciaCTR();
        return movEquipResidenciaCTR.verEnvioMovEquipResidenciaEnviar();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////MECANISMO DE ENVIO/////////////////////////////////////////

    public void envioDados(String activity) {
        LogProcessoDAO.getInstance().insertLogProcesso("public void envioDados(String activity) {\n" +
                "        status = 1;", activity);
        status = 1;
        if(ActivityGeneric.connectNetwork) {
            LogProcessoDAO.getInstance().insertLogProcesso("if(ActivityGeneric.connectNetwork) {\n" +
                    "            status = 2;", activity);
            status = 2;
            if (verifMovEquipProprioEnviar()) {
                LogProcessoDAO.getInstance().insertLogProcesso("if (verifMovEquipProprioFech()) {\n" +
                        "                enviarMovEquipProprio(activity);", activity);
                enviarMovEquipProprio(activity);
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {", activity);
                if (verifMovEquipVisitTercEnviar()) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (verifMovEquipVisitTercFech()) {\n" +
                            "                    enviarMovVeicVisitTerc(activity);", activity);
                    enviarMovVeicVisitTerc(activity);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {", activity);
                    if (verifMovEquipResidenciaEnviar()) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if (verifMovEquipResidenciaFech()) {\n" +
                                "                        enviarMovVeicResidencia(activity);", activity);
                        enviarMovVeicResidencia(activity);
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                                status = 3;", activity);
                        status = 3;
                    }
                }
            }
        }
    }

    public boolean verifDadosEnvio() {
        if ((!verifMovEquipProprioEnviar())
                && (!verifMovEquipVisitTercEnviar())
                && (!verifMovEquipResidenciaEnviar())){
            return false;
        } else {
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////MECANISMO DE ENVIO/////////////////////////////////////////

    public void recDados(String result, String activity){
//        LogProcessoDAO.getInstance().insertLogProcesso("public void recDados(String " + result + ", String activity){", activity);
//        if (result.trim().startsWith("MOVEQUIPPROPRIO")) {
//            LogProcessoDAO.getInstance().insertLogProcesso("if (result.trim().startsWith(\"MOVEQUIPPROPRIO\")) {\n" +
//                    "            MovVeicProprioCTR movVeicProprioCTR = new MovVeicProprioCTR();\n" +
//                    "            movVeicProprioCTR.updateMovEquipProprio(result, activity);", activity);
//            MovEquipProprioCTR movEquipProprioCTR = new MovEquipProprioCTR();
//            movVeicProprioCTR.updateMovEquipProprio(result, activity);
//        }
//        else if (result.trim().startsWith("MOVEQUIPVISITTERC")) {
//            LogProcessoDAO.getInstance().insertLogProcesso("else if (result.trim().startsWith(\"MOVEQUIPVISITTERC\")) {\n" +
//                    "            MovVeicVisitTercCTR movVeicVisitTercCTR = new MovVeicVisitTercCTR();\n" +
//                    "            movVeicVisitTercCTR.updateMovEquipVisitTerc(result, activity);", activity);
//            MovEquipVisitTercCTR movEquipVisitTercCTR = new MovEquipVisitTercCTR();
//            movEquipVisitTercCTR.updateMovEquipVisitTerc(result, activity);
//        }
//        else if (result.trim().startsWith("MOVEQUIPRESIDENCIA")) {
//            LogProcessoDAO.getInstance().insertLogProcesso("else if (result.trim().startsWith(\"MOVEQUIPRESIDENCIA\")) {\n" +
//                    "            MovVeicResidenciaCTR movVeicResidenciaCTR = new MovVeicResidenciaCTR();\n" +
//                    "            movVeicResidenciaCTR.updateMovEquipResidencia(result, activity);", activity);
//            MovEquipResidenciaCTR movEquipResidenciaCTR = new MovEquipResidenciaCTR();
//            movEquipResidenciaCTR.updateMovEquipResidencia(result, activity);
//        }
//        else {
//            LogProcessoDAO.getInstance().insertLogProcesso("else {\n" +
//                    "            status = 1;", activity);
//            status = 1;
//            LogErroDAO.getInstance().insertLogErro(result);
//        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

}