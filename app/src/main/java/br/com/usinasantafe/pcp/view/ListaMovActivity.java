package br.com.usinasantafe.pcp.view;

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
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class ListaMovActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private List movEquipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mov);

        pcpContext = (PCPContext) getApplication();

        Button buttonEntradaMov = findViewById(R.id.buttonEntradaMov);
        Button buttonSaidaMov = findViewById(R.id.buttonSaidaMov);
        Button buttonCancMov = findViewById(R.id.buttonCancMov);
        TextView textViewTituloMov = findViewById(R.id.textViewTituloMov);

        if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                    "            textViewTituloMov.setText(\"CONTROLE VEÍCULO USINA\");\n" +
                    "            movEquipList = pcpContext.getMovimentacaoVeicProprioCTR().movEquipProprioAllList();", getLocalClassName());
            textViewTituloMov.setText("CONTROLE VEÍCULO USINA");
            movEquipList = pcpContext.getMovimentacaoVeicProprioCTR().movEquipProprioAllList();
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            textViewTituloMov.setText(\"CONTROLE VEÍCULO VISITANTE/TERCEIRO\");\n" +
                    "            movEquipList = pcpContext.getMovimentacaoVeicVisTercCTR().movEquipVisitTercAllList();", getLocalClassName());
            textViewTituloMov.setText("CONTROLE VEÍCULO VISITANTE/TERCEIRO");
            movEquipList = pcpContext.getMovimentacaoVeicVisitTercCTR().movEquipVisitTercAllList();
        }

        LogProcessoDAO.getInstance().insertLogProcesso("ListView listaMov = findViewById(R.id.listaMov);\n" +
                "        AdapterListMov adapterListMov = new AdapterListMov(this, movEquipList, tipo);\n" +
                "        listaMov.setAdapter(adapterListMov);", getLocalClassName());
        ListView listaMov = findViewById(R.id.listaMov);
        AdapterListMov adapterListMov = new AdapterListMov(this, movEquipList, pcpContext.getConfigCTR().getConfig().getTipoMov());
        listaMov.setAdapter(adapterListMov);

        listaMov.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                LogProcessoDAO.getInstance().insertLogProcesso("tipoFuncaoLeiraListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                pmmContext.getCompostoCTR().setTipoMovLeira((long) (position + 1));\n" +
                        "                Intent it = new Intent(ListaTipoMovLeiraActivity.this, ListaLeiraActivity.class);", getLocalClassName());

                pcpContext.getConfigCTR().setPosicaoListaMov((long) (position + 1));
                Intent it = new Intent(ListaMovActivity.this, DescrMovActivity.class);
                startActivity(it);
                finish();

            }

        });

        buttonEntradaMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonEntradaMov.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoTela(4L);\n" +
                        "                Intent it;", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(4L);
                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){\n" +
                            "                    pcpContext.getMovimentacaoVeicProprioCTR().abrirMovEquipProprio(1L);\n" +
                            "                    it = new Intent(ListaMovActivity.this, ColabActivity.class);", getLocalClassName());
                    pcpContext.getMovimentacaoVeicProprioCTR().abrirMovEquipProprio(1L);
                    it = new Intent(ListaMovActivity.this, ColabActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    pcpContext.getMovimentacaoVeicVisTercCTR().abrirMovEquipVisitTerc(1L);\n" +
                            "                    it = new Intent(ListaMovActivity.this, TipoVisitanteTerceiroActivity.class);", getLocalClassName());
                    pcpContext.getMovimentacaoVeicVisitTercCTR().abrirMovEquipVisitTerc(1L);
                    it = new Intent(ListaMovActivity.this, TipoVisitTercActivity.class);
                }
                startActivity(it);
                finish();
            }
        });

        buttonSaidaMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonSaidaMov.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoTela(5L);\n" +
                        "                Intent it;", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoTela(4L);
                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){\n" +
                            "                    pcpContext.getMovimentacaoVeicProprioCTR().abrirMovEquipProprio(2L);\n" +
                            "                    it = new Intent(ListaMovActivity.this, ColabActivity.class);", getLocalClassName());
                    pcpContext.getMovimentacaoVeicProprioCTR().abrirMovEquipProprio(2L);
                    it = new Intent(ListaMovActivity.this, ColabActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    pcpContext.getMovimentacaoVeicVisTercCTR().abrirMovEquipVisitTerc(2L);\n" +
                            "                    it = new Intent(ListaMovActivity.this, TipoVisitanteTerceiroActivity.class);", getLocalClassName());
                    pcpContext.getMovimentacaoVeicVisitTercCTR().abrirMovEquipVisitTerc(2L);
                    it = new Intent(ListaMovActivity.this, TipoVisitTercActivity.class);
                }
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
                        "                Intent it = new Intent(ListaMovActivity.this, TelaInicialActivity.class);", getLocalClassName());
                Intent it = new Intent(ListaMovActivity.this, TelaInicialActivity.class);
                startActivity(it);
                finish();
            }
        });
    }
}