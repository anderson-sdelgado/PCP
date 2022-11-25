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

public class PlacaVisitTercResidenciaActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private EditText editTextPlacaVisitanteTerceiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placa_visit_terc_residencia);

        pcpContext = (PCPContext) getApplication();

        editTextPlacaVisitanteTerceiro = findViewById(R.id.editTextPlacaVisitTerc);
        Button buttonOkPlacaVisitanteTerceiro =  findViewById(R.id.buttonOkPlacaVisitTercResidencia);
        Button buttonCancPlacaVisitanteTerceiro = findViewById(R.id.buttonCancPlacaVisitTercResidencia);

        editTextPlacaVisitanteTerceiro.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                editTextPlacaVisitanteTerceiro.getText().toString().toUpperCase();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        buttonOkPlacaVisitanteTerceiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkPlacaVisitanteTerceiro.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if(!editTextPlacaVisitanteTerceiro.getText().toString().equals("")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if(!editTextPlacaVisitanteTerceiro.getText().toString().equals(\"\")) {", getLocalClassName());
                    Intent it;
                    if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                                "                        pcpContext.getMovVeicVisitTercCTR().setPlacaVisitTerc(editTextPlacaVisitanteTerceiro.getText().toString());", getLocalClassName());
                        pcpContext.getMovVeicVisitTercCTR().setPlacaVisitTerc(editTextPlacaVisitanteTerceiro.getText().toString());
                        if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoMovEquipVisitTerc() == 1L){
                            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovimentacaoVeicVisTercCTR().getMovEquipVisitTercAberto().getTipoMovEquipVisitTerc() == 1L){\n" +
                                    "                        it  = new Intent(PlacaVisitanteTerceiroActivity.this, DestinoActivity.class);", getLocalClassName());
                            it  = new Intent(PlacaVisitTercResidenciaActivity.this, DestinoActivity.class);
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                        it  = new Intent(PlacaVisitanteTerceiroActivity.this, ObservacaoActivity.class);", getLocalClassName());
                            it  = new Intent(PlacaVisitTercResidenciaActivity.this, ObservacaoActivity.class);
                        }
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        pcpContext.getMovVeicResidenciaCTR().setPlacaResidencia(editTextPlacaVisitanteTerceiro.getText().toString());\n" +
                                "                        it  = new Intent(PlacaVisitTercResidenciaActivity.this, ObservacaoActivity.class);", getLocalClassName());
                        pcpContext.getMovVeicResidenciaCTR().setPlacaResidencia(editTextPlacaVisitanteTerceiro.getText().toString());
                        it  = new Intent(PlacaVisitTercResidenciaActivity.this, ObservacaoActivity.class);
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
                    AlertDialog.Builder alerta = new AlertDialog.Builder(PlacaVisitTercResidenciaActivity.this);
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

        buttonCancPlacaVisitanteTerceiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancPlacaVisitanteTerceiro.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(PlacaVisitanteTerceiroActivity.this, VeiculoVisitanteTerceiroActivity.class);", getLocalClassName());
                Intent it = new Intent(PlacaVisitTercResidenciaActivity.this, VeiculoVisitTercResidenciaActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed()  {
    }

}