package br.com.usinasantafe.pcp.util;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import br.com.usinasantafe.pcp.control.MovVeicProprioCTR;
import br.com.usinasantafe.pcp.control.MovVeicVisitTercCTR;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcp.util.conHttp.PostCadGenerico;
import br.com.usinasantafe.pcp.util.conHttp.UrlsConexaoHttp;
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
        LogProcessoDAO.getInstance().insertLogProcesso("MovimentacaoVeicProprioCTR movimentacaoVeicProprioCTR = new MovimentacaoVeicProprioCTR();\n" +
                "        envio(urlsConexaoHttp.getsInserirMovEquipProprio(), movimentacaoVeicProprioCTR.dadosEnvioMovEquipProprio(), activity);", activity);
        MovVeicProprioCTR movVeicProprioCTR = new MovVeicProprioCTR();
        envio(urlsConexaoHttp.getsInserirMovEquipProprio(), movVeicProprioCTR.dadosEnvioMovEquipProprio(), activity);
    }

    public void enviarMovVeicVisitTerc(String activity) {
        LogProcessoDAO.getInstance().insertLogProcesso("MovVeicVisitTercCTR movVeicVisitTercCTR = new MovVeicVisitTercCTR();\n" +
                "        envio(urlsConexaoHttp.getsInserirMovEquipVisitTerc(), movVeicVisitTercCTR.dadosEnvioMovEquipVisitTerc(), activity);", activity);
        MovVeicVisitTercCTR movVeicVisitTercCTR = new MovVeicVisitTercCTR();
        envio(urlsConexaoHttp.getsInserirMovEquipVisitTerc(), movVeicVisitTercCTR.dadosEnvioMovEquipVisitTerc(), activity);
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

    public Boolean verifMovEquipProprioFech() {
        MovVeicProprioCTR movVeicProprioCTR = new MovVeicProprioCTR();
        return movVeicProprioCTR.verEnvioMovEquipProprioFech();
    }

    public Boolean verifMovEquipVisitTercFech() {
        MovVeicVisitTercCTR movVeicVisitTercCTR = new MovVeicVisitTercCTR();
        return movVeicVisitTercCTR.verEnvioMovEquipVisitTercFech();
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
            if (verifMovEquipProprioFech()) {
                LogProcessoDAO.getInstance().insertLogProcesso("if (verifMovEquipProprioFech()) {\n" +
                        "                enviarMovEquipProprio(activity);", activity);
                enviarMovEquipProprio(activity);
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {", activity);
                if (verifMovEquipVisitTercFech()) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (verifMovEquipVisitTercFech()) {\n" +
                            "                    enviarMovVeicVisitTerc(activity);", activity);
                    enviarMovVeicVisitTerc(activity);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                                status = 3;", activity);
                    status = 3;
                }
            }
        }
    }

    public boolean verifDadosEnvio() {
        if ((!verifMovEquipProprioFech())
                && (!verifMovEquipVisitTercFech())){
            return false;
        } else {
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////MECANISMO DE ENVIO/////////////////////////////////////////

    public void recDados(String result, String activity){
        LogProcessoDAO.getInstance().insertLogProcesso("public void recDados(String " + result + ", String activity){", activity);
        if (result.trim().startsWith("MOVEQUIPPROPRIO")) {
            LogProcessoDAO.getInstance().insertLogProcesso("else if (result.trim().startsWith(\"BOLABERTOMM\")) {\n" +
                    "            MotoMecFertCTR motoMecFertCTR = new MotoMecFertCTR();\n" +
                    "motoMecFertCTR.updBolAberto(result)", activity);
            MovVeicProprioCTR movVeicProprioCTR = new MovVeicProprioCTR();
            movVeicProprioCTR.updateMovEquipProprioFechado(result, activity);
        }
        else if (result.trim().startsWith("BOLFECHADOMM")) {
            LogProcessoDAO.getInstance().insertLogProcesso("else if (result.trim().startsWith(\"BOLFECHADOMM\")) {\n" +
                    "            MotoMecFertCTR motoMecFertCTR = new MotoMecFertCTR();\n" +
                    "motoMecFertCTR.updateBolFechado(result)", activity);
            MovVeicVisitTercCTR movVeicVisitTercCTR = new MovVeicVisitTercCTR();
            movVeicVisitTercCTR.updateMovEquipVisitFechado(result, activity);
        }
        else {
            LogProcessoDAO.getInstance().insertLogProcesso("else {\n" +
                    "            status = 1;", activity);
            status = 1;
            LogErroDAO.getInstance().insertLogErro(result);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

}