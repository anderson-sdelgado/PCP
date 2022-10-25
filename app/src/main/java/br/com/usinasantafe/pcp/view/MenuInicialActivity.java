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

    private ListView listaMenuInicial;
    private PCPContext piaContext;
    private TextView textViewProcesso;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        TextView textViewPrincipal = findViewById(R.id.textViewPrincipal);

//        textViewPrincipal.setText("PRINCIPAL - V " + BuildConfig.VERSION_NAME);

        piaContext = (PCPContext) getApplication();

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
        listaMenuInicial = findViewById(R.id.listaMenuInicial);
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

                } else if (text.equals("CONTROLE VEÍCULO VISITANTE/TERCEIRO")) {

                } else if (text.equals("VIGIA")) {

                } else if (text.equals("CONFIGURAÇÃO")) {

                } else if (text.equals("LOG")) {

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

            if(!piaContext.getMovimentacaoVeicProprioCTR().verMovEquipProprioAberto()
                    && !piaContext.getMovimentacaoVeicVisTercCTR().verMovEquipProprioAberto()){
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