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

        if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){

            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){\n" +
                    "            movEquipProprioPassagList = pcpContext.getMovVeicProprioCTR().movEquipProprioPassagList();\n" +
                    "            for(MovEquipProprioPassagBean movEquipProprioPassagBean : movEquipProprioPassagList){\n" +
                    "                itens.add(movEquipProprioPassagBean.getMatricColabMovEquipProprioPassag() + \" - \" + pcpContext.getConfigCTR().getColab(movEquipProprioPassagBean.getMatricColabMovEquipProprioPassag()).getNomeColab());\n" +
                    "            }", getLocalClassName());
            movEquipProprioPassagList = pcpContext.getMovVeicProprioCTR().movEquipProprioPassagList();
            for(MovEquipProprioPassagBean movEquipProprioPassagBean : movEquipProprioPassagList){
                itens.add(movEquipProprioPassagBean.getMatricColabMovEquipProprioPassag() + " - " + pcpContext.getConfigCTR().getColabMatric(movEquipProprioPassagBean.getMatricColabMovEquipProprioPassag()).getNomeColab());
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

        LogProcessoDAO.getInstance().insertLogProcesso("AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        ListView passageiroListView = findViewById(R.id.listaPassageiro);\n" +
                "        passageiroListView.setAdapter(adapterList);", getLocalClassName());
        AdapterList adapterList = new AdapterList(this, itens);
        ListView passageiroListView = findViewById(R.id.listaPassageiro);
        passageiroListView.setAdapter(adapterList);
        passageiroListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("passageiroListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
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

                alerta.show();

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
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){\n" +
                            "                    pcpContext.getConfigCTR().setPosicaoTela(6L);\n" +
                            "                    it = new Intent(ListaPassagColabVisitTercActivity.this, MatricColabActivity.class);", getLocalClassName());
                    pcpContext.getConfigCTR().setPosicaoTela(6L);
                    it = new Intent(ListaPassagColabVisitTercActivity.this, MatricColabActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    pcpContext.getConfigCTR().setPosicaoTela(7L);\n" +
                            "                    it = new Intent(ListaPassagColabVisitTercActivity.this, CpfVisitTercActivity.class);", getLocalClassName());
                    pcpContext.getConfigCTR().setPosicaoTela(7L);
                    it = new Intent(ListaPassagColabVisitTercActivity.this, CpfVisitTercActivity.class);
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
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){
                    it = new Intent(ListaPassagColabVisitTercActivity.this, VeiculoUsinaActivity.class);
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){\n" +
                            "                    it = new Intent(ListaPassagColabVisitTercActivity.this, VeiculoUsinaActivity.class);", getLocalClassName());
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    it = new Intent(ListaPassagColabVisitTercActivity.this, VeiculoVisitTercResidenciaActivity.class);", getLocalClassName());
                    it = new Intent(ListaPassagColabVisitTercActivity.this, VeiculoVisitTercResidenciaActivity.class);
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
                        "            public void onClick(View v) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoTela(4L);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(4L);
                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1){\n" +
                            "                    it = new Intent(ListaPassagColabVisitTercActivity.this, MatricColabActivity.class);", getLocalClassName());
                    it = new Intent(ListaPassagColabVisitTercActivity.this, MatricColabActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    it = new Intent(ListaPassagColabVisitTercActivity.this, CpfVisitTercActivity.class);", getLocalClassName());
                    it = new Intent(ListaPassagColabVisitTercActivity.this, CpfVisitTercActivity.class);
                }
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed() {
    }

}