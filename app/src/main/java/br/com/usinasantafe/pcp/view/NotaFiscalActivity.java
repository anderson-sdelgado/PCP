package br.com.usinasantafe.pcp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

        buttonOkNotaFiscal.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("rawtypes")
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkNotaFiscal.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @SuppressWarnings(\"rawtypes\")\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if (editTextPadrao.getText().toString().equals("")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (editTextPadrao.getText().toString().equals(\"\")) {\n" +
                            "                    pcpContext.getMovimentacaoVeicProprioCTR().setNroNotaFiscal(0L);", getLocalClassName());
                    pcpContext.getMovimentacaoVeicProprioCTR().setNroNotaFiscal(0L);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    pcpContext.getMovimentacaoVeicProprioCTR().setNroNotaFiscal(Long.valueOf(editTextPadrao.getText().toString()));", getLocalClassName());
                    pcpContext.getMovimentacaoVeicProprioCTR().setNroNotaFiscal(Long.valueOf(editTextPadrao.getText().toString()));
                }
                LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(NotaFiscalActivity.this, ObservacaoActivity.class);", getLocalClassName());
                Intent it = new Intent(NotaFiscalActivity.this, ObservacaoActivity.class);
                startActivity(it);
                finish();

            }

        });

        buttonCancNotaFiscal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancNotaFiscal.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "if (editTextPadrao.getText().toString().length() > 0) {\n" +
                        "                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));\n" +
                        "                }", getLocalClassName());
                if (editTextPadrao.getText().toString().length() > 0) {
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                }
            }
        });
    }

    public void onBackPressed() {
        LogProcessoDAO.getInstance().insertLogProcesso("public void onBackPressed() {\n" +
                "        Intent it = new Intent(NotaFiscalActivity.this, DestinoActivity.class);", getLocalClassName());
        Intent it = new Intent(NotaFiscalActivity.this, DestinoActivity.class);
        startActivity(it);
        finish();
    }

}