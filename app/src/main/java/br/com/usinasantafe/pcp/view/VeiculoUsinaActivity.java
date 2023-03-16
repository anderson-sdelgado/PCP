package br.com.usinasantafe.pcp.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class VeiculoUsinaActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculo_usina);

        pcpContext = (PCPContext) getApplication();

        Button buttonOkVeicUsina = findViewById(R.id.buttonOkPadrao);
        Button buttonCancVeicUsina = findViewById(R.id.buttonCancPadrao);
        Button buttonAtualPadrao = findViewById(R.id.buttonAtualPadrao);

        buttonAtualPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonAtualPadrao.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                AlertDialog.Builder alerta = new AlertDialog.Builder(VeiculoUsinaActivity.this);\n" +
                        "                alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                alerta.setMessage(\"DESEJA REALMENTE ATUALIZAR BASE DE DADOS?\");", getLocalClassName());
                AlertDialog.Builder alerta = new AlertDialog.Builder(VeiculoUsinaActivity.this);
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
                                    "progressBar = new ProgressDialog(VeiculoUsinaActivity.this);\n" +
                                    "                            progressBar.setCancelable(true);\n" +
                                    "                            progressBar.setMessage(\"ATUALIZANDO ...\");\n" +
                                    "                            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);\n" +
                                    "                            progressBar.setProgress(0);\n" +
                                    "                            progressBar.setMax(100);\n" +
                                    "                            progressBar.show();", getLocalClassName());
                            progressBar = new ProgressDialog(VeiculoUsinaActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("ATUALIZANDO ...");
                            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressBar.setProgress(0);
                            progressBar.setMax(100);
                            progressBar.show();

                            LogProcessoDAO.getInstance().insertLogProcesso("pmmContext.getMotoMecFertCTR().atualDados(VeiculoUsinaActivity.this, VeiculoUsinaActivity.class, progressBar, \"Equip\", 1, getLocalClassName());", getLocalClassName());
                            pcpContext.getConfigCTR().atualDados(VeiculoUsinaActivity.this, VeiculoUsinaActivity.class, progressBar, "Equip", 1, getLocalClassName());

                        } else {

                            LogProcessoDAO.getInstance().insertLogProcesso("AlertDialog.Builder alerta = new AlertDialog.Builder(VeiculoUsinaActivity.this);\n" +
                                    "                            alerta.setTitle(\"ATENÇÃO\");\n" +
                                    "                            alerta.setMessage(\"FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");\n" +
                                    "                            alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                    "                                @Override\n" +
                                    "                                public void onClick(DialogInterface dialog, int which) {\n" +
                                    "                                }\n" +
                                    "                            });\n" +
                                    "                            alerta.show();", getLocalClassName());
                            AlertDialog.Builder alerta = new AlertDialog.Builder(VeiculoUsinaActivity.this);
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

        buttonOkVeicUsina.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("rawtypes")
            @Override
            public void onClick(View v) {

                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkMotorista.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @SuppressWarnings(\"rawtypes\")\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if (!editTextPadrao.getText().toString().equals("")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (!editTextPadrao.getText().toString().equals(\"\")) {", getLocalClassName());
                    if (pcpContext.getConfigCTR().verEquipNro(Long.parseLong(editTextPadrao.getText().toString()))) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if (pcpContext.getConfigCTR().verColab(Long.parseLong(editTextPadrao.getText().toString()))) {", getLocalClassName());
                        Intent it;
                        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L) {
                            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L) {\n" +
                                    "                            pcpContext.getMovVeicProprioCTR().setEquip(Long.parseLong(editTextPadrao.getText().toString()));\n" +
                                    "                            it = new Intent(VeiculoUsinaActivity.this, ListaVeiculoSegActivity.class);", getLocalClassName());
                            pcpContext.getMovVeicProprioCTR().setNroEquipProprio(Long.parseLong(editTextPadrao.getText().toString()));
                            it = new Intent(VeiculoUsinaActivity.this, ListaVeiculoSegActivity.class);
                        } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 5L){
                            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 5L){\n" +
                                    "                            pcpContext.getMovVeicProprioCTR().inserirMovEquipProprioSeg(Long.parseLong(editTextPadrao.getText().toString()));\n" +
                                    "                            it = new Intent(VeiculoUsinaActivity.this, ListaVeiculoSegActivity.class);", getLocalClassName());
                            pcpContext.getMovVeicProprioCTR().inserirMovEquipProprioSeg(Long.parseLong(editTextPadrao.getText().toString()));
                            it = new Intent(VeiculoUsinaActivity.this, ListaVeiculoSegActivity.class);
                        } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){
                            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){\n" +
                                    "                            pcpContext.getMovVeicProprioCTR().setEquip(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), Long.parseLong(editTextPadrao.getText().toString()));\n" +
                                    "                            it = new Intent(VeiculoUsinaActivity.this, DescrMovActivity.class);", getLocalClassName());
                            pcpContext.getMovVeicProprioCTR().setNroEquipProprio(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), Long.parseLong(editTextPadrao.getText().toString()));
                            it = new Intent(VeiculoUsinaActivity.this, DescrMovActivity.class);
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "                            pcpContext.getMovVeicProprioCTR().inserirMovEquipProprioSeg(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), Long.parseLong(editTextPadrao.getText().toString()));\n" +
                                    "                            it = new Intent(VeiculoUsinaActivity.this, ListaVeiculoSegActivity.class);", getLocalClassName());
                            pcpContext.getMovVeicProprioCTR().inserirMovEquipProprioSeg(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), Long.parseLong(editTextPadrao.getText().toString()));
                            it = new Intent(VeiculoUsinaActivity.this, ListaVeiculoSegActivity.class);
                        }
                        LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(VeiculoUsinaActivity.this, ListaVeiculoSecActivity.class);", getLocalClassName());
                        startActivity(it);
                        finish();
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "AlertDialog.Builder alerta = new AlertDialog.Builder(ColabActivity.this);\n" +
                                "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                                "                        alerta.setMessage(\"NUMERAÇÃO DE EQUIPAMENTO INVÁLIDA! FAVOR, VERIFICAR A NUMERAÇÃO DIGITADA.\");\n" +
                                "                        alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                "                            @Override\n" +
                                "                            public void onClick(DialogInterface dialog, int which) {\n" +
                                "                            }\n" +
                                "                        });\n" +
                                "                        alerta.show();", getLocalClassName());
                        AlertDialog.Builder alerta = new AlertDialog.Builder(VeiculoUsinaActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("NUMERAÇÃO DE EQUIPAMENTO INVÁLIDA! FAVOR, VERIFICAR A NUMERAÇÃO DIGITADA.");
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

        buttonCancVeicUsina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancMotorista.setOnClickListener(new View.OnClickListener() {\n" +
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
        LogProcessoDAO.getInstance().insertLogProcesso("public void onBackPressed() {", getLocalClassName());
        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L) {
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                    "            Intent it = new Intent(VeiculoUsinaActivity.this, ListaMovActivity.class);", getLocalClassName());
            Intent it = new Intent(VeiculoUsinaActivity.this, ListaMovProprioActivity.class);
            startActivity(it);
            finish();
        } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){
            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){\n" +
                    "            Intent it = new Intent(VeiculoUsinaActivity.this, DescrMovActivity.class);", getLocalClassName());
            Intent it = new Intent(VeiculoUsinaActivity.this, DescrMovActivity.class);
            startActivity(it);
            finish();
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            Intent it = new Intent(VeiculoUsinaActivity.this, ListaVeiculoSegActivity.class);", getLocalClassName());
            Intent it = new Intent(VeiculoUsinaActivity.this, ListaVeiculoSegActivity.class);
            startActivity(it);
            finish();
        }
    }

}