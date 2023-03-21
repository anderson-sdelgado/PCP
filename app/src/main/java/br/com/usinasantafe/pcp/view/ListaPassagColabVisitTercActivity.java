package br.com.usinasantafe.pcp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import br.com.usinasantafe.pcp.model.bean.estaticas.TerceiroBean;
import br.com.usinasantafe.pcp.model.bean.estaticas.VisitanteBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioPassagBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercPassagBean;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class ListaPassagColabVisitTercActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private List<MovEquipProprioPassagBean> movEquipProprioPassagList;
    private List<MovEquipVisitTercPassagBean> movEquipVisitTercPassagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_passag_colab_visit_terc);

        pcpContext = (PCPContext) getApplication();

        Button buttonInserirPassageiro = findViewById(R.id.buttonInserirPassageiro);
        Button buttonOkPassageiro = findViewById(R.id.buttonOkPassageiro);
        Button buttonCancPassageiro = findViewById(R.id.buttonCancPassageiro);

        ArrayList<String> itens = new ArrayList<String>();

        if((pcpContext.getConfigCTR().getConfig().getPosicaoTela() < 7L)){
            LogProcessoDAO.getInstance().insertLogProcesso("if((pcpContext.getConfigCTR().getConfig().getPosicaoTela() < 7L)){", getLocalClassName());
            if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){\n" +
                        "            movEquipProprioPassagList = pcpContext.getMovVeicProprioCTR().movEquipProprioPassagList();\n" +
                        "            for(MovEquipProprioPassagBean movEquipProprioPassagBean : movEquipProprioPassagList){\n" +
                        "                itens.add(movEquipProprioPassagBean.getMatricColabMovEquipProprioPassag() + \" - \" + pcpContext.getConfigCTR().getColab(movEquipProprioPassagBean.getMatricColabMovEquipProprioPassag()).getNomeColab());\n" +
                        "            }", getLocalClassName());
                movEquipProprioPassagList = pcpContext.getMovVeicProprioCTR().movEquipProprioPassagAbertoList();
                for(MovEquipProprioPassagBean movEquipProprioPassagBean : movEquipProprioPassagList){
                    itens.add(movEquipProprioPassagBean.getNroMatricMovEquipProprioPassag() + " - " + pcpContext.getConfigCTR().getColabMatric(movEquipProprioPassagBean.getNroMatricMovEquipProprioPassag()).getNomeColab());
                }
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "            movEquipVisitTercPassagList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercPassagList();\n" +
                        "            for(MovEquipVisitTercPassagBean movEquipVisitTercPassagBean : movEquipVisitTercPassagList) {\n" +
                        "                if (pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoVisitTercMovEquipVisitTerc() == 2L) {\n" +
                        "                    TerceiroBean terceiroBean = pcpContext.getMovVeicVisitTercCTR().getTerceiroId(movEquipVisitTercPassagBean.getIdVisitTercMovEquipVisitTercPassag());\n" +
                        "                    itens.add(terceiroBean.getCpfTerceiro() + \" - \" + terceiroBean.getNomeTerceiro());\n" +
                        "                } else {\n" +
                        "                    VisitanteBean visitanteBean = pcpContext.getMovVeicVisitTercCTR().getVisitanteId(movEquipVisitTercPassagBean.getIdVisitTercMovEquipVisitTercPassag());\n" +
                        "                    itens.add(visitanteBean.getCpfVisitante() + \" - \" + visitanteBean.getNomeVisitante());\n" +
                        "                }\n" +
                        "            }", getLocalClassName());
                movEquipVisitTercPassagList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercPassagList();
                for(MovEquipVisitTercPassagBean movEquipVisitTercPassagBean : movEquipVisitTercPassagList) {
                    if (pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoVisitTercMovEquipVisitTerc() == 2L) {
                        TerceiroBean terceiroBean = pcpContext.getMovVeicVisitTercCTR().getTerceiroId(movEquipVisitTercPassagBean.getIdVisitTercMovEquipVisitTercPassag());
                        itens.add(terceiroBean.getCpfTerceiro() + " - " + terceiroBean.getNomeTerceiro());
                    } else {
                        VisitanteBean visitanteBean = pcpContext.getMovVeicVisitTercCTR().getVisitanteId(movEquipVisitTercPassagBean.getIdVisitTercMovEquipVisitTercPassag());
                        itens.add(visitanteBean.getCpfVisitante() + " - " + visitanteBean.getNomeVisitante());
                    }
                }
            }
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso(" } else {", getLocalClassName());
            if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){\n" +
                        "                movEquipProprioPassagList = pcpContext.getMovVeicProprioCTR().movEquipProprioFechadoPassagList(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());\n" +
                        "                for(MovEquipProprioPassagBean movEquipProprioPassagBean : movEquipProprioPassagList){\n" +
                        "                    itens.add(movEquipProprioPassagBean.getNroMatricMovEquipProprioPassag() + \" - \" + pcpContext.getConfigCTR().getColabMatric(movEquipProprioPassagBean.getNroMatricMovEquipProprioPassag()).getNomeColab());\n" +
                        "                }", getLocalClassName());
                movEquipProprioPassagList = pcpContext.getMovVeicProprioCTR().movEquipProprioPassagFechadoList(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());
                for(MovEquipProprioPassagBean movEquipProprioPassagBean : movEquipProprioPassagList){
                    itens.add(movEquipProprioPassagBean.getNroMatricMovEquipProprioPassag() + " - " + pcpContext.getConfigCTR().getColabMatric(movEquipProprioPassagBean.getNroMatricMovEquipProprioPassag()).getNomeColab());
                }
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                movEquipVisitTercPassagList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercPassagFechadoList(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());\n" +
                        "                for(MovEquipVisitTercPassagBean movEquipVisitTercPassagBean : movEquipVisitTercPassagList) {\n" +
                        "                    if (pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getTipoVisitTercMovEquipVisitTerc() == 2L) {\n" +
                        "                        TerceiroBean terceiroBean = pcpContext.getMovVeicVisitTercCTR().getTerceiroId(movEquipVisitTercPassagBean.getIdVisitTercMovEquipVisitTercPassag());\n" +
                        "                        itens.add(terceiroBean.getCpfTerceiro() + \" - \" + terceiroBean.getNomeTerceiro());\n" +
                        "                    } else {\n" +
                        "                        VisitanteBean visitanteBean = pcpContext.getMovVeicVisitTercCTR().getVisitanteId(movEquipVisitTercPassagBean.getIdVisitTercMovEquipVisitTercPassag());\n" +
                        "                        itens.add(visitanteBean.getCpfVisitante() + \" - \" + visitanteBean.getNomeVisitante());\n" +
                        "                    }\n" +
                        "                }", getLocalClassName());
                movEquipVisitTercPassagList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercPassagFechadoList(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());
                for(MovEquipVisitTercPassagBean movEquipVisitTercPassagBean : movEquipVisitTercPassagList) {
                    if (pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getTipoVisitTercMovEquipVisitTerc() == 2L) {
                        TerceiroBean terceiroBean = pcpContext.getMovVeicVisitTercCTR().getTerceiroId(movEquipVisitTercPassagBean.getIdVisitTercMovEquipVisitTercPassag());
                        itens.add(terceiroBean.getCpfTerceiro() + " - " + terceiroBean.getNomeTerceiro());
                    } else {
                        VisitanteBean visitanteBean = pcpContext.getMovVeicVisitTercCTR().getVisitanteId(movEquipVisitTercPassagBean.getIdVisitTercMovEquipVisitTercPassag());
                        itens.add(visitanteBean.getCpfVisitante() + " - " + visitanteBean.getNomeVisitante());
                    }
                }
            }
        }
        LogProcessoDAO.getInstance().insertLogProcesso("AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        ListView listViewPassag = findViewById(R.id.listaPassageiro);\n" +
                "        listViewPassag.setAdapter(adapterList);", getLocalClassName());
        AdapterList adapterList = new AdapterList(this, itens);
        ListView listViewPassag = findViewById(R.id.listViewPassag);
        listViewPassag.setAdapter(adapterList);
        listViewPassag.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("listViewPassag.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaPassagColabVisitTercActivity.this);\n" +
                        "                alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                String label = \"DESEJA EXCLUIR O PASSAGEIRO?\";\n" +
                        "                alerta.setMessage(label);", getLocalClassName());
                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaPassagColabVisitTercActivity.this);
                alerta.setTitle("ATENÇÃO");
                String label = "DESEJA EXCLUIR O PASSAGEIRO?";
                alerta.setMessage(label);
                alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                        if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){
                            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){\n" +
                                    "                            pcpContext.getMovVeicProprioCTR().deleteMovEquipProprioPassag(movEquipProprioPassagList.get(position));", getLocalClassName());
                            pcpContext.getMovVeicProprioCTR().deleteMovEquipProprioPassag(movEquipProprioPassagList.get(position));
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                            pcpContext.getMovVeicVisitTercCTR().deleteMovEquipVisitTercPassag(movEquipVisitTercPassagList.get(position));", getLocalClassName());
                            pcpContext.getMovVeicVisitTercCTR().deleteMovEquipVisitTercPassag(movEquipVisitTercPassagList.get(position));
                        }

                        LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(ListaPassagColabVisitTercActivity.this, ListaPassagColabVisitTercActivity.class);", getLocalClassName());
                        Intent it = new Intent(ListaPassagColabVisitTercActivity.this, ListaPassagColabVisitTercActivity.class);
                        startActivity(it);
                        finish();
                    }

                });

                alerta.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setNegativeButton(\"NÃO\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                    }

                });

                alerta.show();
            }

        });

        buttonInserirPassageiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonInserirPassageiro.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                            "                    pcpContext.getConfigCTR().setPosicaoTela(6L);", getLocalClassName());
                    pcpContext.getConfigCTR().setPosicaoTela(6L);
                } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){\n" +
                            "                    pcpContext.getConfigCTR().setPosicaoTela(8L);", getLocalClassName());
                    pcpContext.getConfigCTR().setPosicaoTela(8L);
                }
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){\n" +
                            "                    it = new Intent(ListaPassagColabVisitTercActivity.this, MatricColabActivity.class);", getLocalClassName());
                    it = new Intent(ListaPassagColabVisitTercActivity.this, MatricColabActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    it = new Intent(ListaPassagColabVisitTercActivity.this, CpfVisitTercActivity.class);", getLocalClassName());
                    it = new Intent(ListaPassagColabVisitTercActivity.this, CPFVisitTercActivity.class);
                }
                startActivity(it);
                finish();
            }
        });

        buttonOkPassageiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkPassageiro.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                Intent it;
                if((pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L)
                        || (pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 6L)){
                    LogProcessoDAO.getInstance().insertLogProcesso("if((pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L)\n" +
                            "                        || (pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 6L)){\n" +
                            "                    pcpContext.getConfigCTR().setPosicaoTela(4L);", getLocalClassName());
                    pcpContext.getConfigCTR().setPosicaoTela(4L);
                    if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){\n" +
                                "                    it = new Intent(ListaPassagColabVisitTercActivity.this, VeiculoUsinaActivity.class);", getLocalClassName());
                        it = new Intent(ListaPassagColabVisitTercActivity.this, VeiculoUsinaActivity.class);
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                    it = new Intent(ListaPassagColabVisitTercActivity.this, VeiculoVisitTercResidenciaActivity.class);", getLocalClassName());
                        it = new Intent(ListaPassagColabVisitTercActivity.this, VeiculoVisitTercResidActivity.class);
                    }
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    it = new Intent(ListaPassagColabVisitTercActivity.this, DescrMovActivity.class);", getLocalClassName());
                    it = new Intent(ListaPassagColabVisitTercActivity.this, DescrMovActivity.class);
                }
                startActivity(it);
                finish();
            }
        });

        buttonCancPassageiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancPassageiro.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                Intent it;
                if((pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L)
                        || (pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 6L)) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if((pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L)\n" +
                            "                        || (pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 6L)){\n" +
                            "                    pcpContext.getConfigCTR().setPosicaoTela(4L);", getLocalClassName());
                    pcpContext.getConfigCTR().setPosicaoTela(4L);
                    if (pcpContext.getConfigCTR().getConfig().getTipoMov() == 1) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){\n" +
                                "                    it = new Intent(ListaPassagColabVisitTercActivity.this, MatricColabActivity.class);", getLocalClassName());
                        it = new Intent(ListaPassagColabVisitTercActivity.this, MatricColabActivity.class);
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                    it = new Intent(ListaPassagColabVisitTercActivity.this, CpfVisitTercActivity.class);", getLocalClassName());
                        it = new Intent(ListaPassagColabVisitTercActivity.this, CPFVisitTercActivity.class);
                    }
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    it = new Intent(ListaPassagColabVisitTercActivity.this, DescrMovActivity.class);", getLocalClassName());
                    it = new Intent(ListaPassagColabVisitTercActivity.this, DescrMovActivity.class);
                }
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed() {
    }

}