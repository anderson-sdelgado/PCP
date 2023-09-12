package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;

public class TelaInicialActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        pcpContext = (PCPContext) getApplication();
        LogProcessoDAO.getInstance().insertLogProcesso("customHandler.postDelayed(excluirBDThread, 0);", getLocalClassName());
        customHandler.postDelayed(excluirBDThread, 0);

    }

    private Runnable excluirBDThread = new Runnable() {

        public void run() {
            LogProcessoDAO.getInstance().insertLogProcesso("private Runnable excluirBDThread = new Runnable() {\n" +
                    "        public void run() {\n" +
                    "            clearBD();\n" +
                    "            goMenuInicial();", getLocalClassName());
            clearBD();
            if(EnvioDadosServ.getInstance().verifDadosEnvio()){
                LogProcessoDAO.getInstance().insertLogProcesso("EnvioDadosServ.getInstance().verifDadosEnvio()", getLocalClassName());
                if(connectNetwork){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(connectNetwork){\n" +
                            "EnvioDadosServ.getInstance().envioDados()", getLocalClassName());
                    EnvioDadosServ.getInstance().envioDados(getLocalClassName());
                }
                else{
                    LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                            "                EnvioDadosServ.status = 1;", getLocalClassName());
                    EnvioDadosServ.status = 1;
                }
            }
            else{
                LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                        "            EnvioDadosServ.status = 3;", getLocalClassName());
                EnvioDadosServ.status = 3;
            }

            if(pcpContext.getConfigCTR().hasElemConfig()){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().hasElemConfig()){", getLocalClassName());
                if((pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() > 0L) && (pcpContext.getConfigCTR().getConfig().getIdLocalConfig() > 0L)){
                    LogProcessoDAO.getInstance().insertLogProcesso("if((pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() > 0L) && (pcpContext.getConfigCTR().getConfig().getIdLocalConfig() > 0L)){\n" +
                            "                    goMenuApont();", getLocalClassName());
                    goMenuApont();
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    goMenuInicial();", getLocalClassName());
                    goMenuInicial();
                }
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                goMenuInicial();", getLocalClassName());
                goMenuInicial();
            }
        }

    };

    public void clearBD() {
        LogProcessoDAO.getInstance().insertLogProcesso("pcpContext.getConfigCTR().deleteLogs();", getLocalClassName());
        pcpContext.getConfigCTR().deleteLogs();
    }

    public void goMenuInicial(){
        LogProcessoDAO.getInstance().insertLogProcesso("public void goMenuInicial(){\n" +
                "        Intent it = new Intent(TelaInicialActivity.this, MenuInicialActivity.class);", getLocalClassName());
        Intent it = new Intent(TelaInicialActivity.this, MenuInicialActivity.class);
        startActivity(it);
        finish();
    }

    public void goMenuApont(){
        LogProcessoDAO.getInstance().insertLogProcesso("public void goMenuApont(){\n" +
                "        Intent it = new Intent(TelaInicialActivity.this, ListaMenuApontActivity.class);", getLocalClassName());
        Intent it = new Intent(TelaInicialActivity.this, ListaMenuApontActivity.class);
        startActivity(it);
        finish();
    }

    public void onBackPressed()  {
    }

}