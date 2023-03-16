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

public class CPFVisitTercActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpf_visit_terc);

        pcpContext = (PCPContext) getApplication();

        Button buttonOkVisitTerc = findViewById(R.id.buttonOkPadrao);
        Button buttonCancVisitTerc = findViewById(R.id.buttonCancPadrao);
        Button buttonAtualPadrao = findViewById(R.id.buttonAtualPadrao);
        TextView textViewCpfVisitTerc = findViewById(R.id.textViewCpfVisitTerc);

        if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoVisitTercMovEquipVisitTerc() == 2L){
            textViewCpfVisitTerc.setText("CPF TERCEIRO:");
        } else {
            textViewCpfVisitTerc.setText("CPF VISITANTE:");
        }

        buttonAtualPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonAtualPadrao.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {\n" +
                        "                AlertDialog.Builder alerta = new AlertDialog.Builder(VisitanteTerceiroActivity.this);\n" +
                        "                alerta.setTitle(\"ATENÇÃO\");\n" +
                        "                alerta.setMessage(\"DESEJA REALMENTE ATUALIZAR BASE DE DADOS?\");", getLocalClassName());
                AlertDialog.Builder alerta = new AlertDialog.Builder(CPFVisitTercActivity.this);
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
                                    "progressBar = new ProgressDialog(VisitanteTerceiroActivity.this);\n" +
                                    "                            progressBar.setCancelable(true);\n" +
                                    "                            progressBar.setMessage(\"ATUALIZANDO ...\");\n" +
                                    "                            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);\n" +
                                    "                            progressBar.setProgress(0);\n" +
                                    "                            progressBar.setMax(100);\n" +
                                    "                            progressBar.show();", getLocalClassName());
                            progressBar = new ProgressDialog(CPFVisitTercActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("ATUALIZANDO ...");
                            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressBar.setProgress(0);
                            progressBar.setMax(100);
                            progressBar.show();

                            LogProcessoDAO.getInstance().insertLogProcesso("pmmContext.getMotoMecFertCTR().atualDados(VisitanteTerceiroActivity.this, VisitanteTerceiroActivity.class, progressBar, \"VisitanteTerceiro\", 1, getLocalClassName());", getLocalClassName());
                            pcpContext.getConfigCTR().atualDados(CPFVisitTercActivity.this, CPFVisitTercActivity.class, progressBar, "VisitanteTerceiro", 1, getLocalClassName());

                        } else {

                            LogProcessoDAO.getInstance().insertLogProcesso("AlertDialog.Builder alerta = new AlertDialog.Builder(VisitanteTerceiroActivity.this);\n" +
                                    "                            alerta.setTitle(\"ATENÇÃO\");\n" +
                                    "                            alerta.setMessage(\"FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");\n" +
                                    "                            alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                    "                                @Override\n" +
                                    "                                public void onClick(DialogInterface dialog, int which) {\n" +
                                    "                                }\n" +
                                    "                            });\n" +
                                    "                            alerta.show();", getLocalClassName());
                            AlertDialog.Builder alerta = new AlertDialog.Builder(CPFVisitTercActivity.this);
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

        buttonOkVisitTerc.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("rawtypes")
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkVisitTerc.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @SuppressWarnings(\"rawtypes\")\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                if (!editTextPadrao.getText().toString().equals("")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if (!editTextPadrao.getText().toString().equals(\"\")) {", getLocalClassName());
                    if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoVisitTercMovEquipVisitTerc() == 2L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoVisitTercMovEquipVisitTerc() == 2L){", getLocalClassName());
                        if (pcpContext.getMovVeicVisitTercCTR().verTerceiroCpf(editTextPadrao.getText().toString())) {
                            LogProcessoDAO.getInstance().insertLogProcesso("if (pcpContext.getMovVeicVisitTercCTR().verTerceiroCpf(editTextPadrao.getText().toString())) {\n" +
                                    "                            setIdVisitTerc(pcpContext.getMovVeicVisitTercCTR().getTerceiroCpf(editTextPadrao.getText().toString()).getIdTerceiro());", getLocalClassName());
                            setIdVisitTerc(pcpContext.getMovVeicVisitTercCTR().getTerceiroCpf(editTextPadrao.getText().toString()).getIdTerceiro());
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "AlertDialog.Builder alerta = new AlertDialog.Builder(VisitanteTerceiroActivity.this);\n" +
                                    "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                                    "                        alerta.setMessage(msg);\n" +
                                    "                        alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                    "                            @Override\n" +
                                    "                            public void onClick(DialogInterface dialog, int which) {\n" +
                                    "                            }\n" +
                                    "                        });\n" +
                                    "                        alerta.show();", getLocalClassName());
                            AlertDialog.Builder alerta = new AlertDialog.Builder(CPFVisitTercActivity.this);
                            alerta.setTitle("ATENÇÃO");
                            alerta.setMessage("CPF DO TERCEIRO INVÁLIDO! FAVOR VERIFICA O MESMO.");
                            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            alerta.show();
                        }

                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                        if (pcpContext.getMovVeicVisitTercCTR().verVisitanteCpf(editTextPadrao.getText().toString())) {
                            LogProcessoDAO.getInstance().insertLogProcesso("if (pcpContext.getMovVeicVisitTercCTR().verVisitanteCpf(editTextPadrao.getText().toString())) {\n" +
                                    "                            setIdVisitTerc(pcpContext.getMovVeicVisitTercCTR().getVisitanteCpf(editTextPadrao.getText().toString()).getIdVisitante());", getLocalClassName());
                            setIdVisitTerc(pcpContext.getMovVeicVisitTercCTR().getVisitanteCpf(editTextPadrao.getText().toString()).getIdVisitante());
                        } else {
                            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                    "AlertDialog.Builder alerta = new AlertDialog.Builder(VisitanteTerceiroActivity.this);\n" +
                                    "                        alerta.setTitle(\"ATENÇÃO\");\n" +
                                    "                        alerta.setMessage(msg);\n" +
                                    "                        alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
                                    "                            @Override\n" +
                                    "                            public void onClick(DialogInterface dialog, int which) {\n" +
                                    "                            }\n" +
                                    "                        });\n" +
                                    "                        alerta.show();", getLocalClassName());
                            AlertDialog.Builder alerta = new AlertDialog.Builder(CPFVisitTercActivity.this);
                            alerta.setTitle("ATENÇÃO");
                            alerta.setMessage("CPF DO VISITANTE INVÁLIDO! FAVOR VERIFICA O MESMO.");
                            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            alerta.show();
                        }
                    }
                }
            }
        });

        buttonCancVisitTerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancColab.setOnClickListener(new View.OnClickListener() {\n" +
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
        Intent it;
        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                    "            it = new Intent(CpfVisitTercActivity.this, TipoVisitTercActivity.class);", getLocalClassName());
            it = new Intent(CPFVisitTercActivity.this, TipoVisitTercActivity.class);
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            it = new Intent(CpfVisitTercActivity.this, ListaPassagColabVisitTercActivity.class);", getLocalClassName());
            it = new Intent(CPFVisitTercActivity.this, ListaPassagColabVisitTercActivity.class);
        }
        startActivity(it);
        finish();
    }

    private void setIdVisitTerc(Long idVisitTerc){
        LogProcessoDAO.getInstance().insertLogProcesso("private void setIdVisitTerc(Long idVisitTerc){", getLocalClassName());
        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L) {
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L) {\n" +
                    "            pcpContext.getMovVeicVisitTercCTR().setIdVisitTerc(idVisitTerc);", getLocalClassName());
            pcpContext.getMovVeicVisitTercCTR().setIdVisitTerc(idVisitTerc);
        } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){
            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){\n" +
                    "            pcpContext.getMovVeicVisitTercCTR().setIdVisitTerc(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), idVisitTerc);", getLocalClassName());
            pcpContext.getMovVeicVisitTercCTR().setIdVisitTerc(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), idVisitTerc);
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().setIdVisitTercMovEquipVisitTercPassag(idVisitTerc);", getLocalClassName());
            pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().setIdVisitTercMovEquipVisitTercPassag(idVisitTerc);
        }
        LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(CPFVisitTercActivity.this, NomeColabVisitTercActivity.class);", getLocalClassName());
        Intent it = new Intent(CPFVisitTercActivity.this, NomeColabVisitTercActivity.class);
        startActivity(it);
        finish();
    }

}