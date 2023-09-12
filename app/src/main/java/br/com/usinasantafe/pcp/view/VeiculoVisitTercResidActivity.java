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

public class VeiculoVisitTercResidActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private EditText editTextVeicVisitTercResid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculo_visit_terc_resid);

        pcpContext = (PCPContext) getApplication();

        editTextVeicVisitTercResid = findViewById(R.id.editTextVeicVisitTercResid);
        Button buttonOkVeicVisitTercResid =  findViewById(R.id.buttonOkVeicVisitTercResid);
        Button buttonCancVeicVisitTercResid = findViewById(R.id.buttonCancVeicVisitTercResid);

        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){", getLocalClassName());
            if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                        "                editTextVeicVisitTercResid.setText(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getVeiculoMovEquipVisitTerc());", getLocalClassName());
                editTextVeicVisitTercResid.setText(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getVeiculoMovEquipVisitTerc());
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                        "                editTextVeicVisitTercResid.setText(pcpContext.getMovVeicResidenciaCTR().getMovEquipResidenciaFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getVeiculoMovEquipResidencia());", getLocalClassName());
                editTextVeicVisitTercResid.setText(pcpContext.getMovVeicResidenciaCTR().getMovEquipResidenciaFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getVeiculoMovEquipResidencia());
            }
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            editTextVeicVisitTercResid.setText(\"\");", getLocalClassName());
            editTextVeicVisitTercResid.setText("");
        }

        buttonOkVeicVisitTercResid.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonOkVeicVisitTercResid.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());
            if(!editTextVeicVisitTercResid.getText().toString().trim().equals("")) {
                LogProcessoDAO.getInstance().insertLogProcesso("if(!editTextVeiculoVisitanteTerceiro.getText().toString().equals(\"\")) {", getLocalClassName());
                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){", getLocalClassName());
                    if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                                "                        pcpContext.getMovVeicVisitTercCTR().setVeiculoVisitTerc(editTextVeiculoVisitanteTerceiro.getText().toString());", getLocalClassName());
                        pcpContext.getMovVeicVisitTercCTR().setVeiculoVisitTerc(editTextVeicVisitTercResid.getText().toString());
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        pcpContext.getMovVeicResidenciaCTR().setVeiculoResidencia(editTextVeiculoVisitanteTerceiro.getText().toString());", getLocalClassName());
                        pcpContext.getMovVeicResidenciaCTR().setVeiculoResidencia(editTextVeicVisitTercResid.getText().toString());
                    }
                    LogProcessoDAO.getInstance().insertLogProcesso("it  = new Intent(VeiculoVisitTercResidenciaActivity.this, PlacaVisitTercResidenciaActivity.class);", getLocalClassName());
                    it  = new Intent(VeiculoVisitTercResidActivity.this, PlacaVisitTercResidActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                    if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                                "                        pcpContext.getMovVeicVisitTercCTR().setVeiculoVisitTerc(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextVeiculoVisitanteTerceiro.getText().toString());", getLocalClassName());
                        pcpContext.getMovVeicVisitTercCTR().setVeiculoVisitTerc(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextVeicVisitTercResid.getText().toString());
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                            pcpContext.getMovVeicResidenciaCTR().setVeiculoResidencia(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextVeiculoVisitanteTerceiro.getText().toString());", getLocalClassName());
                        pcpContext.getMovVeicResidenciaCTR().setVeiculoResidencia(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextVeicVisitTercResid.getText().toString());
                    }
                    LogProcessoDAO.getInstance().insertLogProcesso("it  = new Intent(VeiculoVisitTercResidenciaActivity.this, DescrMovActivity.class);", getLocalClassName());
                    it  = new Intent(VeiculoVisitTercResidActivity.this, DescrMovActivity.class);
                }
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
                AlertDialog.Builder alerta = new AlertDialog.Builder(VeiculoVisitTercResidActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage(msg);
                alerta.setPositiveButton("OK", (dialog, which) -> {
                });
                alerta.show();
            }
        });

        buttonCancVeicVisitTercResid.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonCancVeicVisitTercResid.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());
            Intent it;
            if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){", getLocalClassName());
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                            "                    it = new Intent(VeiculoVisitTercResidActivity.this, ListaMovVisitTercActivity.class);", getLocalClassName());
                    it = new Intent(VeiculoVisitTercResidActivity.this, ListaMovVisitTercActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    it = new Intent(VeiculoVisitTercResidActivity.this, ListaMovResidenciaActivity.class);", getLocalClassName());
                    it = new Intent(VeiculoVisitTercResidActivity.this, ListaMovResidenciaActivity.class);
                }
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                    it = new Intent(VeiculoVisitTercResidenciaActivity.this, DescrMovActivity.class);", getLocalClassName());
                it = new Intent(VeiculoVisitTercResidActivity.this, DescrMovActivity.class);
            }
            startActivity(it);
            finish();
        });

    }

    public void onBackPressed()  {
    }

}