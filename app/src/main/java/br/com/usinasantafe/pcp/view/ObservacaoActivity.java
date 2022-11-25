package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class ObservacaoActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private EditText editTextObservacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observacao);

        pcpContext = (PCPContext) getApplication();

        editTextObservacao = findViewById(R.id.editTextObservacao);
        Button buttonOkObservacao =  findViewById(R.id.buttonOkObservacao);
        Button buttonCancObservacao = findViewById(R.id.buttonCancObservacao);

        buttonOkObservacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkObservacao.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                String observacao = null;
                if(!editTextObservacao.getText().toString().equals("")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if(!editTextObservacao.getText().toString().equals(\"\")) {\n" +
                            "                    observacao = editTextObservacao.getText().toString();", getLocalClassName());
                    observacao = editTextObservacao.getText().toString();
                }

                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {\n" +
                            "                    pcpContext.getMovVeicProprioCTR().fecharMovEquipProprio(observacao, getLocalClassName());\n" +
                            "                    it = new Intent(ObservacaoActivity.this, ListaMovProprioActivity.class);", getLocalClassName());
                    pcpContext.getMovVeicProprioCTR().fecharMovEquipProprio(observacao, getLocalClassName());
                    it = new Intent(ObservacaoActivity.this, ListaMovProprioActivity.class);
                } else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L) {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L)  {\n" +
                            "                    pcpContext.getMovVeicVisitTercCTR().fecharMovEquipVisitTerc(observacao, getLocalClassName());\n" +
                            "                    it = new Intent(ObservacaoActivity.this, ListaMovVisitTercActivity.class);", getLocalClassName());
                    pcpContext.getMovVeicVisitTercCTR().fecharMovEquipVisitTerc(observacao, getLocalClassName());
                    it = new Intent(ObservacaoActivity.this, ListaMovVisitTercActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else { \n" +
                            "                    pcpContext.getMovVeicResidenciaCTR().fecharMovEquipResidencia(observacao, getLocalClassName());\n" +
                            "                    it = new Intent(ObservacaoActivity.this, ListaMovResidenciaActivity.class);", getLocalClassName());
                    pcpContext.getMovVeicResidenciaCTR().fecharMovEquipResidencia(observacao, getLocalClassName());
                    it = new Intent(ObservacaoActivity.this, ListaMovResidenciaActivity.class);
                }
                startActivity(it);
                finish();
            }
        });

        buttonCancObservacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancObservacao.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                Intent it;

                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {", getLocalClassName());
                    if (pcpContext.getMovVeicProprioCTR().getMovEquipProprioAberto().getTipoMovEquipProprio() == 1L) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if (pcpContext.getMovVeicProprioCTR().getMovEquipProprioAberto().getTipoMovEquipProprio() == 1L) {\n" +
                                "                        it = new Intent(ObservacaoActivity.this, DestinoActivity.class);", getLocalClassName());
                        it = new Intent(ObservacaoActivity.this, DestinoActivity.class);
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else { \n" +
                                "                        it = new Intent(ObservacaoActivity.this, NotaFiscalActivity.class);", getLocalClassName());
                        it = new Intent(ObservacaoActivity.this, NotaFiscalActivity.class);
                    }
                } else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L) {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L) {", getLocalClassName());
                    if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoMovEquipVisitTerc() == 1L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoMovEquipVisitTerc() == 1L){\n" +
                                "                        it = new Intent(ObservacaoActivity.this, DestinoActivity.class);", getLocalClassName());
                        it = new Intent(ObservacaoActivity.this, DestinoActivity.class);
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        it = new Intent(ObservacaoActivity.this, ListaMovVisitTercActivity.class);", getLocalClassName());
                        it = new Intent(ObservacaoActivity.this, ListaMovVisitTercActivity.class);
                    }
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                    if(pcpContext.getMovVeicResidenciaCTR().getMovEquipResidenciaAberto().getTipoMovEquipResidencia() == 1L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovVeicResidenciaCTR().getMovEquipResidenciaAberto().getTipoMovEquipResidencia() == 1L){\n" +
                                "                        it = new Intent(ObservacaoActivity.this, PlacaVisitTercResidenciaActivity.class);", getLocalClassName());
                        it = new Intent(ObservacaoActivity.this, PlacaVisitTercResidenciaActivity.class);
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        it = new Intent(ObservacaoActivity.this, ListaMovResidenciaActivity.class);", getLocalClassName());
                        it = new Intent(ObservacaoActivity.this, ListaMovResidenciaActivity.class);
                    }
                }
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed()  {
    }

}