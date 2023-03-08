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

        LogProcessoDAO.getInstance().insertLogProcesso("movEquipSegProprioList = pcpContext.getMovVeicProprioCTR().movEquipProprioSegList();\n" +
                "        for(MovEquipProprioSegBean movEquipProprioSegBean : movEquipSegProprioList){\n" +
                "            itens.add(String.valueOf(pcpContext.getConfigCTR().getEquipId(movEquipProprioSegBean.getIdEquipMovEquipProprioSeg()).getNroEquip()));\n" +
                "        }\n" +
                "        AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        ListView listaVeiculoSec = findViewById(R.id.listaVeiculoSec);\n" +
                "        listaVeiculoSec.setAdapter(adapterList);", getLocalClassName());

        movEquipSegProprioList = pcpContext.getMovVeicProprioCTR().movEquipProprioSegList();

        for(MovEquipProprioSegBean movEquipProprioSegBean : movEquipSegProprioList){
            itens.add(String.valueOf(pcpContext.getConfigCTR().getEquipId(movEquipProprioSegBean.getIdEquipMovEquipProprioSeg()).getNroEquip()));
        }

        AdapterList adapterList = new AdapterList(this, itens);
        ListView listaVeiculoSec = findViewById(R.id.listaVeiculoSec);
        listaVeiculoSec.setAdapter(adapterList);
        listaVeiculoSec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("listaVeiculoSec.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
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
                        "            public void onClick(View v) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoTela(5L);\n" +
                        "                Intent it = new Intent(ListaVeiculoSecActivity.this, VeiculoUsinaActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(5L);
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
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaVeiculoSecActivity.this, DestinoActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaVeiculoSegActivity.this, DestinoActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonCancVeiculoSeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancVeiculoSeg.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaVeiculoSecActivity.this, VeiculoUsinaActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(4L);
                Intent it = new Intent(ListaVeiculoSegActivity.this, VeiculoUsinaActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed() {
    }

}