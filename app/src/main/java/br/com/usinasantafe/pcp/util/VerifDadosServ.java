package br.com.usinasantafe.pcp.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import br.com.usinasantafe.pcp.control.ConfigCTR;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcp.util.conHttp.PostVerGenerico;
import br.com.usinasantafe.pcp.util.conHttp.UrlsConexaoHttp;
import br.com.usinasantafe.pcp.view.TelaInicialActivity;

/**
 * Created by anderson on 16/11/2015.
 */
public class VerifDadosServ {

    private static VerifDadosServ instance = null;
    private UrlsConexaoHttp urlsConexaoHttp;
    private Context telaAtual;
    private Class telaProx;
    private ProgressDialog progressDialog;
    private String dados;
    private String classe;
    private TelaInicialActivity telaInicialActivity;
    private PostVerGenerico postVerGenerico;
    public static int status;
    private int tipo;

    public VerifDadosServ() {
    }

    public static VerifDadosServ getInstance() {
        if (instance == null)
            instance = new VerifDadosServ();
        return instance;
    }

    public void manipularDadosHttp(String result, String activity) {

        ConfigCTR configCTR = new ConfigCTR();
        CheckListCTR checkListCTR = new CheckListCTR();
        CompostoCTR compostoCTR = new CompostoCTR();
        CECCTR cecCTR = new CECCTR();
        MecanicoCTR mecanicoCTR = new MecanicoCTR();
        MotoMecFertCTR motoMecFertCTR = new MotoMecFertCTR();
        LogProcessoDAO.getInstance().insertLogProcesso("public void manipularDadosHttp(String result) {", activity);
        if (this.classe.equals("Equip")) {
            LogProcessoDAO.getInstance().insertLogProcesso("if (this.tipo.equals(\"Equip\")) {\n" +
                    "            configCTR.receberVerifEquip(" + result + ");", activity);
            configCTR.receberVerifEquip(result, this.tipo);
        } else if (this.classe.equals("OS")) {
            LogProcessoDAO.getInstance().insertLogProcesso("} else if (this.tipo.equals(\"OS\")) {\n" +
                    "            configCTR.receberVerifOS(" + result + ");", activity);
            configCTR.receberVerifOS(result);
        } else if (this.classe.equals("Atividade")) {
            LogProcessoDAO.getInstance().insertLogProcesso("} else if (this.tipo.equals(\"Atividade\")) {\n" +
                    "            configCTR.receberVerifAtiv(" + result + ");", activity);
            configCTR.receberVerifAtiv(result);
        } else if (this.classe.equals("AtividadeECM")) {
            LogProcessoDAO.getInstance().insertLogProcesso("} else if (this.tipo.equals(\"AtividadeECM\")) {\n" +
                    "            configCTR.receberVerifAtivECM(" + result + ");", activity);
            configCTR.receberVerifAtivECM(result);
        } else if (this.classe.equals("Atualiza")) {
            LogProcessoDAO.getInstance().insertLogProcesso("} else if (this.tipo.equals(\"Atualiza\")) {\n" +
                    "            configCTR.recAtual(result.trim());\n" +
                    "            status = 3;", activity);
            configCTR.recAtual(result.trim());
            status = 3;
            LogProcessoDAO.getInstance().insertLogProcesso("this.telaInicialActivity.goMenuInicial();", activity);
            this.telaInicialActivity.goMenuInicial();
        } else if (this.classe.equals("CheckList")) {
            LogProcessoDAO.getInstance().insertLogProcesso("} else if (this.tipo.equals(\"CheckList\")) {\n" +
                    "            checkListCTR.receberVerifCheckList(" + result + ");", activity);
            checkListCTR.receberVerifCheckList(result);
        } else if(this.classe.equals("OrdCarreg")) {
            LogProcessoDAO.getInstance().insertLogProcesso("} else if(this.tipo.equals(\"OrdCarreg\")) {\n" +
                    "            compostoCTR.receberVerifOrdCarreg(" + result + ");", activity);
            compostoCTR.receberVerifOrdCarreg(result, activity);
        } else if (this.classe.equals("CEC")) {
            LogProcessoDAO.getInstance().insertLogProcesso("} else if (this.tipo.equals(\"CEC\")) {\n" +
                    "            cecCTR.receberVerifCEC(" + result + ");", activity);
            cecCTR.receberVerifCEC(result);
        } else if (this.classe.equals("OSMecan")) {
            LogProcessoDAO.getInstance().insertLogProcesso("} else if (this.tipo.equals(\"OS\")) {\n" +
                    "            configCTR.receberVerifOS(" + result + ");", activity);
            mecanicoCTR.receberVerifOSMecan(result);
        } else if (this.classe.equals("Pneu")) {
            LogProcessoDAO.getInstance().insertLogProcesso("} else if (this.tipo.equals(\"Pneu\")) {\n" +
                    "            motoMecFertCTR.receberVerifPneu(" + result + ");", activity);
            motoMecFertCTR.receberVerifPneu(result);
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            status = 1;", activity);
            status = 1;
        }

    }

    public void verifAtualAplic(String dados, TelaInicialActivity telaInicialActivity, String activity) {

        urlsConexaoHttp = new UrlsConexaoHttp();
        this.classe = "Atualiza";
        this.dados = dados;
        this.telaInicialActivity = telaInicialActivity;

        envioVerif(activity);

    }

    public void verifDados(String dados, String classe, Context telaAtual, Class telaProx, ProgressDialog progressDialog, String activity) {

        this.urlsConexaoHttp = new UrlsConexaoHttp();
        this.telaAtual = telaAtual;
        this.telaProx = telaProx;
        this.progressDialog = progressDialog;
        this.classe = classe;
        this.dados = dados;

        envioVerif(activity);

    }


    public void verifDados(String dados, String classe, Context telaAtual, Class telaProx, String activity) {

        this.urlsConexaoHttp = new UrlsConexaoHttp();
        this.telaAtual = telaAtual;
        this.telaProx = telaProx;
        this.classe = classe;
        this.dados = dados;

        envioVerif(activity);

    }

    public void verifDados(String dados, String classe, Context telaAtual, Class telaProx, ProgressDialog progressDialog, String activity, int tipo) {

        this.urlsConexaoHttp = new UrlsConexaoHttp();
        this.telaAtual = telaAtual;
        this.telaProx = telaProx;
        this.progressDialog = progressDialog;
        this.classe = classe;
        this.dados = dados;
        this.tipo = tipo;

        envioVerif(activity);

    }

    public void envioVerif(String activity) {

        status = 2;
        this.urlsConexaoHttp = new UrlsConexaoHttp();
        String[] url = {urlsConexaoHttp.urlVerifica(classe), activity};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", this.dados);

        Log.i("PMM", "postVerGenerico.execute('" + urlsConexaoHttp.urlVerifica(classe) + "'); - Dados de Envio = " + this.dados);
        LogProcessoDAO.getInstance().insertLogProcesso("postVerGenerico.execute('" + urlsConexaoHttp.urlVerifica(classe) + "'); - Dados de Envio = " + this.dados, activity);
        postVerGenerico = new PostVerGenerico();
        postVerGenerico.setParametrosPost(parametrosPost);
        postVerGenerico.execute(url);

    }

    public void cancel() {
        status = 3;
        if (postVerGenerico.getStatus() == AsyncTask.Status.RUNNING) {
            postVerGenerico.cancel(true);
        }
    }

    public void pulaTela(){
        if(status < 3){
            status = 3;
            this.progressDialog.dismiss();
            Intent it = new Intent(telaAtual, telaProx);
            telaAtual.startActivity(it);
        }
    }

    public void atualTodosDados(){
        AtualDadosServ.getInstance().atualTodasTabBD(telaAtual, progressDialog, "MenuInicialActivity");
    }

    public void msg(String texto){
        if(status < 3){
            status = 3;
            this.progressDialog.dismiss();
            AlertDialog.Builder alerta = new AlertDialog.Builder(telaAtual);
            alerta.setTitle("ATENÇÃO");
            alerta.setMessage(texto);
            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alerta.show();
        }
    }

    public void pulaTelaSemBarra(){
        if(status < 3){
            status = 3;
            Intent it = new Intent(telaAtual, telaProx);
            telaAtual.startActivity(it);
        }
    }

    public void pulaTelaSemBarra(Class telaProx){
        if(status < 3){
            status = 3;
            Intent it = new Intent(telaAtual, telaProx);
            telaAtual.startActivity(it);
        }
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
