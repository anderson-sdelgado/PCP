package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class ObservActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private EditText editTextObserv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observ);

        pcpContext = (PCPContext) getApplication();

        editTextObserv = findViewById(R.id.editTextObserv);
        Button buttonOkObserv =  findViewById(R.id.buttonOkObserv);
        Button buttonCancObserv = findViewById(R.id.buttonCancObserv);

        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){", getLocalClassName());
            if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {\n" +
                        "                editTextObserv.setText(pcpContext.getMovVeicProprioCTR().getMovEquipProprioFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getObservMovEquipProprio());", getLocalClassName());
                editTextObserv.setText(pcpContext.getMovVeicProprioCTR().getMovEquipProprioFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getObservMovEquipProprio());
            } else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L) {
                LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L) {\n" +
                        "                editTextObserv.setText(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getObservMovEquipVisitTerc());", getLocalClassName());
                editTextObserv.setText(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getObservMovEquipVisitTerc());
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                editTextObserv.setText(pcpContext.getMovVeicResidenciaCTR().getMovEquipResidenciaFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getObservMovEquipResidencia());", getLocalClassName());
                editTextObserv.setText(pcpContext.getMovVeicResidenciaCTR().getMovEquipResidenciaFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getObservMovEquipResidencia());
            }
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            editTextObserv.setText(\"\");", getLocalClassName());
            editTextObserv.setText("");
        }

        buttonOkObserv.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonOkObserv.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());
            String observacao = null;
            if(!editTextObserv.getText().toString().equals("")) {
                LogProcessoDAO.getInstance().insertLogProcesso("if(!editTextObservacao.getText().toString().equals(\"\")) {\n" +
                        "                    observacao = editTextObservacao.getText().toString();", getLocalClassName());
                observacao = editTextObserv.getText().toString();
            }

            Intent it;
            if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4){", getLocalClassName());
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {\n" +
                            "                    pcpContext.getMovVeicProprioCTR().fecharMovEquipProprio(observacao, getLocalClassName());\n" +
                            "                    it = new Intent(ObservacaoActivity.this, ListaMovProprioActivity.class);", getLocalClassName());
                    pcpContext.getMovVeicProprioCTR().finalizarMovEquipProprio(observacao);
                    it = new Intent(ObservActivity.this, ListaMovProprioActivity.class);
                } else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L) {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L)  {\n" +
                            "                    pcpContext.getMovVeicVisitTercCTR().fecharMovEquipVisitTerc(observacao, getLocalClassName());\n" +
                            "                    it = new Intent(ObservacaoActivity.this, ListaMovVisitTercActivity.class);", getLocalClassName());
                    pcpContext.getMovVeicVisitTercCTR().finalizarMovEquipVisitTerc(observacao);
                    it = new Intent(ObservActivity.this, ListaMovVisitTercActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else { \n" +
                            "                    pcpContext.getMovVeicResidenciaCTR().fecharMovEquipResidencia(observacao, getLocalClassName());\n" +
                            "                    it = new Intent(ObservacaoActivity.this, ListaMovResidenciaActivity.class);", getLocalClassName());
                    pcpContext.getMovVeicResidenciaCTR().finalizarMovEquipResidencia(observacao);
                    it = new Intent(ObservActivity.this, ListaMovResidenciaActivity.class);
                }
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {\n" +
                            "                        pcpContext.getMovVeicProprioCTR().setObservacao(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), observacao);", getLocalClassName());
                    pcpContext.getMovVeicProprioCTR().setObservacaoProprio(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), observacao);
                } else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L) {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L) {\n" +
                            "                        pcpContext.getMovVeicVisitTercCTR().setObservacaoVisitTerc(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), observacao);", getLocalClassName());
                    pcpContext.getMovVeicVisitTercCTR().setObservacaoVisitTerc(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), observacao);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                        pcpContext.getMovVeicResidenciaCTR().setObservacaoResidencia(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), observacao);", getLocalClassName());
                    pcpContext.getMovVeicResidenciaCTR().setObservacaoResidencia(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), observacao);
                }
                it = new Intent(ObservActivity.this, DescrMovActivity.class);
            }
            startActivity(it);
            finish();

        });

        buttonCancObserv.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonCancObserv.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());
            Intent it;
            if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4){", getLocalClassName());
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {", getLocalClassName());
                    if (pcpContext.getMovVeicProprioCTR().getMovEquipProprioAberto().getTipoMovEquipProprio() == 1L) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if (pcpContext.getMovVeicProprioCTR().getMovEquipProprioAberto().getTipoMovEquipProprio() == 1L) {\n" +
                                "                        it = new Intent(ObservacaoActivity.this, DestinoActivity.class);", getLocalClassName());
                        it = new Intent(ObservActivity.this, DestinoActivity.class);
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else { \n" +
                                "                        it = new Intent(ObservacaoActivity.this, NotaFiscalActivity.class);", getLocalClassName());
                        it = new Intent(ObservActivity.this, NotaFiscalActivity.class);
                    }
                } else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L) {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L) {", getLocalClassName());
                    if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoMovEquipVisitTerc() == 1L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoMovEquipVisitTerc() == 1L){\n" +
                                "                        it = new Intent(ObservacaoActivity.this, DestinoActivity.class);", getLocalClassName());
                        it = new Intent(ObservActivity.this, DestinoActivity.class);
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        it = new Intent(ObservacaoActivity.this, ListaMovVisitTercActivity.class);", getLocalClassName());
                        it = new Intent(ObservActivity.this, ListaMovVisitTercActivity.class);
                    }
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                    if(pcpContext.getMovVeicResidenciaCTR().getMovEquipResidenciaAberto().getTipoMovEquipResidencia() == 1L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovVeicResidenciaCTR().getMovEquipResidenciaAberto().getTipoMovEquipResidencia() == 1L){\n" +
                                "                        it = new Intent(ObservacaoActivity.this, PlacaVisitTercResidenciaActivity.class);", getLocalClassName());
                        it = new Intent(ObservActivity.this, PlacaVisitTercResidActivity.class);
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        it = new Intent(ObservacaoActivity.this, ListaMovResidenciaActivity.class);", getLocalClassName());
                        it = new Intent(ObservActivity.this, ListaMovResidenciaActivity.class);
                    }
                }
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                    it = new Intent(ObservacaoActivity.this, DescrMovActivity.class);", getLocalClassName());
                it = new Intent(ObservActivity.this, DescrMovActivity.class);
            }
            startActivity(it);
            finish();
        });

    }

    public void onBackPressed()  {
    }

}