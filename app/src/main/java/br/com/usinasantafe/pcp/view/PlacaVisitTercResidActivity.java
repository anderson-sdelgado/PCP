package br.com.usinasantafe.pcp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class PlacaVisitTercResidActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private EditText editTextPlacaVisitTercResid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placa_visit_terc_resid);

        pcpContext = (PCPContext) getApplication();

        editTextPlacaVisitTercResid = findViewById(R.id.editTextPlacaVisitTercResid);
        Button buttonOkPlacaVisitTercResid =  findViewById(R.id.buttonOkPlacaVisitTercResid);
        Button buttonCancPlacaVisitTercResid = findViewById(R.id.buttonCancPlacaVisitTercResid);

        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){", getLocalClassName());
            if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                        "                editTextPlacaVisitTercResid.setText(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getPlacaMovEquipVisitTerc());", getLocalClassName());
                editTextPlacaVisitTercResid.setText(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getPlacaMovEquipVisitTerc());
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                editTextPlacaVisitTercResid.setText(pcpContext.getMovVeicResidenciaCTR().getMovEquipResidenciaFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getPlacaMovEquipResidencia());", getLocalClassName());
                editTextPlacaVisitTercResid.setText(pcpContext.getMovVeicResidenciaCTR().getMovEquipResidenciaFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getPlacaMovEquipResidencia());
            }
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            editTextPlacaVisitTercResid.setText(\"\");", getLocalClassName());
            editTextPlacaVisitTercResid.setText("");
        }

        editTextPlacaVisitTercResid.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                editTextPlacaVisitTercResid.getText().toString().toUpperCase();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

        });

        buttonOkPlacaVisitTercResid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkPlacaVisitTercResid.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if(!editTextPlacaVisitTercResid.getText().toString().trim().equals("")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if(!editTextPlacaVisitanteTerceiroResidencia.getText().toString().equals(\"\")) {", getLocalClassName());
                    Intent it;
                    if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){", getLocalClassName());
                        if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                                    "                        pcpContext.getMovVeicVisitTercCTR().setPlacaVisitTerc(editTextPlacaVisitanteTerceiro.getText().toString());", getLocalClassName());
                            pcpContext.getMovVeicVisitTercCTR().setPlacaVisitTerc(editTextPlacaVisitTercResid.getText().toString());
                            if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoMovEquipVisitTerc() == 1L){
                                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovimentacaoVeicVisTercCTR().getMovEquipVisitTercAberto().getTipoMovEquipVisitTerc() == 1L){\n" +
                                        "                        it  = new Intent(PlacaVisitanteTerceiroActivity.this, DestinoActivity.class);", getLocalClassName());
                                it  = new Intent(PlacaVisitTercResidActivity.this, DestinoActivity.class);
                            } else {
                                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                        "                        it  = new Intent(PlacaVisitanteTerceiroActivity.this, ObservacaoActivity.class);", getLocalClassName());
                                it  = new Intent(PlacaVisitTercResidActivity.this, ObservActivity.class);
                            }
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                        pcpContext.getMovVeicResidenciaCTR().setPlacaResidencia(editTextPlacaVisitanteTerceiro.getText().toString());\n" +
                                    "                        it  = new Intent(PlacaVisitTercResidenciaActivity.this, ObservacaoActivity.class);", getLocalClassName());
                            pcpContext.getMovVeicResidenciaCTR().setPlacaResidencia(editTextPlacaVisitTercResid.getText().toString());
                            it  = new Intent(PlacaVisitTercResidActivity.this, ObservActivity.class);
                        }
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                        if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                                    "                            pcpContext.getMovVeicVisitTercCTR().setPlacaVisitTerc(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextPlacaVisitTercResidencia.getText().toString());", getLocalClassName());
                            pcpContext.getMovVeicVisitTercCTR().setPlacaVisitTerc(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextPlacaVisitTercResid.getText().toString());
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                            pcpContext.getMovVeicResidenciaCTR().setPlacaResidencia(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextPlacaVisitTercResidencia.getText().toString());", getLocalClassName());
                            pcpContext.getMovVeicResidenciaCTR().setPlacaResidencia(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextPlacaVisitTercResid.getText().toString());
                        }
                        LogProcessoDAO.getInstance().insertLogProcesso("it  = new Intent(PlacaVisitTercResidenciaActivity.this, DescrMovActivity.class);", getLocalClassName());
                        it  = new Intent(PlacaVisitTercResidActivity.this, DescrMovActivity.class);
                    }
                    startActivity(it);
                    finish();
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "AlertDialog.Builder alerta = new AlertDialog.Builder(PlacaVisitanteTerceiroActivity.this);\n" +
                            "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                        alerta.setMessage(\"POR FAVOR, DIGITE A PLACA DO VEÍCULO DO TERCEIRO/VISITANTE!\");\n" +
                            "                        alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                            "                            @Override\n" +
                            "                            public void onClick(DialogInterface dialog, int which) {\n" +
                            "                            }\n" +
                            "                        });\n" +
                            "                        alerta.show();", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(PlacaVisitTercResidActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("POR FAVOR, DIGITE A PLACA DO VEÍCULO DO TERCEIRO/VISITANTE!");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alerta.show();
                }
            }
        });

        buttonCancPlacaVisitTercResid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancPlacaVisitTercResid.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                            "                    it = new Intent(PlacaVisitTercResidenciaActivity.this, VeiculoVisitTercResidenciaActivity.class);", getLocalClassName());
                    it = new Intent(PlacaVisitTercResidActivity.this, VeiculoVisitTercResidActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    it = new Intent(PlacaVisitTercResidenciaActivity.this, DescrMovActivity.class);", getLocalClassName());
                    it = new Intent(PlacaVisitTercResidActivity.this, DescrMovActivity.class);
                }
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed()  {
    }

}