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

public class VeiculoVisitTercActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private EditText editTextVeiculoVisitanteTerceiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculo_visit_terc);

        pcpContext = (PCPContext) getApplication();

        editTextVeiculoVisitanteTerceiro = findViewById(R.id.editTextVeiculoVisitanteTerceiro);
        Button buttonOkVeiculoVisitanteTerceiro =  findViewById(R.id.buttonOkVeiculoVisitanteTerceiro);
        Button buttonCancVeiculoVisitanteTerceiro = findViewById(R.id.buttonCancVeiculoVisitanteTerceiro);

        buttonOkVeiculoVisitanteTerceiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkVeiculoVisitanteTerceiro.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if(!editTextVeiculoVisitanteTerceiro.getText().toString().equals("")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if(!editTextVeiculoVisitanteTerceiro.getText().toString().equals(\"\")) {\n" +
                            "                    pcpContext.getMovimentacaoVeicVisTercCTR().setVeiculoVisitTerc(editTextVeiculoVisitanteTerceiro.getText().toString());\n" +
                            "                    Intent it  = new Intent(VeiculoVisitanteTerceiroActivity.this, PlacaVisitanteTerceiroActivity.class);", getLocalClassName());
                    pcpContext.getMovVeicVisitTercCTR().setVeiculoVisitTerc(editTextVeiculoVisitanteTerceiro.getText().toString());
                    Intent it  = new Intent(VeiculoVisitTercActivity.this, PlacaVisitTercActivity.class);
                    startActivity(it);
                    finish();
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "AlertDialog.Builder alerta = new AlertDialog.Builder(VeiculoVisitanteTerceiroActivity.this);\n" +
                            "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                        alerta.setMessage(\"POR FAVOR, DIGITE O VEÍCULO DO TERCEIRO/VISITANTE!\");\n" +
                            "                        alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                            "                            @Override\n" +
                            "                            public void onClick(DialogInterface dialog, int which) {\n" +
                            "                            }\n" +
                            "                        });\n" +
                            "                        alerta.show();", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(VeiculoVisitTercActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("POR FAVOR, DIGITE O VEÍCULO DO TERCEIRO/VISITANTE!");
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
                        "            public void onClick(View v) {\n" +
                        "                Intent it = new Intent(VeiculoVisitanteTerceiroActivity.this, VisitanteTerceiroActivity.class);", getLocalClassName());
                Intent it = new Intent(VeiculoVisitTercActivity.this, VisitTercActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed()  {
    }

}