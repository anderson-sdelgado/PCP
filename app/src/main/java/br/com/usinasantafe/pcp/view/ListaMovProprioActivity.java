package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        Button buttonRetornarMov = findViewById(R.id.buttonRetornarMovProprio);

        TextView textViewVigia = findViewById(R.id.textViewVigia);
        TextView textViewLocal = findViewById(R.id.textViewLocal);

        LogProcessoDAO.getInstance().insertLogProcesso("textViewVigia.setText(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() + \" - \"  + pcpContext.getConfigCTR().getColab(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());\n" +
                "        textViewLocal.setText(\"LOCAL: \" + pcpContext.getConfigCTR().getLocal().getDescrLocal());\n" +
                "        pcpContext.getMovVeicProprioCTR().deleteMovEquipProprioAberto();\n" +
                "        movEquipList = pcpContext.getMovVeicProprioCTR().movEquipProprioList();\n" +
                "        ListView listViewMov = findViewById(R.id.listaMovProprio);\n" +
                "        AdapterListMovProprio adapterListMovProprio = new AdapterListMovProprio(this, movEquipList);\n" +
                "        listViewMov.setAdapter(adapterListMovProprio);", getLocalClassName());

        textViewVigia.setText(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() + " - "  + pcpContext.getConfigCTR().getColabMatric(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());
        textViewLocal.setText("LOCAL: " + pcpContext.getConfigCTR().getLocal().getDescrLocal());

        pcpContext.getMovVeicProprioCTR().deleteMovEquipProprioAberto();
        movEquipList = pcpContext.getMovVeicProprioCTR().movEquipProprioFechadoList();

        ListView listViewMov = findViewById(R.id.listViewMovProprio);
        AdapterListMovProprio adapterListMovProprio = new AdapterListMovProprio(this, movEquipList);
        listViewMov.setAdapter(adapterListMovProprio);

        buttonEntradaMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonEntradaMov.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoTela(4L);\n" +
                        "                pcpContext.getMovVeicProprioCTR().abrirMovEquipProprio(2L);\n" +
                        "                Intent it = new Intent(ListaMovProprioActivity.this, VeiculoUsinaActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(4L);
                pcpContext.getMovVeicProprioCTR().abrirMovEquipProprio(2L);
                Intent it = new Intent(ListaMovProprioActivity.this, VeiculoUsinaActivity.class);
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
                        "                Intent it = new Intent(ListaMovProprioActivity.this, VeiculoUsinaActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(4L);
                pcpContext.getMovVeicProprioCTR().abrirMovEquipProprio(1L);
                Intent it = new Intent(ListaMovProprioActivity.this, VeiculoUsinaActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonRetornarMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonRetornarMov.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaMovProprioActivity.this, ListaMenuApontActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaMovProprioActivity.this, ListaMenuApontActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

    public void onBackPressed() {
    }

}