package br.com.usinasantafe.pcp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercBean;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class ListaMovVisitTercActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private List<MovEquipVisitTercBean> movEquipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mov_visit_terc);

        pcpContext = (PCPContext) getApplication();

        Button buttonEntradaMov = findViewById(R.id.buttonEntradaMovVisitTerc);
        Button buttonCancMov = findViewById(R.id.buttonCancMovVisitTerc);

        TextView textViewVigia = findViewById(R.id.textViewVigiaMovVisitTerc);

        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() > 0L){\n" +
                "            textViewVigia.setText(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() + \" - \"  + pcpContext.getConfigCTR().getColab(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());\n" +
                "        } else {\n" +
                "            textViewVigia.setText(\"\");\n" +
                "        }\n" +
                "pcpContext.getMovVeicVisitTercCTR().deleteMovEquipVisitTercAberto();" +
                "        movEquipList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercList();", getLocalClassName());

        if(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() > 0L){
            textViewVigia.setText(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() + " - "  + pcpContext.getConfigCTR().getColab(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());
        } else {
            textViewVigia.setText("");
        }

        pcpContext.getMovVeicVisitTercCTR().deleteMovEquipVisitTercAberto();
        movEquipList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercEntradaList();

        LogProcessoDAO.getInstance().insertLogProcesso("ListView listaMov = findViewById(R.id.listaMovVisitTerc);\n" +
                "        AdapterListMovVisitTerc adapterListMovVisitTerc = new AdapterListMovVisitTerc(this, movEquipList);\n" +
                "        listaMov.setAdapter(adapterListMovVisitTerc);", getLocalClassName());
        ListView listaMov = findViewById(R.id.listaMovVisitTerc);
        AdapterListMovVisitTerc adapterListMovVisitTerc = new AdapterListMovVisitTerc(this, movEquipList);
        listaMov.setAdapter(adapterListMovVisitTerc);
        listaMov.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("listaMov.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoListaMov((long) (position));\n" +
                        "                MovEquipVisitTercBean movEquipVisitTercBean = movEquipList.get(position);\n" +
                        "                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaMovVisitTercActivity.this);\n" +
                        "                alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                alerta.setMessage(\"DESEJA REALMENTE DAR SAÍDA DO VEÍCULO \" + movEquipVisitTercBean.getVeiculoMovEquipVisitTerc() + \" - PLACA \" + movEquipVisitTercBean.getPlacaMovEquipVisitTerc() + \"?\");", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoListaMov((long) (position));
                MovEquipVisitTercBean movEquipVisitTercBean = movEquipList.get(position);
                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaMovVisitTercActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("DESEJA REALMENTE DAR SAÍDA DO VEÍCULO " + movEquipVisitTercBean.getVeiculoMovEquipVisitTerc() + " - PLACA " + movEquipVisitTercBean.getPlacaMovEquipVisitTerc() + "?");
                alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {\n" +
                                "                        pcpContext.getConfigCTR().setPosicaoTela(4L);\n" +
                                "                        pcpContext.getMovVeicVisitTercCTR().abrirMovEquipVisitTerc(2L);\n" +
                                "                        Intent it = new Intent(ListaMovVisitTercActivity.this, ObservacaoActivity.class);", getLocalClassName());
                        pcpContext.getConfigCTR().setPosicaoTela(4L);
                        pcpContext.getMovVeicVisitTercCTR().abrirMovEquipVisitTerc(2L);
                        Intent it = new Intent(ListaMovVisitTercActivity.this, ObservacaoActivity.class);
                        startActivity(it);
                        finish();
                    }

                });

                alerta.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setNegativeButton(\"NÃO\", new DialogInterface.OnClickListener() {", getLocalClassName());
                    }
                });

                alerta.show();

            }

        });

        buttonEntradaMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonEntradaMov.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoTela(4L);\n" +
                        "                pcpContext.getMovVeicVisitTercCTR().abrirMovEquipVisitTerc(1L);\n" +
                        "                Intent it = new Intent(ListaMovVisitTercActivity.this, TipoVisitTercActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(4L);
                pcpContext.getMovVeicVisitTercCTR().abrirMovEquipVisitTerc(1L);
                Intent it = new Intent(ListaMovVisitTercActivity.this, TipoVisitTercActivity.class);
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
                        "                Intent it = new Intent(ListaMovVisitTercActivity.this, TelaInicialActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaMovVisitTercActivity.this, TelaInicialActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

    public void onBackPressed() {
    }

}