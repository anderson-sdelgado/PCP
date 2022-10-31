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
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipSegProprioBean;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipSegProprioDAO;

public class ListaVeiculoSecActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private List<MovEquipSegProprioBean> movEquipSegProprioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_veiculo_sec);

        pcpContext = (PCPContext) getApplication();

        Button buttonInserirVeiculoSec = findViewById(R.id.buttonInserirVeiculoSec);
        Button buttonOkVeiculoSec = findViewById(R.id.buttonOkVeiculoSec);
        Button buttonCancVeiculoSec = findViewById(R.id.buttonCancVeiculoSec);

        ArrayList<String> itens = new ArrayList<String>();

        movEquipSegProprioList = pcpContext.getMovimentacaoVeicProprioCTR().movEquipSegProprioList();

        for(MovEquipSegProprioBean movEquipSegProprioBean : movEquipSegProprioList){
            itens.add(String.valueOf(pcpContext.getConfigCTR().getEquipId(movEquipSegProprioBean.getIdMovEquipSegProprio()).getNroEquip()));
        }

        AdapterList adapterList = new AdapterList(this, itens);
        ListView listaVeiculoSec = findViewById(R.id.listaVeiculoSec);
        listaVeiculoSec.setAdapter(adapterList);

        listaVeiculoSec.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaVeiculoSecActivity.this);
                alerta.setTitle("ATENÇÃO");
                String label = "DESEJA EXCLUIR O VEÍCULO?";
                alerta.setMessage(label);

                alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {\n" +
                                "                        pcpContext.getMovimentacaoVeicProprioCTR().deleteMovEquipSegProprio(movEquipSegProprioList.get(position));", getLocalClassName());
                        pcpContext.getMovimentacaoVeicProprioCTR().deleteMovEquipSegProprio(movEquipSegProprioList.get(position));
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

        buttonInserirVeiculoSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonInserirVeiculoSec.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoTela(5L);\n" +
                        "                Intent it = new Intent(ListaVeiculoSecActivity.this, VeiculoUsinaActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(5L);
                Intent it = new Intent(ListaVeiculoSecActivity.this, VeiculoUsinaActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonOkVeiculoSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkVeiculoSec.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaVeiculoSecActivity.this, DestinoActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaVeiculoSecActivity.this, DestinoActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonCancVeiculoSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancVeiculoSec.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaVeiculoSecActivity.this, VeiculoUsinaActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(4L);
                Intent it = new Intent(ListaVeiculoSecActivity.this, VeiculoUsinaActivity.class);
                startActivity(it);
                finish();
            }
        });

    }
}