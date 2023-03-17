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

import java.util.ArrayList;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;

public class ListaMenuApontActivity extends ActivityGeneric {

    private PCPContext pcpContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_menu_apont);

        pcpContext = (PCPContext) getApplication();

        Button buttonMovProprio = findViewById(R.id.buttonMovProprio);
        Button buttonMovVisitTerc = findViewById(R.id.buttonMovVisitTerc);
        Button buttonMovResidencia = findViewById(R.id.buttonMovResidencia);
        Button buttonSairMenuApont = findViewById(R.id.buttonSairMenuApont);

        TextView textViewVigia = findViewById(R.id.textViewVigia);
        TextView textViewLocal = findViewById(R.id.textViewLocal);

        LogProcessoDAO.getInstance().insertLogProcesso("textViewVigia.setText(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() + \" - \"  + pcpContext.getConfigCTR().getColabMatric(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());\n" +
                "        textViewLocal.setText(\"LOCAL: \" + pcpContext.getConfigCTR().getLocal().getDescrLocal());\n" +
                "        ArrayList<String> itens = new ArrayList<String>();\n" +
                "        itens.add(\"VEÍCULO PRÓPRIO: \" + pcpContext.getMovVeicProprioCTR().qtdeMovEquipProprioFechado());\n" +
                "        itens.add(\"VEÍCULO DE VISITANTE/TERCEIRO: \" + pcpContext.getMovVeicVisitTercCTR().qtdeMovEquipVisitTercFechado());\n" +
                "        itens.add(\"VÉICULO RESIDÊNCIA: \" + pcpContext.getMovVeicResidenciaCTR().qtdeMovEquipResidenciaFechado());\n" +
                "        AdapterList adapterList = new AdapterList(this, itens);\n" +
                "        ListView listViewMenuApont = findViewById(R.id.listViewMenuApont);\n" +
                "        listViewMenuApont.setAdapter(adapterList);", getLocalClassName());

        textViewVigia.setText(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig() + " - "  + pcpContext.getConfigCTR().getColabMatric(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());
        textViewLocal.setText("LOCAL: " + pcpContext.getConfigCTR().getLocal().getDescrLocal());

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("VEÍCULO PRÓPRIO: " + pcpContext.getMovVeicProprioCTR().qtdeMovEquipProprioFechado());
        itens.add("VEÍCULO DE VISITANTE/TERCEIRO: " + pcpContext.getMovVeicVisitTercCTR().qtdeMovEquipVisitTercFechado());
        itens.add("VÉICULO RESIDÊNCIA: " + pcpContext.getMovVeicResidenciaCTR().qtdeMovEquipResidenciaFechado());

        AdapterList adapterList = new AdapterList(this, itens);
        ListView listViewMenuApont = findViewById(R.id.listViewMenuApont);
        listViewMenuApont.setAdapter(adapterList);
        listViewMenuApont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("listViewMenuApont.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {", getLocalClassName());
                switch (position){
                    case 0:
                        LogProcessoDAO.getInstance().insertLogProcesso("switch (position){\n" +
                                "                    case 0:\n" +
                                "                        pcpContext.getConfigCTR().setTipoMov(1L);", getLocalClassName());
                        pcpContext.getConfigCTR().setTipoMov(1L);
                        break;
                    case 1:
                        LogProcessoDAO.getInstance().insertLogProcesso("case 1:\n" +
                                "                        pcpContext.getConfigCTR().setTipoMov(2L);", getLocalClassName());
                        pcpContext.getConfigCTR().setTipoMov(2L);
                        break;
                    default:
                        LogProcessoDAO.getInstance().insertLogProcesso("default:\n" +
                                "                        pcpContext.getConfigCTR().setTipoMov(3L);", getLocalClassName());
                        pcpContext.getConfigCTR().setTipoMov(3L);
                        break;
                }

                LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(ListaMenuApontActivity.this, ListaMovAbertoActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaMenuApontActivity.this, ListaMovFinalizadoActivity.class);
                startActivity(it);
                finish();

            }

        });

        buttonMovProprio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonMovProprio.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                pcpContext.getConfigCTR().setTipoMov(1L);\n" +
                        "                Intent it = new Intent(ListaMenuApontActivity.this, ListaMovProprioActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setTipoMov(1L);
                Intent it = new Intent(ListaMenuApontActivity.this, ListaMovProprioActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonMovVisitTerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonMovVisitTerc.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                pcpContext.getConfigCTR().setTipoMov(2L);\n" +
                        "                Intent it = new Intent(ListaMenuApontActivity.this, ListaMovVisitTercActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setTipoMov(2L);
                Intent it = new Intent(ListaMenuApontActivity.this, ListaMovVisitTercActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonMovResidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonMovResidencia.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                pcpContext.getConfigCTR().setTipoMov(3L);\n" +
                        "                Intent it = new Intent(ListaMenuApontActivity.this, ListaMovResidenciaActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setTipoMov(3L);
                Intent it = new Intent(ListaMenuApontActivity.this, ListaMovResidenciaActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonSairMenuApont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogProcessoDAO.getInstance().insertLogProcesso("buttonSairMenuApont.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaMenuApontActivity.this);\n" +
                        "                alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                alerta.setMessage(\"SE SAIR DESSA TELA TODOS OS MOVIMENTOS SERÃO FECHADOS. DESEJA REALMENTE SAIR? \");", getLocalClassName());
                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaMenuApontActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("SE SAIR DESSA TELA TODOS OS MOVIMENTOS SERÃO FECHADOS. DESEJA REALMENTE SAIR? ");
                alerta.setNegativeButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setNegativeButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {\n" +
                                "                        pcpContext.getMovVeicProprioCTR().atualizarEnviarMovEquipProprio();\n" +
                                "                        pcpContext.getMovVeicVisitTercCTR().atualizarEnviarMovEquipVisitTerc();\n" +
                                "                        pcpContext.getMovVeicResidenciaCTR().atualizarEnviarMovEquipResidencia();\n" +
                                "                        EnvioDadosServ.getInstance().envioDados(getLocalClassName());\n" +
                                "                        Intent it = new Intent(ListaMenuApontActivity.this, MenuInicialActivity.class);", getLocalClassName());
                        pcpContext.getMovVeicProprioCTR().atualizarEnviarMovEquipProprio();
                        pcpContext.getMovVeicVisitTercCTR().atualizarEnviarMovEquipVisitTerc();
                        pcpContext.getMovVeicResidenciaCTR().atualizarEnviarMovEquipResidencia();
                        EnvioDadosServ.getInstance().envioDados(getLocalClassName());
                        Intent it = new Intent(ListaMenuApontActivity.this, MenuInicialActivity.class);
                        startActivity(it);
                        finish();

                    }
                });

                alerta.setPositiveButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"NÃO\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                    }
                });
                alerta.show();

            }
        });

    }

    public void onBackPressed() {
    }
}