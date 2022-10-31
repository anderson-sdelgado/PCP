package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class ObservacaoActivity extends ActivityGeneric {

    private PCPContext pcpContext;
    private EditText editTextObservacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observacao);

        pcpContext = (PCPContext) getApplication();

        editTextObservacao = findViewById(R.id.editTextObservacao);
        Button buttonOkObservacao =  findViewById(R.id.buttonOkObservacao);
        Button buttonCancObservacao = findViewById(R.id.buttonCancObservacao);

        buttonOkObservacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonOkObservacao.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                String observacao;
                if(!editTextObservacao.getText().toString().equals("")) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if(!editTextObservacao.getText().toString().equals(\"\")) {\n" +
                            "                    observacao = \"null\";", getLocalClassName());
                    observacao = "null";
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    observacao = editTextObservacao.getText().toString();", getLocalClassName());
                    observacao = editTextObservacao.getText().toString();
                }

                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {\n" +
                            "                    pcpContext.getMovimentacaoVeicProprioCTR().fecharMovEquipProprio(observacao);", getLocalClassName());
                    pcpContext.getMovimentacaoVeicProprioCTR().fecharMovEquipProprio(observacao, getLocalClassName());
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    pcpContext.getMovimentacaoVeicVisTercCTR().fecharMovEquipVisitTerc(observacao);", getLocalClassName());
                    pcpContext.getMovimentacaoVeicVisitTercCTR().fecharMovEquipVisitTerc(observacao);
                }

                LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(ObservacaoActivity.this, ListaMovActivity.class);", getLocalClassName());
                Intent it = new Intent(ObservacaoActivity.this, ListaMovActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonCancObservacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonCancObservacao.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 1L) {
                    if (pcpContext.getMovimentacaoVeicProprioCTR().getMovEquipProprioAberto().getTipoMovEquipProprio() == 1L) {
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovimentacaoVeicProprioCTR().getMovEquipProprioAberto().getTipoMovEquipProprio() == 1L){\n" +
                                "                    it = new Intent(ObservacaoActivity.this, DestinoActivity.class);", getLocalClassName());
                        it = new Intent(ObservacaoActivity.this, DestinoActivity.class);
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                    it = new Intent(ObservacaoActivity.this, NotaFiscalActivity.class);", getLocalClassName());
                        it = new Intent(ObservacaoActivity.this, NotaFiscalActivity.class);
                    }
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
                    if(pcpContext.getMovimentacaoVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoMovEquipVisitTerc() == 1L){
                        LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovimentacaoVeicVisTercCTR().getMovEquipVisitTercAberto().getTipoMovEquipVisitTerc() == 1L){\n" +
                                "                        it = new Intent(ObservacaoActivity.this, DestinoActivity.class);", getLocalClassName());
                        it = new Intent(ObservacaoActivity.this, DestinoActivity.class);
                    } else {
                        LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                                "                        it = new Intent(ObservacaoActivity.this, PlacaVisitanteTerceiroActivity.class);", getLocalClassName());
                        it = new Intent(ObservacaoActivity.this, PlacaVisitTercActivity.class);
                    }
                }
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed()  {
    }

}