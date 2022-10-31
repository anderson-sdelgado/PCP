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
//        textViewPrincipal.setText("PRINCIPAL - V " + BuildConfig.VERSION_NAME);

        pcpContext = (PCPContext) getApplication();

        LogProcessoDAO.getInstance().insertLogProcesso("textViewProcesso = findViewById(R.id.textViewProcesso);\n" +
                "        customHandler.postDelayed(updateTimerThread, 0);\n" +
                "        ArrayList<String> itens = new ArrayList<String>();\n" +
                "        itens.add(\"CONTROLE VEÍCULO PRÓPRIO\");\n" +
                "        itens.add(\"CONTROLE VEÍCULO VISITANTE/TERCEIRO\");\n" +
                "        itens.add(\"VIGIA\");\n" +
                "        itens.add(\"CONFIGURAÇÃO\");\n" +
                "        itens.add(\"SAIR\");\n" +
                "        AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        lista = findViewById(R.id.listaMenuInicial);\n" +
                "        lista.setAdapter(adapterList);", getLocalClassName());

        textViewProcesso = findViewById(R.id.textViewProcesso);
        customHandler.postDelayed(updateTimerThread, 0);

        if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, PERMISSIONS, 112);
        }

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("CONTROLE VEÍCULO PRÓPRIO");
        itens.add("CONTROLE VEÍCULO VISITANTE/TERCEIRO");
        itens.add("VIGIA");
        itens.add("CONFIGURAÇÃO");
        itens.add("LOG");
        itens.add("SAIR");

        AdapterList adapterList = new AdapterList(this, itens);
        ListView listaMenuInicial = findViewById(R.id.listaMenuInicial);
        listaMenuInicial.setAdapter(adapterList);

        listaMenuInicial.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                TextView textView = v.findViewById(R.id.textViewItemList);\n" +
                        "                String text = textView.getText().toString();", getLocalClassName());
                TextView textView = v.findViewById(R.id.textViewItemList);
                String text = textView.getText().toString();

                if (text.equals("CONTROLE VEÍCULO PRÓPRIO")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (text.equals(\"CONTROLE VEÍCULO PRÓPRIO\")) {\n" +
                            "                    pcpContext.getConfigCTR().setTipoMov(1L);\n" +
                            "                    Intent it = new Intent(MenuInicialActivity.this, ListaMovActivity.class);", getLocalClassName());
                    pcpContext.getConfigCTR().setTipoMov(1L);
                    Intent it = new Intent(MenuInicialActivity.this, ListaMovActivity.class);
                    startActivity(it);
                    finish();

                } else if (text.equals("CONTROLE VEÍCULO VISITANTE/TERCEIRO")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"CONTROLE VEÍCULO VISITANTE/TERCEIRO\")) {\n" +
                            "                    pcpContext.getConfigCTR().setTipoMov(2L);\n" +
                            "                    Intent it = new Intent(MenuInicialActivity.this, ListaMovActivity.class);", getLocalClassName());
                    pcpContext.getConfigCTR().setTipoMov(2L);
                    Intent it = new Intent(MenuInicialActivity.this, ListaMovActivity.class);
                    startActivity(it);
                    finish();

                } else if (text.equals("VIGIA")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"VIGIA\")) {\n" +
                            "                    pcpContext.getConfigCTR().setPosicaoTela(3L);\n" +
                            "                    Intent it = new Intent(MenuInicialActivity.this, ColabActivity.class);", getLocalClassName());
                    pcpContext.getConfigCTR().setPosicaoTela(3L);
                    Intent it = new Intent(MenuInicialActivity.this, ColabActivity.class);
                    startActivity(it);
                    finish();

                } else if (text.equals("CONFIGURAÇÃO")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"CONFIGURAÇÃO\")) {\n" +
                            "                    pcpContext.getConfigCTR().setPosicaoTela(1L);\n" +
                            "                    Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);", getLocalClassName());
                    pcpContext.getConfigCTR().setPosicaoTela(1L);
                    Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);
                    startActivity(it);
                    finish();

                } else if (text.equals("LOG")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"LOG\")) {\n" +
                            "                    pcpContext.getConfigCTR().setPosicaoTela(2L);\n" +
                            "                    Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);", getLocalClassName());
                    pcpContext.getConfigCTR().setPosicaoTela(2L);
                    Intent it = new Intent(MenuInicialActivity.this, SenhaActivity.class);
                    startActivity(it);
                    finish();

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

            if(!pcpContext.getMovimentacaoVeicProprioCTR().verMovEquipProprioAberto()
                    && !pcpContext.getMovimentacaoVeicVisitTercCTR().verMovEquipVisitTercAberto()){
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