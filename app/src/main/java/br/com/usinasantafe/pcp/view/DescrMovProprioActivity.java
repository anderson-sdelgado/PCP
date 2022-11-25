package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercBean;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class DescrMovProprioActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private List<MovEquipProprioBean> movEquipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descr_mov_proprio);

        pcpContext = (PCPContext) getApplication();

        Button buttonRetDescrMov = findViewById(R.id.buttonRetDescrMov);

        LogProcessoDAO.getInstance().insertLogProcesso("movEquipList = pcpContext.getMovVeicProprioCTR().movEquipProprioAllList();\n" +
                "        ArrayList<String> itens = movEquipProprio(movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()););\n" +
                "        AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        ListView listViewDescrMov = findViewById(R.id.listViewDescrMov);\n" +
                "        listViewDescrMov.setAdapter(adapterList);", getLocalClassName());
        movEquipList = pcpContext.getMovVeicProprioCTR().movEquipProprioAllList();
        ArrayList<String> itens = movEquipProprio(movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()));

        AdapterList adapterList = new AdapterList(this, itens);
        ListView listViewDescrMov = findViewById(R.id.listViewDescrMov);
        listViewDescrMov.setAdapter(adapterList);

        buttonRetDescrMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonRetDescrMov.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(DescrMovActivity.this, ListaMovActivity.class);", getLocalClassName());
                Intent it = new Intent(DescrMovProprioActivity.this, ListaMovProprioActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    public ArrayList<String> movEquipProprio(MovEquipProprioBean movEquipProprioBean){
        LogProcessoDAO.getInstance().insertLogProcesso("public ArrayList<String> movEquipProprio(MovEquipProprioBean movEquipProprioBean){\n" +
                "        return pcpContext.getMovVeicProprioCTR().getMovEquipProprio(movEquipProprioBean);", getLocalClassName());
        return pcpContext.getMovVeicProprioCTR().getMovEquipProprio(movEquipProprioBean);
    }

    public void onBackPressed() {
    }

}