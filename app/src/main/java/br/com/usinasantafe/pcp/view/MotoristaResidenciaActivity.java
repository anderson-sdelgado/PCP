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

public class MotoristaResidenciaActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private EditText editTextMotoristaResidencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorista_residencia);

        pcpContext = (PCPContext) getApplication();

        editTextMotoristaResidencia = findViewById(R.id.editTextMotoristaResidencia);
        Button buttonOkMotoristaResidencia =  findViewById(R.id.buttonOkMotoristaResidencia);
        Button buttonCancMotoristaResidencia = findViewById(R.id.buttonCancMotoristaResidencia);

        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){\n" +
                    "            editTextMotoristaResidencia.setText(pcpContext.getMovVeicResidenciaCTR().getMovEquipResidenciaFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getNomeVisitanteMovEquipResidencia());", getLocalClassName());
            editTextMotoristaResidencia.setText(pcpContext.getMovVeicResidenciaCTR().getMovEquipResidenciaFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getNomeVisitanteMovEquipResidencia());
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            editTextMotoristaResidencia.setText(\"\");", getLocalClassName());
            editTextMotoristaResidencia.setText("");
        }

        buttonOkMotoristaResidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkMotoristaResidencia.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if(!editTextMotoristaResidencia.getText().toString().trim().equals("")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if(!editTextVisitanteResidencia.getText().toString().equals(\"\")) {", getLocalClassName());
                    Intent it;
                    if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                                "                        pcpContext.getMovVeicResidenciaCTR().setNomeVisitanteResidencia(editTextVisitanteResidencia.getText().toString());\n" +
                                "                        it  = new Intent(VisitanteResidenciaActivity.this, VeiculoVisitTercResidenciaActivity.class);", getLocalClassName());
                        pcpContext.getMovVeicResidenciaCTR().setNomeVisitanteResidencia(editTextMotoristaResidencia.getText().toString());
                        it  = new Intent(MotoristaResidenciaActivity.this, VeiculoVisitTercResidActivity.class);
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        pcpContext.getMovVeicResidenciaCTR().setNomeVisitanteResidencia(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextVisitanteResidencia.getText().toString());\n" +
                                "                        it  = new Intent(VisitanteResidenciaActivity.this, DescrMovActivity.class);", getLocalClassName());
                        pcpContext.getMovVeicResidenciaCTR().setNomeVisitanteResidencia(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextMotoristaResidencia.getText().toString());
                        it  = new Intent(MotoristaResidenciaActivity.this, DescrMovActivity.class);
                    }
                    startActivity(it);
                    finish();
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "AlertDialog.Builder alerta = new AlertDialog.Builder(VeiculoVisitanteTerceiroActivity.this);\n" +
                            "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                            "                        alerta.setMessage(\"POR FAVOR, DIGITE O NOME DO VISITANTE DA RESIDÊNCIA!\");\n" +
                            "                        alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                            "                            @Override\n" +
                            "                            public void onClick(DialogInterface dialog, int which) {\n" +
                            "                            }\n" +
                            "                        });\n" +
                            "                        alerta.show();", getLocalClassName());
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MotoristaResidenciaActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("POR FAVOR, DIGITE O NOME DO VISITANTE DA RESIDÊNCIA!");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alerta.show();
                }
            }
        });

        buttonCancMotoristaResidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancMotoristaResidencia.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                            "                    it = new Intent(VisitanteResidenciaActivity.this, ListaMovResidenciaActivity.class);", getLocalClassName());
                    it = new Intent(MotoristaResidenciaActivity.this, ListaMovResidenciaActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    it = new Intent(VisitanteResidenciaActivity.this, DescrMovActivity.class);", getLocalClassName());
                    it = new Intent(MotoristaResidenciaActivity.this, DescrMovActivity.class);
                }
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed()  {
    }

}