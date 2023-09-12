package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class NotaFiscalActivity extends ActivityGeneric {

    private PCPContext pcpContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_fiscal);

        pcpContext = (PCPContext) getApplication();

        Button buttonOkNotaFiscal = findViewById(R.id.buttonOkPadrao);
        Button buttonCancNotaFiscal = findViewById(R.id.buttonCancPadrao);
        editTextPadrao = findViewById(R.id.editTextPadrao);

        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){\n" +
                    "            editTextPadrao.setText(pcpContext.getMovVeicProprioCTR().getMovEquipProprioFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getNroNotaFiscalMovEquipProprio().toString());", getLocalClassName());
            editTextPadrao.setText(pcpContext.getMovVeicProprioCTR().getMovEquipProprioFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getNroNotaFiscalMovEquipProprio().toString());
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            editTextPadrao.setText(\"\");", getLocalClassName());
            editTextPadrao.setText("");
        }

        buttonOkNotaFiscal.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonOkNotaFiscal.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @SuppressWarnings(\"rawtypes\")\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());
            if (!editTextPadrao.getText().toString().equals("")) {
                LogProcessoDAO.getInstance().insertLogProcesso("if (!editTextPadrao.getText().toString().equals(\"\")) {", getLocalClassName());
                if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                            "                    pcpContext.getMovimentacaoVeicProprioCTR().setNroNotaFiscal(Long.valueOf(editTextPadrao.getText().toString()));", getLocalClassName());
                    pcpContext.getMovVeicProprioCTR().setNroNotaFiscalProprio(Long.valueOf(editTextPadrao.getText().toString()));
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                        pcpContext.getMovVeicProprioCTR().setNroNotaFiscal(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), Long.valueOf(editTextPadrao.getText().toString()));", getLocalClassName());
                    pcpContext.getMovVeicProprioCTR().setNroNotaFiscalProprio(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), Long.valueOf(editTextPadrao.getText().toString()));
                }
            }
            Intent it;
            if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                        "                    it = new Intent(NotaFiscalActivity.this, ObservacaoActivity.class);", getLocalClassName());
                it = new Intent(NotaFiscalActivity.this, ObservActivity.class);
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                    it = new Intent(NotaFiscalActivity.this, DescrMovActivity.class);", getLocalClassName());
                it = new Intent(NotaFiscalActivity.this, DescrMovActivity.class);
            }
            startActivity(it);
            finish();

        });

        buttonCancNotaFiscal.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonCancNotaFiscal.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "if (editTextPadrao.getText().toString().length() > 0) {\n" +
                    "                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));\n" +
                    "                }", getLocalClassName());
            if (editTextPadrao.getText().toString().length() > 0) {
                editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
            }
        });
    }

    public void onBackPressed() {
        LogProcessoDAO.getInstance().insertLogProcesso("public void onBackPressed() {", getLocalClassName());
        Intent it;
        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                    "            it = new Intent(NotaFiscalActivity.this, DestinoActivity.class);", getLocalClassName());
            it = new Intent(NotaFiscalActivity.this, DestinoActivity.class);
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "                    it = new Intent(NotaFiscalActivity.this, DescrMovActivity.class);", getLocalClassName());
            it = new Intent(NotaFiscalActivity.this, DescrMovActivity.class);
        }
        startActivity(it);
        finish();
    }

}