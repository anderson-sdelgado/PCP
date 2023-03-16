package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.bean.estaticas.LocalBean;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class ListaLocalActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private List<LocalBean> localList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_local);

        pcpContext = (PCPContext) getApplication();

        Button buttonRetornarLocal = findViewById(R.id.buttonRetornarLocal);

        LogProcessoDAO.getInstance().insertLogProcesso("ArrayList<String> itens = new ArrayList<String>();\n" +
                "        localList = pcpContext.getConfigCTR().localList();\n" +
                "        for(LocalBean localBean : localList){\n" +
                "            itens.add(localBean.getDescrLocal());\n" +
                "        }\n" +
                "        AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        ListView listViewLocal = findViewById(R.id.listViewLocal);\n" +
                "        listViewLocal.setAdapter(adapterList);", getLocalClassName());

        ArrayList<String> itens = new ArrayList<String>();

        localList = pcpContext.getConfigCTR().localList();

        for(LocalBean localBean : localList){
            itens.add(localBean.getDescrLocal());
        }

        AdapterList adapterList = new AdapterList(this, itens);
        ListView listViewLocal = findViewById(R.id.listViewLocal);
        listViewLocal.setAdapter(adapterList);

        listViewLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                TextView textView = v.findViewById(R.id.textViewItemList);\n" +
                        "                String text = textView.getText().toString();", getLocalClassName());

                LocalBean localBean = localList.get(position);
                pcpContext.getConfigCTR().setIdLocal(localBean.getIdLocal());
                Intent it = new Intent(ListaLocalActivity.this, MenuInicialActivity.class);
                startActivity(it);
                finish();

            }

        });

        buttonRetornarLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogProcessoDAO.getInstance().insertLogProcesso("buttonRetornarLocal.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaLocalActivity.this, ColabActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaLocalActivity.this, MatricColabActivity.class);
                startActivity(it);
                finish();

            }
        });

    }

    public void onBackPressed() {
    }

}