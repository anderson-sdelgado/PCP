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
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioSegBean;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class ListaVeiculoSegActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private List<MovEquipProprioSegBean> movEquipSegProprioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_veiculo_seg);

        pcpContext = (PCPContext) getApplication();

        Button buttonInserirVeiculoSeg = findViewById(R.id.buttonInserirVeiculoSeg);
        Button buttonOkVeiculoSeg = findViewById(R.id.buttonOkVeiculoSeg);
        Button buttonCancVeiculoSeg = findViewById(R.id.buttonCancVeiculoSeg);

        ArrayList<String> itens = new ArrayList<String>();

        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() < 7L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() < 7L){\n" +
                    "            movEquipSegProprioList = pcpContext.getMovVeicProprioCTR().movEquipProprioSegAbertoList();", getLocalClassName());
            movEquipSegProprioList = pcpContext.getMovVeicProprioCTR().movEquipProprioSegAbertoList();
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            movEquipSegProprioList = pcpContext.getMovVeicProprioCTR().movEquipProprioSegFechadoList(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());", getLocalClassName());
            movEquipSegProprioList = pcpContext.getMovVeicProprioCTR().movEquipProprioSegFechadoList(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue());
        }

        LogProcessoDAO.getInstance().insertLogProcesso("for(MovEquipProprioSegBean movEquipProprioSegBean : movEquipSegProprioList){\n" +
                "            itens.add(String.valueOf(pcpContext.getConfigCTR().getEquipId(movEquipProprioSegBean.getIdEquipMovEquipProprioSeg()).getNroEquip()));\n" +
                "        }", getLocalClassName());
        for(MovEquipProprioSegBean movEquipProprioSegBean : movEquipSegProprioList){
            itens.add(String.valueOf(pcpContext.getConfigCTR().getEquipId(movEquipProprioSegBean.getIdEquipMovEquipProprioSeg()).getNroEquip()));
        }

        LogProcessoDAO.getInstance().insertLogProcesso("AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        ListView listViewVeiculoSeg = findViewById(R.id.listViewVeiculoSeg);\n" +
                "        listViewVeiculoSeg.setAdapter(adapterList);", getLocalClassName());
        AdapterList adapterList = new AdapterList(this, itens);
        ListView listViewVeiculoSeg = findViewById(R.id.listViewVeiculoSeg);
        listViewVeiculoSeg.setAdapter(adapterList);
        listViewVeiculoSeg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("listViewVeiculoSeg.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaVeiculoSegActivity.this);\n" +
                        "                alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                String label = \"DESEJA EXCLUIR O VEÍCULO?\";\n" +
                        "                alerta.setMessage(label);", getLocalClassName());
                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaVeiculoSegActivity.this);
                alerta.setTitle("ATENÇÃO");
                String label = "DESEJA EXCLUIR O VEÍCULO?";
                alerta.setMessage(label);
                alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {\n" +
                                "                        pcpContext.getMovVeicProprioCTR().deleteMovEquipSegProprio(movEquipSegProprioList.get(position));\n" +
                                "                        Intent it = new Intent(ListaVeiculoSecActivity.this, ListaVeiculoSecActivity.class);", getLocalClassName());
                        pcpContext.getMovVeicProprioCTR().deleteMovEquipProprioSeg(movEquipSegProprioList.get(position));
                        Intent it = new Intent(ListaVeiculoSegActivity.this, ListaVeiculoSegActivity.class);
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

        buttonInserirVeiculoSeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonInserirVeiculoSeg.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() < 7L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() < 7L){\n" +
                            "                    pcpContext.getConfigCTR().setPosicaoTela(5L);", getLocalClassName());
                    pcpContext.getConfigCTR().setPosicaoTela(5L);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    pcpContext.getConfigCTR().setPosicaoTela(9L);", getLocalClassName());
                    pcpContext.getConfigCTR().setPosicaoTela(9L);
                }
                Intent it = new Intent(ListaVeiculoSegActivity.this, VeiculoUsinaActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonOkVeiculoSeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkVeiculoSeg.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() < 7L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() < 7L){\n" +
                            "                    pcpContext.getConfigCTR().setPosicaoTela(4L);\n" +
                            "                    it = new Intent(ListaVeiculoSegActivity.this, DestinoActivity.class);", getLocalClassName());
                    pcpContext.getConfigCTR().setPosicaoTela(4L);
                    it = new Intent(ListaVeiculoSegActivity.this, DestinoActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    it = new Intent(ListaVeiculoSegActivity.this, DescrMovActivity.class);", getLocalClassName());
                    it = new Intent(ListaVeiculoSegActivity.this, DescrMovActivity.class);
                }
                startActivity(it);
                finish();
            }
        });

        buttonCancVeiculoSeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancVeiculoSeg.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() < 7L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() < 7L){\n" +
                            "                    pcpContext.getConfigCTR().setPosicaoTela(4L);\n" +
                            "                    it = new Intent(ListaVeiculoSegActivity.this, VeiculoUsinaActivity.class);", getLocalClassName());
                    pcpContext.getConfigCTR().setPosicaoTela(4L);
                    it = new Intent(ListaVeiculoSegActivity.this, VeiculoUsinaActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    it = new Intent(ListaVeiculoSegActivity.this, DescrMovActivity.class);", getLocalClassName());
                    it = new Intent(ListaVeiculoSegActivity.this, DescrMovActivity.class);
                }
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed() {
    }

}