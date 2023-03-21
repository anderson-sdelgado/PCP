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
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipResidenciaBean;
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
                    "            itens = movEquipProprio();", getLocalClassName());
            itens = movEquipProprio();
        } else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                    "            itens = movEquipVisitTerc();", getLocalClassName());
            itens = movEquipVisitTerc();
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            itens = movEquipVisitResidencia();", getLocalClassName());
            itens = movEquipVisitResidencia();
        }
        LogProcessoDAO.getInstance().insertLogProcesso("AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        ListView listViewDescrMov = findViewById(R.id.listViewDescrMov);\n" +
                "        listViewDescrMov.setAdapter(adapterList);", getLocalClassName());
        AdapterList adapterList = new AdapterList(this, itens);
        ListView listViewDescrMov = findViewById(R.id.listViewDescrMov);
        listViewDescrMov.setAdapter(adapterList);
        listViewDescrMov.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                LogProcessoDAO.getInstance().insertLogProcesso("listViewDescrMov.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoTela(7L);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(7L);
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){\n" +
                            "                    manutMovEquipProprio(position);", getLocalClassName());
                    manutMovEquipProprio(position);
                } else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                            "                    manutMovEquipVisitTerc(position);", getLocalClassName());
                    manutMovEquipVisitTerc(position);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    manutMovEquipResidencia(position);", getLocalClassName());
                    manutMovEquipResidencia(position);
                }

            }

        });

        buttonRetDescrMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonRetDescrMov.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(DescrMovActivity.this, ListaMovAbertoActivity.class);", getLocalClassName());
                Intent it = new Intent(DescrMovActivity.this, ListaMovFinalizadoActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    public ArrayList<String> movEquipProprio(){
        LogProcessoDAO.getInstance().insertLogProcesso("public ArrayList<String> movEquipProprio(){\n" +
                "        movEquipList = pcpContext.getMovVeicProprioCTR().movEquipProprioFechadoList();\n" +
                "        MovEquipProprioBean movEquipProprioBean = (MovEquipProprioBean) movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());\n" +
                "        return pcpContext.getMovVeicProprioCTR().getMovEquipProprio(movEquipProprioBean);", getLocalClassName());
        movEquipList = pcpContext.getMovVeicProprioCTR().movEquipProprioFechadoList();
        MovEquipProprioBean movEquipProprioBean = (MovEquipProprioBean) movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());
        return pcpContext.getMovVeicProprioCTR().getMovEquipProprio(movEquipProprioBean);
    }

    public ArrayList<String> movEquipVisitTerc(){
        LogProcessoDAO.getInstance().insertLogProcesso("public ArrayList<String> movEquipVisitTerc(){\n" +
                "        movEquipList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercFechadoList();\n" +
                "        MovEquipVisitTercBean movEquipVisitTercBean = (MovEquipVisitTercBean) movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());\n" +
                "        return pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTerc(movEquipVisitTercBean);", getLocalClassName());
        movEquipList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercFechadoList();
        MovEquipVisitTercBean movEquipVisitTercBean = (MovEquipVisitTercBean) movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());
        return pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTerc(movEquipVisitTercBean);
    }

    public ArrayList<String> movEquipVisitResidencia(){
        LogProcessoDAO.getInstance().insertLogProcesso("public ArrayList<String> movEquipProprio(){\n" +
                "        movEquipList = pcpContext.getMovVeicProprioCTR().movEquipProprioAllList();\n" +
                "        MovEquipProprioBean movEquipProprioBean = movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());\n" +
                "        return pcpContext.getMovVeicProprioCTR().getMovEquipProprio(movEquipProprioBean);", getLocalClassName());
        movEquipList = pcpContext.getMovVeicResidenciaCTR().movEquipResidenciaFechadoList();
        MovEquipResidenciaBean movEquipResidenciaBean = (MovEquipResidenciaBean) movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());
        return pcpContext.getMovVeicResidenciaCTR().getMovEquipResidencia(movEquipResidenciaBean);
    }

    public void manutMovEquipProprio(int posicao){
        LogProcessoDAO.getInstance().insertLogProcesso("public void manutMovEquipProprio(int posicao){\n" +
                "        MovEquipProprioBean movEquipProprioBean = (MovEquipProprioBean) movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());", getLocalClassName());
        MovEquipProprioBean movEquipProprioBean = (MovEquipProprioBean) movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());
        Intent it;
        switch (posicao){
            case 2:
                LogProcessoDAO.getInstance().insertLogProcesso("case 2:\n" +
                        "                it = new Intent(DescrMovActivity.this, VeiculoUsinaActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, VeiculoUsinaActivity.class);
                startActivity(it);
                finish();
                break;
            case 3:
                LogProcessoDAO.getInstance().insertLogProcesso("case 3:\n" +
                        "                it = new Intent(DescrMovActivity.this, MatricColabActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, MatricColabActivity.class);
                startActivity(it);
                finish();
                break;
            case 4:
                LogProcessoDAO.getInstance().insertLogProcesso("case 4:\n" +
                        "                it = new Intent(DescrMovActivity.this, ListaPassagColabVisitTercActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, ListaPassagColabVisitTercActivity.class);
                startActivity(it);
                finish();
                break;
            case 5:
                    LogProcessoDAO.getInstance().insertLogProcesso("case 5:\n" +
                            "                    it = new Intent(DescrMovActivity.this, DestinoActivity.class);", getLocalClassName());
                    it = new Intent(DescrMovActivity.this, DestinoActivity.class);
                    startActivity(it);
                    finish();
                break;
            case 6:
                LogProcessoDAO.getInstance().insertLogProcesso("case 6:\n" +
                        "                it = new Intent(DescrMovActivity.this, ListaVeiculoSegActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, ListaVeiculoSegActivity.class);
                startActivity(it);
                finish();
                break;
            case 7:
                if(movEquipProprioBean.getTipoMovEquipProprio() == 1L){
                    LogProcessoDAO.getInstance().insertLogProcesso("case 7:\n" +
                            "                if(movEquipProprioBean.getTipoMovEquipProprio() == 1L){\n" +
                            "                    it = new Intent(DescrMovActivity.this, NotaFiscalActivity.class);", getLocalClassName());
                    it = new Intent(DescrMovActivity.this, NotaFiscalActivity.class);
                    startActivity(it);
                    finish();
                }
                break;
            case 8:
                LogProcessoDAO.getInstance().insertLogProcesso("case 8:\n" +
                        "                it = new Intent(DescrMovActivity.this, ObservacaoActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, ObservActivity.class);
                startActivity(it);
                finish();
                break;
        }
    }

    public void manutMovEquipVisitTerc(int posicao){
        LogProcessoDAO.getInstance().insertLogProcesso("public void manutMovEquipVisitTerc(int posicao){\n" +
                "        MovEquipVisitTercBean movEquipVisitTercBean = (MovEquipVisitTercBean) movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());", getLocalClassName());
        MovEquipVisitTercBean movEquipVisitTercBean = (MovEquipVisitTercBean) movEquipList.get(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());
        Intent it;
        switch (posicao){
            case 2:
                LogProcessoDAO.getInstance().insertLogProcesso("case 2:\n" +
                        "                it = new Intent(DescrMovActivity.this, VeiculoVisitTercResidenciaActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, VeiculoVisitTercResidActivity.class);
                startActivity(it);
                finish();
                break;
            case 3:
                LogProcessoDAO.getInstance().insertLogProcesso("case 3:\n" +
                        "                it = new Intent(DescrMovActivity.this, PlacaVisitTercResidenciaActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, PlacaVisitTercResidActivity.class);
                startActivity(it);
                finish();
                break;
            case 5:
                LogProcessoDAO.getInstance().insertLogProcesso("case 5:\n" +
                        "                it = new Intent(DescrMovActivity.this, CPFVisitTercActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, CPFVisitTercActivity.class);
                startActivity(it);
                finish();
                break;
            case 6:
                LogProcessoDAO.getInstance().insertLogProcesso("case 6:\n" +
                        "                it = new Intent(DescrMovActivity.this, ListaPassagColabVisitTercActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, ListaPassagColabVisitTercActivity.class);
                startActivity(it);
                finish();
                break;
            case 8:
                if(movEquipVisitTercBean.getTipoMovEquipVisitTerc() == 1L){
                    LogProcessoDAO.getInstance().insertLogProcesso("case 8:\n" +
                            "                if(movEquipVisitTercBean.getTipoMovEquipVisitTerc() == 1L){\n" +
                            "                    it = new Intent(DescrMovActivity.this, DestinoActivity.class);", getLocalClassName());
                    it = new Intent(DescrMovActivity.this, DestinoActivity.class);
                    startActivity(it);
                    finish();
                }
                break;
            case 9:
                LogProcessoDAO.getInstance().insertLogProcesso("case 9:\n" +
                        "                it = new Intent(DescrMovActivity.this, ObservacaoActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, ObservActivity.class);
                startActivity(it);
                finish();
                break;
        }
    }

    public void manutMovEquipResidencia(int posicao){
        LogProcessoDAO.getInstance().insertLogProcesso("public void manutMovEquipResidencia(int posicao){", getLocalClassName());
        Intent it;
        switch (posicao){
            case 2:
                LogProcessoDAO.getInstance().insertLogProcesso("case 2:\n" +
                        "                it = new Intent(DescrMovActivity.this, VeiculoVisitTercResidenciaActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, VeiculoVisitTercResidActivity.class);
                startActivity(it);
                finish();
                break;
            case 3:
                LogProcessoDAO.getInstance().insertLogProcesso("case 3:\n" +
                        "                it = new Intent(DescrMovActivity.this, PlacaVisitTercResidenciaActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, PlacaVisitTercResidActivity.class);
                startActivity(it);
                finish();
                break;
            case 4:
                LogProcessoDAO.getInstance().insertLogProcesso("case 4:\n" +
                        "                it = new Intent(DescrMovActivity.this, VisitanteResidenciaActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, MotoristaResidenciaActivity.class);
                startActivity(it);
                finish();
                break;
            case 5:
                LogProcessoDAO.getInstance().insertLogProcesso("case 5:\n" +
                        "                it = new Intent(DescrMovActivity.this, ObservacaoActivity.class);", getLocalClassName());
                it = new Intent(DescrMovActivity.this, ObservActivity.class);
                startActivity(it);
                finish();
                break;
        }
    }

    public void onBackPressed() {
    }

}