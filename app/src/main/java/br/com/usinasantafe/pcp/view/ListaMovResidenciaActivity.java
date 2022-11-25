package br.com.usinasantafe.pcp.view;

import androidx.appcompat.app.AppCompatActivity;

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
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipResidenciaBean;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class ListaMovResidenciaActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private List<MovEquipResidenciaBean> movEquipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mov_residencia);

        pcpContext = (PCPContext) getApplication();

        Button buttonEntradaMov = findViewById(R.id.buttonEntradaResidencia);
        Button buttonCancMov = findViewById(R.id.buttonCancMovResidencia);

        TextView textViewVigia = findViewById(R.id.textViewVigiaMovResidencia);

        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() > 0L){\n" +
                "            textViewVigia.setText(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() + \" - \"  + pcpContext.getConfigCTR().getColab(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());\n" +
                "        } else {\n" +
                "            textViewVigia.setText(\"\");\n" +
                "        }\n" +
                "        movEquipList = pcpContext.getMovVeicResidenciaCTR().movEquipResidenciaList();", getLocalClassName());
        if(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() > 0L){
            textViewVigia.setText(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() + " - "  + pcpContext.getConfigCTR().getColab(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());
        } else {
            textViewVigia.setText("");
        }

        movEquipList = pcpContext.getMovVeicResidenciaCTR().movEquipResidenciaList();

        LogProcessoDAO.getInstance().insertLogProcesso("ListView listaMov = findViewById(R.id.listaMovResidencia);\n" +
                "        AdapterListMovResidencia adapterListMovResidencia = new AdapterListMovResidencia(this, movEquipList);\n" +
                "        listaMov.setAdapter(adapterListMovResidencia);", getLocalClassName());
        ListView listaMov = findViewById(R.id.listaMovResidencia);
        AdapterListMovResidencia adapterListMovResidencia = new AdapterListMovResidencia(this, movEquipList);
        listaMov.setAdapter(adapterListMovResidencia);

        listaMov.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("listaMov.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoListaMov((long) (position));\n" +
                        "                MovEquipResidenciaBean movEquipResidenciaBean = movEquipList.get(position);\n" +
                        "                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaMovResidenciaActivity.this);\n" +
                        "                alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                alerta.setMessage(\"DESEJA REALMENTE DAR SAÍDA DO VEÍCULO \" + movEquipResidenciaBean.getVeiculoMovEquipResidencia() + \" - PLACA \" + movEquipResidenciaBean.getPlacaMovEquipResidencia() + \"?\");", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoListaMov((long) (position));
                MovEquipResidenciaBean movEquipResidenciaBean = movEquipList.get(position);
                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaMovResidenciaActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("DESEJA REALMENTE DAR SAÍDA DO VEÍCULO " + movEquipResidenciaBean.getVeiculoMovEquipResidencia() + " - PLACA " + movEquipResidenciaBean.getPlacaMovEquipResidencia() + "?");
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
                        pcpContext.getMovVeicResidenciaCTR().abrirMovEquipResidencia(2L);
                        Intent it = new Intent(ListaMovResidenciaActivity.this, ObservacaoActivity.class);
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
                        "                pcpContext.getMovVeicResidenciaCTR().abrirMovEquipResidencia(1L);\n" +
                        "                Intent it = new Intent(ListaMovResidenciaActivity.this, VisitanteResidenciaActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(4L);
                pcpContext.getMovVeicResidenciaCTR().abrirMovEquipResidencia(1L);
                Intent it = new Intent(ListaMovResidenciaActivity.this, VisitanteResidenciaActivity.class);
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
                        "                Intent it = new Intent(ListaMovResidenciaActivity.this, TelaInicialActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaMovResidenciaActivity.this, TelaInicialActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

    public void onBackPressed() {
    }

}