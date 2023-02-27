package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class ListaMovProprioActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private List<MovEquipProprioBean> movEquipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mov_proprio);

        pcpContext = (PCPContext) getApplication();

        Button buttonEntradaMov = findViewById(R.id.buttonEntradaMovProprio);
        Button buttonSaidaMov = findViewById(R.id.buttonSaidaMovProprio);
        Button buttonCancMov = findViewById(R.id.buttonCancMovProprio);

        TextView textViewVigia = findViewById(R.id.textViewVigia);

        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() > 0L){\n" +
                "            textViewVigia.setText(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() + \" - \"  + pcpContext.getConfigCTR().getColab(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());\n" +
                "        } else {\n" +
                "            textViewVigia.setText(\"\");\n" +
                "        }\n" +
                "        movEquipList = pcpContext.getMovVeicProprioCTR().movEquipProprioList();", getLocalClassName());
        if(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() > 0L){
            textViewVigia.setText(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() + " - "  + pcpContext.getConfigCTR().getColab(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());
        } else {
            textViewVigia.setText("");
        }

        pcpContext.getMovVeicProprioCTR().deleteMovEquipProprioAberto();
        movEquipList = pcpContext.getMovVeicProprioCTR().movEquipProprioList();

        LogProcessoDAO.getInstance().insertLogProcesso("ListView listaMov = findViewById(R.id.listaMovProprio);\n" +
                "        AdapterListMov adapterListMov = new AdapterListMov(this, movEquipList, tipo);\n" +
                "        listaMov.setAdapter(adapterListMov);", getLocalClassName());
        ListView listaMov = findViewById(R.id.listaMovProprio);
        AdapterListMovProprio adapterListMovProprio = new AdapterListMovProprio(this, movEquipList);
        listaMov.setAdapter(adapterListMovProprio);
        listaMov.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("listaMov.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoListaMov((long) (position));\n" +
                        "                Intent it = new Intent(ListaMovProprioActivity.this, DescrMovActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoListaMov((long) (position));
                Intent it = new Intent(ListaMovProprioActivity.this, DescrMovProprioActivity.class);
                startActivity(it);
                finish();

            }

        });

        buttonEntradaMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonEntradaMov.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoTela(4L);\n" +
                        "                pcpContext.getMovVeicProprioCTR().abrirMovEquipProprio(2L);\n" +
                        "                Intent it = new Intent(ListaMovProprioActivity.this, ColabActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(4L);
                pcpContext.getMovVeicProprioCTR().abrirMovEquipProprio(2L);
                Intent it = new Intent(ListaMovProprioActivity.this, ColabActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonSaidaMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonSaidaMov.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoTela(4L);\n" +
                        "                pcpContext.getMovVeicProprioCTR().abrirMovEquipProprio(1L);\n" +
                        "                Intent it = new Intent(ListaMovProprioActivity.this, ColabActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(4L);
                pcpContext.getMovVeicProprioCTR().abrirMovEquipProprio(1L);
                Intent it = new Intent(ListaMovProprioActivity.this, ColabActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonCancMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancMov.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaMovActivity.this, TelaInicialActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaMovProprioActivity.this, TelaInicialActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

    public void onBackPressed() {
    }

}