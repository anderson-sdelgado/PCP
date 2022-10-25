package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

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
            goMenuInicial();
        }

    };

    public void clearBD() {
        LogProcessoDAO.getInstance().insertLogProcesso("piaContext.getInfestacaoCTR().deleteCabecEnviado();\n" +
                "        piaContext.getConfigCTR().deleteLogs();", getLocalClassName());
//        piaContext.getInfestacaoCTR().deleteCabecEnviado();
        pcpContext.getConfigCTR().deleteLogs();
    }

    public void goMenuInicial(){
        LogProcessoDAO.getInstance().insertLogProcesso("public void goMenuInicial(){\n" +
                "        Intent it = new Intent(TelaInicialActivity.this, MenuInicialActivity.class);", getLocalClassName());
        Intent it = new Intent(TelaInicialActivity.this, MenuInicialActivity.class);
        startActivity(it);
        finish();
    }

    public void onBackPressed()  {
    }

}