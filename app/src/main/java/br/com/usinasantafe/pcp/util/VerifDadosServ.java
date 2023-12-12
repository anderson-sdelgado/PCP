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
    private PostVerGenerico postVerGenerico;
    public static int status;
    private String senha;

    public VerifDadosServ() {
    }

    public static VerifDadosServ getInstance() {
        if (instance == null)
            instance = new VerifDadosServ();
        return instance;
    }

    public void manipularDadosHttp(String result) {

        ConfigCTR configCTR = new ConfigCTR();
        configCTR.recToken(result.trim(), this.senha, this.telaAtual, this.telaProx, this.progressDialog);

    }

    public void salvarToken(String senha, String dados, Context telaAtual, Class telaProx, ProgressDialog progressDialog, String activity) {

        this.urlsConexaoHttp = new UrlsConexaoHttp();
        this.telaAtual = telaAtual;
        this.telaProx = telaProx;
        this.progressDialog = progressDialog;
        this.dados = dados;
        this.senha = senha;
        this.classe = "Token";

        envioVerif(activity);

    }

    public void envioVerif(String activity) {

        status = 2;
        this.urlsConexaoHttp = new UrlsConexaoHttp();
        String[] url = {urlsConexaoHttp.urlVerifica(classe), activity};
        Map<String, Object> parametrosPost = new HashMap<>();
        parametrosPost.put("dado", this.dados);

        Log.i("PMM", "postVerGenerico.execute('" + urlsConexaoHttp.urlVerifica(classe) + "'); - Dados de Envio = " + this.dados);
        LogProcessoDAO.getInstance().insertLogProcesso("postVerGenerico.execute('" + urlsConexaoHttp.urlVerifica(classe) + "'); - Dados de Envio = " + this.dados, activity);
        postVerGenerico = new PostVerGenerico();
        postVerGenerico.setParametrosPost(parametrosPost);
        postVerGenerico.execute(url);

    }

}
