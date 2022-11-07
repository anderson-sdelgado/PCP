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

public class DescrMovActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private List movEquipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descr_mov);

        pcpContext = (PCPContext) getApplication();

        Button buttonRetDescrMov = findViewById(R.id.buttonRetDescrMov);

        ArrayList<String> itens;
        if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){\n" +
                    "            movEquipList = pcpContext.getMovVeicProprioCTR().movEquipProprioAllList();\n" +
                    "            MovEquipProprioBean movEquipProprioBean = (MovEquipProprioBean) movEquipList.get(Math.toIntExact(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov()));\n" +
                    "            itens = movEquipProprio(movEquipProprioBean);", getLocalClassName());
            movEquipList = pcpContext.getMovVeicProprioCTR().movEquipProprioAllList();
            MovEquipProprioBean movEquipProprioBean = (MovEquipProprioBean) movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());
            itens = movEquipProprio(movEquipProprioBean);
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            movEquipList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercAllList();\n" +
                    "            MovEquipVisitTercBean movEquipVisitTercBean = (MovEquipVisitTercBean) movEquipList.get(Math.toIntExact(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov()));\n" +
                    "            itens = movEquipVisitTerc(movEquipVisitTercBean);", getLocalClassName());
            movEquipList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercAllList();
            MovEquipVisitTercBean movEquipVisitTercBean = (MovEquipVisitTercBean) movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());
            itens = movEquipVisitTerc(movEquipVisitTercBean);
        }

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
                Intent it = new Intent(DescrMovActivity.this, ListaMovActivity.class);
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

    public ArrayList<String> movEquipVisitTerc(MovEquipVisitTercBean movEquipVisitTercBean){
        LogProcessoDAO.getInstance().insertLogProcesso("public ArrayList<String> movEquipVisitTerc(MovEquipVisitTercBean movEquipVisitTercBean){\n" +
                "        return pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTerc(movEquipVisitTercBean);", getLocalClassName());
        return pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTerc(movEquipVisitTercBean);
    }

    public void onBackPressed() {
    }

}