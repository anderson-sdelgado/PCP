package br.com.usinasantafe.pcp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class VeiculoVisitTercResidenciaActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private EditText editTextVeiculoVisitanteTerceiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculo_visit_terc_residencia);

        pcpContext = (PCPContext) getApplication();

        editTextVeiculoVisitanteTerceiro = findViewById(R.id.editTextVeiculoVisitTercResidencia);
        Button buttonOkVeiculoVisitanteTerceiro =  findViewById(R.id.buttonOkVeiculoVisitTercResidencia);
        Button buttonCancVeiculoVisitanteTerceiro = findViewById(R.id.buttonCancVeiculoVisitTercResidencia);

        buttonOkVeiculoVisitanteTerceiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkVeiculoVisitanteTerceiro.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if(!editTextVeiculoVisitanteTerceiro.getText().toString().trim().equals("")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if(!editTextVeiculoVisitanteTerceiro.getText().toString().equals(\"\")) {", getLocalClassName());
                    if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                                "                        pcpContext.getMovVeicVisitTercCTR().setVeiculoVisitTerc(editTextVeiculoVisitanteTerceiro.getText().toString());", getLocalClassName());
                        pcpContext.getMovVeicVisitTercCTR().setVeiculoVisitTerc(editTextVeiculoVisitanteTerceiro.getText().toString());
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        pcpContext.getMovVeicResidenciaCTR().setVeiculoResidencia(editTextVeiculoVisitanteTerceiro.getText().toString());", getLocalClassName());
                        pcpContext.getMovVeicResidenciaCTR().setVeiculoResidencia(editTextVeiculoVisitanteTerceiro.getText().toString());
                    }
                    LogProcessoDAO.getInstance().insertLogProcesso("Intent it  = new Intent(VeiculoVisitTercResidenciaActivity.this, PlacaVisitTercResidenciaActivity.class);", getLocalClassName());
                    Intent it  = new Intent(VeiculoVisitTercResidenciaActivity.this, PlacaVisitTercResidenciaActivity.class);
                    startActivity(it);
                    finish();
                } else {
                    String msg;
                    if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                                "                        msg = \"POR FAVOR, DIGITE O VEÍCULO DO TERCEIRO/VISITANTE!\";", getLocalClassName());
                        msg = "POR FAVOR, DIGITE O VEÍCULO DO TERCEIRO/VISITANTE!";
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        msg = \"POR FAVOR, DIGITE O VEÍCULO DO VISITANTE RESIDÊNCIA!\";", getLocalClassName());
                        msg = "POR FAVOR, DIGITE O VEÍCULO DO VISITANTE RESIDÊNCIA!";
                    }
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "AlertDialog.Builder alerta = new AlertDialog.Builder(VeiculoVisitanteTerceiroActivity.this);\n" +
                            "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                        alerta.setMessage(msg);\n" +
                            "                        alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                            "                            @Override\n" +
                            "                            public void onClick(DialogInterface dialog, int which) {\n" +
                            "                            }\n" +
                            "                        });\n" +
                            "                        alerta.show();", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(VeiculoVisitTercResidenciaActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage(msg);
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alerta.show();
                }
            }
        });

        buttonCancVeiculoVisitanteTerceiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancVeiculoVisitanteTerceiro.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                            "                    it = new Intent(VeiculoVisitTercResidenciaActivity.this, VisitTercActivity.class);", getLocalClassName());
                    it = new Intent(VeiculoVisitTercResidenciaActivity.this, CPFVisitTercActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    it = new Intent(VeiculoVisitTercResidenciaActivity.this, VisitanteResidenciaActivity.class);", getLocalClassName());
                    it = new Intent(VeiculoVisitTercResidenciaActivity.this, VisitanteResidenciaActivity.class);
                }
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed()  {
    }

}