package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class SenhaActivity extends ActivityGeneric {

    private EditText editTextSenha;
    private PCPContext pcpContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha);

        editTextSenha = findViewById(R.id.editTextSenha);
        Button buttonOkSenha = findViewById(R.id.buttonOkSenha);
        Button buttonCancSenha = findViewById(R.id.buttonCancSenha);

        pcpContext = (PCPContext) getApplication();

        buttonOkSenha.setOnClickListener(v -> {

            LogProcessoDAO.getInstance().insertLogProcesso("btOkSenha.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @SuppressWarnings(\"unchecked\")\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());
            if (!pcpContext.getConfigCTR().hasElemConfig()) {
                LogProcessoDAO.getInstance().insertLogProcesso("if (!pmmContext.getConfigCTR().hasElemConfig()) {\n" +
                        "Intent it = new Intent(SenhaActivity.this, ConfigActivity.class);", getLocalClassName());
                Intent it = new Intent(SenhaActivity.this, ConfigActivity.class);
                startActivity(it);
                finish();
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                if (pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 1L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if (pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 1L){", getLocalClassName());
                    if (pcpContext.getConfigCTR().verSenha(editTextSenha.getText().toString())) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if (pmmContext.getConfigCTR().verSenha(editTextSenha.getText().toString())) {\n" +
                                "Intent it = new Intent(SenhaActivity.this, ConfigActivity.class);", getLocalClassName());
                        Intent it = new Intent(SenhaActivity.this, ConfigActivity.class);
                        startActivity(it);
                        finish();

                    }
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                    if (editTextSenha.getText().toString().equals("fgbny946")) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if (editTextSenha.getText().toString().equals(\"fgbny946\")) {\n" +
                                "Intent it = new Intent(SenhaActivity.this, LogProcessoActivity.class);", getLocalClassName());
                        Intent it = new Intent(SenhaActivity.this, LogProcessoActivity.class);
                        startActivity(it);
                        finish();
                    }
                }
            }


        });

        buttonCancSenha.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonCancSenha.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "                Intent it = new Intent(SenhaActivity.this, TelaInicialActivity.class);", getLocalClassName());
            Intent it = new Intent(SenhaActivity.this, TelaInicialActivity.class);
            startActivity(it);
            finish();
        });

    }

    public void onBackPressed()  {
    }

}