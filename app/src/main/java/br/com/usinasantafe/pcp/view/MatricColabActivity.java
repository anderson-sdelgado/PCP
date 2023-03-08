package br.com.usinasantafe.pcp.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class MatricColabActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matric_colab);

        pcpContext = (PCPContext) getApplication();

        Button buttonOkMatricColab = findViewById(R.id.buttonOkPadrao);
        Button buttonCancMatricColab = findViewById(R.id.buttonCancPadrao);
        Button buttonAtualPadrao = findViewById(R.id.buttonAtualPadrao);
        TextView textViewPadrao = findViewById(R.id.textViewPadrao);

        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 3L) {
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 3L) {\n" +
                    "            textViewPadrao.setText(\"MATRIC. VIGIA:\");", getLocalClassName());
            textViewPadrao.setText("MATRIC. VIGIA:");
        } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                    "            textViewPadrao.setText(\"MATRIC. MOTORISTA:\");", getLocalClassName());
            textViewPadrao.setText("MATRIC. MOTORISTA:");
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            textViewPadrao.setText(\"MATRIC. PASSAGEIRO:\");", getLocalClassName());
            textViewPadrao.setText("MATRIC. PASSAGEIRO:");
        }

        buttonAtualPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonAtualPadrao.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                AlertDialog.Builder alerta = new AlertDialog.Builder(ColabActivity.this);\n" +
                        "                alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                alerta.setMessage(\"DESEJA REALMENTE ATUALIZAR BASE DE DADOS?\");", getLocalClassName());
                AlertDialog.Builder alerta = new AlertDialog.Builder(MatricColabActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("DESEJA REALMENTE ATUALIZAR BASE DE DADOS?");
                alerta.setNegativeButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setNegativeButton(\"SIM\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName());

                        if (connectNetwork) {

                            LogProcessoDAO.getInstance().insertLogProcesso("if (connectNetwork) {\n" +
                                    "progressBar = new ProgressDialog(ColabActivity.this);\n" +
                                    "                            progressBar.setCancelable(true);\n" +
                                    "                            progressBar.setMessage(\"ATUALIZANDO ...\");\n" +
                                    "                            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);\n" +
                                    "                            progressBar.setProgress(0);\n" +
                                    "                            progressBar.setMax(100);\n" +
                                    "                            progressBar.show();", getLocalClassName());
                            progressBar = new ProgressDialog(MatricColabActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("ATUALIZANDO ...");
                            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressBar.setProgress(0);
                            progressBar.setMax(100);
                            progressBar.show();

                            LogProcessoDAO.getInstance().insertLogProcesso("pmmContext.getMotoMecFertCTR().atualDados(ColabActivity.this, ColabActivity.class, progressBar, \"Colab\", 1, getLocalClassName());", getLocalClassName());
                            pcpContext.getConfigCTR().atualDados(MatricColabActivity.this, MatricColabActivity.class, progressBar, "Colab", 1, getLocalClassName());

                        } else {

                            LogProcessoDAO.getInstance().insertLogProcesso("AlertDialog.Builder alerta = new AlertDialog.Builder(ColabActivity.this);\n" +
                                    "                            alerta.setTitle(\"ATENÇÃO\");\n" +
                                    "                            alerta.setMessage(\"FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");\n" +
                                    "                            alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                    "                                @Override\n" +
                                    "                                public void onClick(DialogInterface dialog, int which) {\n" +
                                    "                                }\n" +
                                    "                            });\n" +
                                    "                            alerta.show();", getLocalClassName());
                            AlertDialog.Builder alerta = new AlertDialog.Builder(MatricColabActivity.this);
                            alerta.setTitle("ATENÇÃO");
                            alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            alerta.show();

                        }


                    }
                });

                alerta.setPositiveButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"NÃO\", new DialogInterface.OnClickListener() {\n" +
                                "                    @Override\n" +
                                "                    public void onClick(DialogInterface dialog, int which) {", getLocalClassName());
                    }
                });
                alerta.show();

            }

        });

        buttonOkMatricColab.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("rawtypes")
            @Override
            public void onClick(View v) {

                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkMatricColab.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @SuppressWarnings(\"rawtypes\")\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if (!editTextPadrao.getText().toString().equals("")) {

                    LogProcessoDAO.getInstance().insertLogProcesso("if (!editTextPadrao.getText().toString().equals(\"\")) {", getLocalClassName());
                    if (pcpContext.getConfigCTR().verColab(Long.parseLong(editTextPadrao.getText().toString()))) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if (pcpContext.getConfigCTR().verColab(Long.parseLong(editTextPadrao.getText().toString()))) {", getLocalClassName());
                        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 3L){
                            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 3L){\n" +
                                    "                            pcpContext.getConfigCTR().setMatricVigia(Long.parseLong(editTextPadrao.getText().toString()));", getLocalClassName());
                            pcpContext.getConfigCTR().setMatricVigia(Long.parseLong(editTextPadrao.getText().toString()));
                        } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                                    "                            pcpContext.getMovimentacaoVeicProprioCTR().abrirMovEquipProprio(Long.parseLong(editTextPadrao.getText().toString()));", getLocalClassName());
                            pcpContext.getMovVeicProprioCTR().setNroMatricColab(Long.parseLong(editTextPadrao.getText().toString()));
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                            pcpContext.getMovVeicProprioCTR().getMovEquipProprioPassagDAO().setMovEquipProprioPassagBean(Long.parseLong(editTextPadrao.getText().toString()));", getLocalClassName());
                            pcpContext.getMovVeicProprioCTR().getMovEquipProprioPassagDAO().setMatricPassagMovEquipProprioPassag(Long.parseLong(editTextPadrao.getText().toString()));
                        }
                        LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(ColabActivity.this, NomeColabTercVisitActivity.class);", getLocalClassName());
                        Intent it = new Intent(MatricColabActivity.this, NomeColabVisitTercActivity.class);
                        startActivity(it);
                        finish();
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        String msg = \"\";", getLocalClassName());
                        String msg = "";
                        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 3L){
                            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 3L){\n" +
                                    "                            msg = \"NUMERAÇÃO DO VIGIA INEXISTENTE! FAVOR VERIFICA A MESMA.\";", getLocalClassName());
                            msg = "NUMERAÇÃO CRACHÁ DO VIGIA INEXISTENTE! FAVOR VERIFICA A MESMA.";
                        } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                                    "                            msg = \"NUMERAÇÃO DO COLABORADOR INEXISTENTE! FAVOR VERIFICA A MESMA.\";", getLocalClassName());
                            msg = "NUMERAÇÃO CRACHÁ DO MOTORISTA INEXISTENTE! FAVOR VERIFICA A MESMA.";
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                            msg = \"NUMERAÇÃO CRACHÁ DO PASSAGEIRO INEXISTENTE! FAVOR VERIFICA A MESMA.\";", getLocalClassName());
                            msg = "NUMERAÇÃO CRACHÁ DO PASSAGEIRO INEXISTENTE! FAVOR VERIFICA A MESMA.";
                        }
                        LogProcessoDAO.getInstance().insertLogProcesso("AlertDialog.Builder alerta = new AlertDialog.Builder(ColabActivity.this);\n" +
                                "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                                "                        alerta.setMessage(msg);\n" +
                                "                        alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                "                            @Override\n" +
                                "                            public void onClick(DialogInterface dialog, int which) {\n" +
                                "                            }\n" +
                                "                        });\n" +
                                "                        alerta.show();", getLocalClassName());
                        AlertDialog.Builder alerta = new AlertDialog.Builder(MatricColabActivity.this);
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

            }

        });

        buttonCancMatricColab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancMatricColab.setOnClickListener(new View.OnClickListener() {\n" +
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
        Intent it;
        LogProcessoDAO.getInstance().insertLogProcesso("public void onBackPressed() {\n" +
                "        Intent it;", getLocalClassName());
        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 3L) {
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 3L){\n" +
                    "            it = new Intent(ColabActivity.this, TelaInicialActivity.class);", getLocalClassName());
            it = new Intent(MatricColabActivity.this, TelaInicialActivity.class);
        } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                    "            it = new Intent(ColabActivity.this, ListaMovActivity.class);", getLocalClassName());
            it = new Intent(MatricColabActivity.this, ListaMovProprioActivity.class);
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            it = new Intent(MatricColabActivity.this, ListaPassagColabVisitTercActivity.class);", getLocalClassName());
            it = new Intent(MatricColabActivity.this, ListaPassagColabVisitTercActivity.class);

        }
        startActivity(it);
        finish();
    }

}