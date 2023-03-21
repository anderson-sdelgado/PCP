package br.com.usinasantafe.pcp.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import br.com.usinasantafe.pcp.BuildConfig;
import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class MenuInicialActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private TextView textViewProcesso;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        TextView textViewPrincipal = findViewById(R.id.textViewPrincipal);
        textViewPrincipal.setText("PRINCIPAL - V " + BuildConfig.VERSION_NAME);

        pcpContext = (PCPContext) getApplication();

        textViewProcesso = findViewById(R.id.textViewProcesso);

        LogProcessoDAO.getInstance().insertLogProcesso("customHandler.postDelayed(updateTimerThread, 0);", getLocalClassName());
        customHandler.postDelayed(updateTimerThread, 0);

        if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, PERMISSIONS, 112);
        }

        LogProcessoDAO.getInstance().insertLogProcesso("ArrayList<String> itens = new ArrayList<String>();\n" +
                "        itens.add(\"APONTAMENTO\");\n" +
                "        itens.add(\"CONFIGURAÇÃO\");\n" +
                "        itens.add(\"SAIR\");\n" +
                "        AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        lista = findViewById(R.id.listViewMenuInicial);\n" +
                "        lista.setAdapter(adapterList);", getLocalClassName());

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("APONTAMENTO");
        itens.add("CONFIGURAÇÃO");
        itens.add("LOG");
        itens.add("SAIR");

        AdapterList adapterList = new AdapterList(this, itens);
        ListView listViewMenuInicial = findViewById(R.id.listViewMenuInicial);
        listViewMenuInicial.setAdapter(adapterList);
        listViewMenuInicial.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("listViewMenuInicial.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                TextView textView = v.findViewById(R.id.textViewItemList);\n" +
                        "                String text = textView.getText().toString();", getLocalClassName());
                TextView textView = v.findViewById(R.id.textViewItemList);
                String text = textView.getText().toString();

                if (text.equals("APONTAMENTO")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (text.equals(\"APONTAMENTO\")) {", getLocalClassName());
                    if(pcpContext.getConfigCTR().hasElemConfig()
                            && pcpContext.getConfigCTR().hasElemVisitante()) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().hasElemConfig() && pcpContext.getConfigCTR().hasElemVisitante()) {\n" +
                                "                        pcpContext.getConfigCTR().setPosicaoTela(3L);\n" +
                                "                        Intent it = new Intent(MenuInicialActivity.this, ColabActivity.class);", getLocalClassName());
                        pcpContext.getConfigCTR().setPosicaoTela(3L);
                        Intent it = new Intent(MenuInicialActivity.this, MatricColabActivity.class);
                        startActivity(it);
                        finish();
                    }

                } else if (text.equals("CONFIGURAÇÃO")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"CONFIGURAÇÃO\")) {", getLocalClassName());
                    if(pcpContext.getConfigCTR().hasElemConfig()) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().hasElemConfig()) {\n" +
                                "                        pcpContext.getConfigCTR().setPosicaoTela(1L);", getLocalClassName());
                        pcpContext.getConfigCTR().setPosicaoTela(1L);
                    }
                    LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);", getLocalClassName());
                    Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);
                    startActivity(it);
                    finish();
                } else if (text.equals("LOG")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"LOG\")) {", getLocalClassName());
                    if(pcpContext.getConfigCTR().hasElemConfig()){
                        pcpContext.getConfigCTR().setPosicaoTela(2L);
                        Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().hasElemConfig()){\n" +
                                "                        pcpContext.getConfigCTR().setPosicaoTela(2L);\n" +
                                "                        Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);", getLocalClassName());
                        startActivity(it);
                        finish();
                    }

                } else if (text.equals("SAIR")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"SAIR\")) {\n" +
                            "                    Intent intent = new Intent(Intent.ACTION_MAIN);\n" +
                            "                    intent.addCategory(Intent.CATEGORY_HOME);\n" +
                            "                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);\n" +
                            "                    startActivity(intent);", getLocalClassName());
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }

        });

    }

    public void onBackPressed()  {
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            if(!pcpContext.getMovVeicProprioCTR().verEnvioMovEquipProprioEnviar()
                    && !pcpContext.getMovVeicVisitTercCTR().verEnvioMovEquipVisitTercEnviar()
                    && !pcpContext.getMovVeicResidenciaCTR().verEnvioMovEquipResidenciaEnviar()){
                textViewProcesso.setTextColor(Color.GREEN);
                textViewProcesso.setText("Todos os Dados já foram Enviados");
            }
            else{
                textViewProcesso.setTextColor(Color.RED);
                textViewProcesso.setText("Existem Dados para serem Enviados");
            }
            customHandler.postDelayed(this, 10000);
        }
    };

    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

}