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
        Button buttonRetornarMov = findViewById(R.id.buttonRetornarMovResidencia);

        TextView textViewVigia = findViewById(R.id.textViewVigia);
        TextView textViewLocal = findViewById(R.id.textViewLocal);

        LogProcessoDAO.getInstance().insertLogProcesso("textViewVigia.setText(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() + \" - \"  + pcpContext.getConfigCTR().getColab(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());\n" +
                "        textViewLocal.setText(\"LOCAL: \" + pcpContext.getConfigCTR().getLocal().getDescrLocal());\n" +
                "        pcpContext.getMovVeicResidenciaCTR().deleteMovEquipResidenciaAberto();\n" +
                "        movEquipList = pcpContext.getMovVeicResidenciaCTR().movEquipResidenciaList();\n" +
                "        ListView listViewMov = findViewById(R.id.listaMovResidencia);\n" +
                "        AdapterListMovResidencia adapterListMovVisitTercResid = new AdapterListMovResidencia(this, movEquipList);\n" +
                "        listViewMov.setAdapter(adapterListMovVisitTercResid);", getLocalClassName());

        textViewVigia.setText(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() + " - "  + pcpContext.getConfigCTR().getColabMatric(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());
        textViewLocal.setText("LOCAL: " + pcpContext.getConfigCTR().getLocal().getDescrLocal());

        pcpContext.getMovVeicResidenciaCTR().deleteMovEquipResidenciaAberto();
        movEquipList = pcpContext.getMovVeicResidenciaCTR().movEquipResidenciaList();

        ListView listViewMov = findViewById(R.id.listViewMovResidencia);
        AdapterListMovVisitTercResid adapterListMovVisitTercResid = new AdapterListMovVisitTercResid(this, movEquipList);
        listViewMov.setAdapter(adapterListMovVisitTercResid);

        listViewMov.setOnItemClickListener((l, v, position, id) -> {

            LogProcessoDAO.getInstance().insertLogProcesso("listViewMov.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
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
            alerta.setPositiveButton("SIM", (dialog, which) -> {
                LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                        "                    @Override\n" +
                        "                    public void onClick(DialogInterface dialog, int which) {\n" +
                        "                        pcpContext.getConfigCTR().setPosicaoTela(4L);\n" +
                        "                        pcpContext.getMovVeicVisitTercCTR().abrirMovEquipVisitTerc(2L);\n" +
                        "                        Intent it = new Intent(ListaMovVisitTercActivity.this, ObservacaoActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(4L);
                pcpContext.getMovVeicResidenciaCTR().abrirMovEquipResidencia(2L);
                Intent it = new Intent(ListaMovResidenciaActivity.this, ObservActivity.class);
                startActivity(it);
                finish();
            });

            alerta.setNegativeButton("NÃO", (dialog, which) -> LogProcessoDAO.getInstance().insertLogProcesso("alerta.setNegativeButton(\"NÃO\", new DialogInterface.OnClickListener() {", getLocalClassName()));

            alerta.show();

        });

        buttonEntradaMov.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonEntradaMov.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "                pcpContext.getConfigCTR().setPosicaoTela(4L);\n" +
                    "                pcpContext.getMovVeicResidenciaCTR().abrirMovEquipResidencia(1L);\n" +
                    "                Intent it = new Intent(ListaMovResidenciaActivity.this, VeiculoVisitTercResidActivity.class);", getLocalClassName());
            pcpContext.getConfigCTR().setPosicaoTela(4L);
            pcpContext.getMovVeicResidenciaCTR().abrirMovEquipResidencia(1L);
            Intent it = new Intent(ListaMovResidenciaActivity.this, VeiculoVisitTercResidActivity.class);
            startActivity(it);
            finish();
        });

        buttonRetornarMov.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonRetornarMov.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "                Intent it = new Intent(ListaMovResidenciaActivity.this, ListaMenuApontActivity.class);", getLocalClassName());
            Intent it = new Intent(ListaMovResidenciaActivity.this, ListaMenuApontActivity.class);
            startActivity(it);
            finish();
        });
    }

    public void onBackPressed() {
    }

}