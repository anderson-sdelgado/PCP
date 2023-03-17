package br.com.usinasantafe.pcp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;

public class ListaMovFinalizadoActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private List movEquipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mov_finalizado);

        pcpContext = (PCPContext) getApplication();

        TextView buttonRetornarMovFinalizado = findViewById(R.id.buttonRetornarMovFinalizado);
        TextView buttonFecharMovFinalizado = findViewById(R.id.buttonFecharMovFinalizado);

        ListView listViewMovFinalizado = findViewById(R.id.listViewMovFinalizado);

        if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){\n" +
                    "            movEquipList = pcpContext.getMovVeicProprioCTR().movEquipProprioFechadoList();\n" +
                    "            AdapterListMovProprio adapterListMovProprio = new AdapterListMovProprio(this, movEquipList);\n" +
                    "            listViewMovFinalizado.setAdapter(adapterListMovProprio);", getLocalClassName());
            movEquipList = pcpContext.getMovVeicProprioCTR().movEquipProprioFechadoList();
            AdapterListMovProprio adapterListMovProprio = new AdapterListMovProprio(this, movEquipList);
            listViewMovFinalizado.setAdapter(adapterListMovProprio);
        } else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                    "            movEquipList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercFechadoList();\n" +
                    "            AdapterListMovVisitTercResidManut adapterListMovVisitTercResidManut = new AdapterListMovVisitTercResidManut(this, movEquipList);\n" +
                    "            listViewMovFinalizado.setAdapter(adapterListMovVisitTercResidManut);", getLocalClassName());
            movEquipList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercFechadoList();
            AdapterListMovVisitTercResidManut adapterListMovVisitTercResidManut = new AdapterListMovVisitTercResidManut(this, movEquipList);
            listViewMovFinalizado.setAdapter(adapterListMovVisitTercResidManut);
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            movEquipList = pcpContext.getMovVeicResidenciaCTR().movEquipResidenciaFechadoList();\n" +
                    "            AdapterListMovVisitTercResidManut adapterListMovVisitTercResidManut = new AdapterListMovVisitTercResidManut(this, movEquipList);\n" +
                    "            listViewMovFinalizado.setAdapter(adapterListMovVisitTercResidManut);", getLocalClassName());
            movEquipList = pcpContext.getMovVeicResidenciaCTR().movEquipResidenciaFechadoList();
            AdapterListMovVisitTercResidManut adapterListMovVisitTercResidManut = new AdapterListMovVisitTercResidManut(this, movEquipList);
            listViewMovFinalizado.setAdapter(adapterListMovVisitTercResidManut);
        }

        listViewMovFinalizado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                LogProcessoDAO.getInstance().insertLogProcesso("listViewMovFinalizado.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoTela(8L);\n" +
                        "                pcpContext.getConfigCTR().setPosicaoListaMov((long) (position));\n" +
                        "                Intent it = new Intent(ListaMovAbertoActivity.this, DescrMovActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(8L);
                pcpContext.getConfigCTR().setPosicaoListaMov((long) (position));
                Intent it = new Intent(ListaMovFinalizadoActivity.this, DescrMovActivity.class);
                startActivity(it);
                finish();
            }

        });

        buttonFecharMovFinalizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonFecharMovFinalizado.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaMovAbertoActivity.this);\n" +
                        "                alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                alerta.setMessage(\"DESEJA REALMENTE FECHAR O(S) MOVIMENTO(S)? \");", getLocalClassName());
                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaMovFinalizadoActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("DESEJA REALMENTE FECHAR O(S) MOVIMENTO(S)? ");
                alerta.setNegativeButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setNegativeButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                        if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){
                            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){\n" +
                                    "                            pcpContext.getMovVeicProprioCTR().atualizarEnviarMovEquipProprio();", getLocalClassName());
                            pcpContext.getMovVeicProprioCTR().atualizarEnviarMovEquipProprio();
                        } else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                                    "                            pcpContext.getMovVeicVisitTercCTR().atualizarEnviarMovEquipVisitTerc(getLocalClassName());", getLocalClassName());
                            pcpContext.getMovVeicVisitTercCTR().atualizarEnviarMovEquipVisitTerc();
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                            pcpContext.getMovVeicResidenciaCTR().atualizarEnviarMovEquipResidencia(getLocalClassName());", getLocalClassName());
                            pcpContext.getMovVeicResidenciaCTR().atualizarEnviarMovEquipResidencia();
                        }
                        LogProcessoDAO.getInstance().insertLogProcesso("EnvioDadosServ.getInstance().envioDados(getLocalClassName());\n" +
                                "                        Intent it = new Intent(ListaMovFinalizadoActivity.this, ListaMenuApontActivity.class);", getLocalClassName());
                        EnvioDadosServ.getInstance().envioDados(getLocalClassName());
                        Intent it = new Intent(ListaMovFinalizadoActivity.this, ListaMenuApontActivity.class);
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

        buttonRetornarMovFinalizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonRetornarMovFinalizado.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(ListaMovFechadoActivity.this, ListaMenuApontActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaMovFinalizadoActivity.this, ListaMenuApontActivity.class);
                startActivity(it);
                finish();
            }
        });
    }

    public void onBackPressed() {
    }

}