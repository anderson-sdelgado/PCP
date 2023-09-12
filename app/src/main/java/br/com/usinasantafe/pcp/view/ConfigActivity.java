package br.com.usinasantafe.pcp.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import br.com.usinasantafe.pcp.BuildConfig;
import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class ConfigActivity extends ActivityGeneric {

    private ProgressDialog progressBar;
    private EditText editTextLinhaConfig;
    private EditText editTextSenhaConfig;
    private PCPContext pcpContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Button buttonSalvarConfig =  findViewById(R.id.buttonSalvarConfig);
        Button buttonCancConfig = findViewById(R.id.buttonCancConfig);
        Button buttonAtualizarBD = findViewById(R.id.buttonAtualizarBD);
        editTextLinhaConfig = findViewById(R.id.editTextLinhaConfig);
        editTextSenhaConfig = findViewById(R.id.editTextSenhaConfig);

        pcpContext = (PCPContext) getApplication();

        if (pcpContext.getConfigCTR().hasElemConfig()) {
            LogProcessoDAO.getInstance().insertLogProcesso("if (pcpContext.getConfigCTR().hasElemConfig()) {\n" +
                    "            editTextLinhaConfig.setText(String.valueOf(pcpContext.getConfigCTR().getConfig().getNumLinhaConfig()));\n" +
                    "            editTextSenhaConfig.setText(pcpContext.getConfigCTR().getConfig().getSenhaConfig());", getLocalClassName());
            editTextLinhaConfig.setText(String.valueOf(pcpContext.getConfigCTR().getConfig().getNroAparelhoConfig()));
            editTextSenhaConfig.setText(pcpContext.getConfigCTR().getConfig().getSenhaConfig());
        }

        buttonSalvarConfig.setOnClickListener(v -> {

            LogProcessoDAO.getInstance().insertLogProcesso("buttonSalvarConfig.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());
            if(!editTextLinhaConfig.getText().toString().equals("") &&
                    !editTextSenhaConfig.getText().toString().equals("")){

                LogProcessoDAO.getInstance().insertLogProcesso("if(!editTextLinhaConfig.getText().toString().equals(\"\") &&\n" +
                        "                    !editTextSenhaConfig.getText().toString().equals(\"\")){\n" +
                        "\n" +
                        "                progressBar = new ProgressDialog(v.getContext());\n" +
                        "                progressBar.setCancelable(true);\n" +
                        "                progressBar.setMessage(\"Pequisando o Equipamento...\");\n" +
                        "                progressBar.show();\n" +
                        "                pcpContext.getConfigCTR().verAplic( editTextSenhaConfig.getText().toString(), BuildConfig.VERSION_NAME, Long.valueOf(editTextLinhaConfig.getText().toString()), ConfigActivity.this, TelaInicialActivity.class, progressBar, getLocalClassName());", getLocalClassName());
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("Pequisando o Equipamento...");
                progressBar.show();
                pcpContext.getConfigCTR().verAplic( editTextSenhaConfig.getText().toString(), BuildConfig.VERSION_NAME, Long.valueOf(editTextLinhaConfig.getText().toString()), ConfigActivity.this, TelaInicialActivity.class, progressBar, getLocalClassName());

            }

        });

        buttonCancConfig.setOnClickListener(v -> {

            LogProcessoDAO.getInstance().insertLogProcesso("buttonCancConfig.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());
            LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(ConfigActivity.this, TelaInicialActivity.class);", getLocalClassName());
            Intent it = new Intent(ConfigActivity.this, TelaInicialActivity.class);
            startActivity(it);
            finish();

        });

        buttonAtualizarBD.setOnClickListener(v -> {

            LogProcessoDAO.getInstance().insertLogProcesso("        buttonAtualizarBD.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n", getLocalClassName());

            if(connectNetwork){

                LogProcessoDAO.getInstance().insertLogProcesso("if(connectNetwork){\n" +
                        "                    progressBar = new ProgressDialog(v.getContext());\n" +
                        "                    progressBar.setCancelable(true);\n" +
                        "                    progressBar.setMessage(\"ATUALIZANDO ...\");\n" +
                        "                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);\n" +
                        "                    progressBar.setProgress(0);\n" +
                        "                    progressBar.setMax(100);\n" +
                        "                    progressBar.show();", getLocalClassName());
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("ATUALIZANDO ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                LogProcessoDAO.getInstance().insertLogProcesso("pmmContext.getConfigCTR().atualTodasTabelas(ConfigActivity.this, progressBar);", getLocalClassName());
                pcpContext.getConfigCTR().atualTodasTabelas(ConfigActivity.this, progressBar, getLocalClassName());

            }
            else{

                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                    AlertDialog.Builder alerta = new AlertDialog.Builder(ConfigActivity.this);\n" +
                        "                    alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                    alerta.setMessage(\"FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");\n" +
                        "                    alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                        "                        @Override\n" +
                        "                        public void onClick(DialogInterface dialog, int which) {\n" +
                        "                    });\n" +
                        "                    alerta.show();", getLocalClassName());
                AlertDialog.Builder alerta = new AlertDialog.Builder(ConfigActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                alerta.setPositiveButton("OK", (dialog, which) -> {
                });
                alerta.show();
            }
        });

    }

    public void onBackPressed()  {
    }

}