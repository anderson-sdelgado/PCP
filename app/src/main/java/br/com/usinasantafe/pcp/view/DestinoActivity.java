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

public class DestinoActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private EditText editTextDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destino);

        pcpContext = (PCPContext) getApplication();

        editTextDestino = findViewById(R.id.editTextDestino);
        Button buttonOkDestino =  findViewById(R.id.buttonOkDestino);
        Button buttonCancDestino = findViewById(R.id.buttonCancDestino);

        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){", getLocalClassName());
            if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){\n" +
                        "                editTextDestino.setText(pcpContext.getMovVeicProprioCTR().getMovEquipProprioFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getDestinoMovEquipProprio());", getLocalClassName());
                editTextDestino.setText(pcpContext.getMovVeicProprioCTR().getMovEquipProprioFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getDestinoMovEquipProprio());
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                editTextDestino.setText(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getDestinoMovEquipVisitTerc());", getLocalClassName());
                editTextDestino.setText(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getDestinoMovEquipVisitTerc());
            }
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            editTextDestino.setText(\"\");", getLocalClassName());
            editTextDestino.setText("");
        }

        buttonOkDestino.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonOkDestino.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());
            if(!editTextDestino.getText().toString().equals("")) {
                LogProcessoDAO.getInstance().insertLogProcesso("if(!editTextDestino.getText().toString().equals(\"\")) {", getLocalClassName());
                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){", getLocalClassName());
                    if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){\n" +
                                "                        pcpContext.getMovimentacaoVeicProprioCTR().setDescrDestino(editTextDestino.getText().toString());", getLocalClassName());
                        pcpContext.getMovVeicProprioCTR().setDescrDestinoProprio(editTextDestino.getText().toString());
                        if(pcpContext.getMovVeicProprioCTR().getMovEquipProprioAberto().getTipoMovEquipProprio() == 2L){
                            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovimentacaoVeicProprioCTR().getMovEquipProprioAberto().getTipoMovEquipProprio() == 1L){\n" +
                                    "                        it = new Intent(DestinoActivity.this, ObservacaoActivity.class);", getLocalClassName());
                            it = new Intent(DestinoActivity.this, ObservActivity.class);
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                        it = new Intent(DestinoActivity.this, NotaFiscalActivity.class);", getLocalClassName());
                            it = new Intent(DestinoActivity.this, NotaFiscalActivity.class);
                        }
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("pcpContext.getMovimentacaoVeicVisTercCTR().setDestinoVisitTerc(editTextDestino.getText().toString());\n" +
                                "                        it = new Intent(DestinoActivity.this, ObservacaoActivity.class);", getLocalClassName());
                        pcpContext.getMovVeicVisitTercCTR().setDestinoVisitTerc(editTextDestino.getText().toString());
                        it = new Intent(DestinoActivity.this, ObservActivity.class);
                    }
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                    if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){\n" +
                                "                        pcpContext.getMovVeicProprioCTR().setDescrDestino(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextDestino.getText().toString());", getLocalClassName());
                        pcpContext.getMovVeicProprioCTR().setDescrDestinoProprio(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextDestino.getText().toString());
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                            pcpContext.getMovVeicVisitTercCTR().setDestinoVisitTerc(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextDestino.getText().toString());", getLocalClassName());
                        pcpContext.getMovVeicVisitTercCTR().setDestinoVisitTerc(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), editTextDestino.getText().toString());
                    }
                    it = new Intent(DestinoActivity.this, DescrMovActivity.class);
                }

                startActivity(it);
                finish();
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "AlertDialog.Builder alerta = new AlertDialog.Builder(DestinoActivity.this);\n" +
                        "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                        alerta.setMessage(\"POR FAVOR, DIGITE O DESTINO DO VEÍCULO!\");\n" +
                        "                        alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                        "                            @Override\n" +
                        "                            public void onClick(DialogInterface dialog, int which) {\n" +
                        "                            }\n" +
                        "                        });\n" +
                        "                        alerta.show();", getLocalClassName());
                AlertDialog.Builder alerta = new AlertDialog.Builder(DestinoActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("POR FAVOR, DIGITE O DESTINO DO VEÍCULO!");
                alerta.setPositiveButton("OK", (dialog, which) -> {
                });
                alerta.show();
            }

        });

        buttonCancDestino.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonCancDestino.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());
            Intent it;
            if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){", getLocalClassName());
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L){\n" +
                            "                    it = new Intent(DestinoActivity.this, ListaPassagColabVisitTercActivity.class);", getLocalClassName());
                    it = new Intent(DestinoActivity.this, ListaPassagColabVisitTercActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    it = new Intent(DestinoActivity.this, PlacaVisitanteTerceiroActivity.class);", getLocalClassName());
                    it = new Intent(DestinoActivity.this, PlacaVisitTercResidActivity.class);
                }
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                    it = new Intent(DestinoActivity.this, DescrMovActivity.class);", getLocalClassName());
                it = new Intent(DestinoActivity.this, DescrMovActivity.class);
            }
            startActivity(it);
            finish();
        });

    }

    public void onBackPressed()  {
    }

}