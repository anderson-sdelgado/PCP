package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.bean.estaticas.TerceiroBean;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class NomeVisitTercActivity extends ActivityGeneric {

    private PCPContext pcpContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nome_visit_terc);

        pcpContext = (PCPContext) getApplication();

        TextView textViewTituloNome = findViewById(R.id.textViewTituloNome);
        TextView textViewNome = findViewById(R.id.textViewNome);
        TextView textViewEmpresa = findViewById(R.id.textViewEmpresa);
        Button buttonOkNome = findViewById(R.id.buttonOkNome);
        Button buttonCancNome =  findViewById(R.id.buttonCancNome);

        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){", getLocalClassName());
            if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoVisitTercMovEquipVisitTerc() == 2L){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovimentacaoVeicVisTercCTR().getMovEquipVisitTercAberto().getTipoVisitTercMovEquipVisitTerc() == 1L){\n" +
                        "                textViewTituloNome.setText(\"NOME DO TERCEIRO\");\n" +
                        "                textViewNome.setText(pcpContext.getMovimentacaoVeicVisTercCTR().getTerceiroId(pcpContext.getMovimentacaoVeicVisTercCTR().getMovEquipVisitTercAberto().getIdVisitTercMovEquipVisitTerc()).getNomeTerceiro());", getLocalClassName());
                textViewTituloNome.setText("NOME DO TERCEIRO");
                textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getTerceiroId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getIdVisitTercMovEquipVisitTerc()).getNomeTerceiro());
                textViewEmpresa.setText(pcpContext.getMovVeicVisitTercCTR().empresasTerc(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getIdVisitTercMovEquipVisitTerc()));
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                textViewTituloNome.setText(\"NOME DO VISITANTE\");\n" +
                        "                textViewNome.setText(pcpContext.getMovimentacaoVeicVisTercCTR().getVisitanteId(pcpContext.getMovimentacaoVeicVisTercCTR().getMovEquipVisitTercAberto().getIdVisitTercMovEquipVisitTerc()).getNomeVisitante());", getLocalClassName());
                textViewTituloNome.setText("NOME DO VISITANTE");
                textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getVisitanteId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getIdVisitTercMovEquipVisitTerc()).getNomeVisitante());
                textViewEmpresa.setText(pcpContext.getMovVeicVisitTercCTR().getVisitanteId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getIdVisitTercMovEquipVisitTerc()).getEmpresaVisitante());
            }
        } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 6L){
            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 6L){", getLocalClassName());
            if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoVisitTercMovEquipVisitTerc() == 2L){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercAberto().getTipoVisitTercMovEquipVisitTerc() == 2L){\n" +
                        "                    textViewTituloNome.setText(\"NOME DO TERCEIRO\");\n" +
                        "                    textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getTerceiroId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag()).getNomeTerceiro());", getLocalClassName());
                textViewTituloNome.setText("NOME DO TERCEIRO");
                textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getTerceiroId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag()).getNomeTerceiro());
                textViewEmpresa.setText(pcpContext.getMovVeicVisitTercCTR().empresasTerc(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag()));
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                    textViewTituloNome.setText(\"NOME DO VISITANTE\");\n" +
                        "                    textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getVisitanteId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag()).getNomeVisitante());", getLocalClassName());
                textViewTituloNome.setText("NOME DO VISITANTE");
                textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getVisitanteId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag()).getNomeVisitante());
                textViewEmpresa.setText(pcpContext.getMovVeicVisitTercCTR().getVisitanteId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag()).getEmpresaVisitante());
            }
        } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){
            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){", getLocalClassName());
            if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getTipoVisitTercMovEquipVisitTerc() == 2L){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getTipoVisitTercMovEquipVisitTerc() == 2L){\n" +
                        "                    textViewTituloNome.setText(\"NOME DO TERCEIRO\");\n" +
                        "                    textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getTerceiroId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getIdVisitTercMovEquipVisitTerc()).getNomeTerceiro());", getLocalClassName());
                textViewTituloNome.setText("NOME DO TERCEIRO");
                textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getTerceiroId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getIdVisitTercMovEquipVisitTerc()).getNomeTerceiro());
                textViewEmpresa.setText(pcpContext.getMovVeicVisitTercCTR().empresasTerc(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getIdVisitTercMovEquipVisitTerc()));
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                textViewTituloNome.setText(\"NOME DO VISITANTE\");\n" +
                        "                textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getVisitanteId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getIdVisitTercMovEquipVisitTerc()).getNomeVisitante());", getLocalClassName());
                textViewTituloNome.setText("NOME DO VISITANTE");
                textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getVisitanteId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getIdVisitTercMovEquipVisitTerc()).getNomeVisitante());
                textViewEmpresa.setText(pcpContext.getMovVeicVisitTercCTR().getVisitanteId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getIdVisitTercMovEquipVisitTerc()).getEmpresaVisitante());
            }
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {", getLocalClassName());
            if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getTipoVisitTercMovEquipVisitTerc() == 2L){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getTipoVisitTercMovEquipVisitTerc() == 2L){\n" +
                        "                    textViewTituloNome.setText(\"NOME DO TERCEIRO\");\n" +
                        "                    textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getTerceiroId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag()).getNomeTerceiro());", getLocalClassName());
                textViewTituloNome.setText("NOME DO TERCEIRO");
                textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getTerceiroId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag()).getNomeTerceiro());
                textViewEmpresa.setText(pcpContext.getMovVeicVisitTercCTR().empresasTerc(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag()));
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                    textViewTituloNome.setText(\"NOME DO VISITANTE\");\n" +
                        "                    textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getVisitanteId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag()).getNomeVisitante());", getLocalClassName());
                textViewTituloNome.setText("NOME DO VISITANTE");
                textViewNome.setText(pcpContext.getMovVeicVisitTercCTR().getVisitanteId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag()).getNomeVisitante());
                textViewEmpresa.setText(pcpContext.getMovVeicVisitTercCTR().getVisitanteId(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag()).getEmpresaVisitante());
            }
        }

        buttonOkNome.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonOkNome.setOnClickListener(v -> {", getLocalClassName());
            Intent it;
            if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                        "                    it = new Intent(NomeColabVisitTercActivity.this, ListaPassagColabVisitTercActivity.class);", getLocalClassName());
                it = new Intent(NomeVisitTercActivity.this, ListaPassagColabVisitTercActivity.class);
            } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 6L){
                LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 6L){\n" +
                        "                pcpContext.getMovVeicVisitTercCTR().inserirMovEquipVisitTercPassag(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag());\n" +
                        "                it = new Intent(NomeVisitTercActivity.this, ListaPassagColabVisitTercActivity.class);", getLocalClassName());
                pcpContext.getMovVeicVisitTercCTR().inserirMovEquipVisitTercPassag(pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag());
                it = new Intent(NomeVisitTercActivity.this, ListaPassagColabVisitTercActivity.class);
            } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){
                LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){\n" +
                        "                    it = new Intent(NomeColabVisitTercActivity.this, DescrMovActivity.class);", getLocalClassName());
                it = new Intent(NomeVisitTercActivity.this, DescrMovActivity.class);
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                pcpContext.getMovVeicVisitTercCTR().inserirMovEquipVisitTercPassag(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag());\n" +
                        "                it = new Intent(NomeVisitTercActivity.this, ListaPassagColabVisitTercActivity.class);", getLocalClassName());
                pcpContext.getMovVeicVisitTercCTR().inserirMovEquipVisitTercPassag(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), pcpContext.getMovVeicVisitTercCTR().getMovEquipVisitTercPassagDAO().getMovEquipVisitTercPassagBean().getIdVisitTercMovEquipVisitTercPassag());
                it = new Intent(NomeVisitTercActivity.this, ListaPassagColabVisitTercActivity.class);
            }
            startActivity(it);
            finish();
        });

        buttonCancNome.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonCancNome.setOnClickListener(v -> {\n" +
                    "            Intent it = new Intent(NomeVisitTercActivity.this, CPFVisitTercActivity.class);", getLocalClassName());
            Intent it = new Intent(NomeVisitTercActivity.this, CPFVisitTercActivity.class);
            startActivity(it);
            finish();
        });

    }

    public void onBackPressed() {
    }

}