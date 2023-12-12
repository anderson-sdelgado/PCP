package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class NomeColabActivity extends ActivityGeneric {

    private PCPContext pcpContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nome_colab);

        pcpContext = (PCPContext) getApplication();

        TextView textViewTituloNome = findViewById(R.id.textViewTituloNome);
        TextView textViewNome = findViewById(R.id.textViewNome);
        Button buttonOkNome = findViewById(R.id.buttonOkNome);
        Button buttonCancNome =  findViewById(R.id.buttonCancNome);

        if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 3L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 3L){\n" +
                    "                textViewTituloNome.setText(\"NOME DO VIGIA\");\n" +
                    "                textViewNome.setText(pcpContext.getConfigCTR().getColab(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());", getLocalClassName());
            textViewTituloNome.setText("NOME DO VIGIA");
            textViewNome.setText(pcpContext.getConfigCTR().getColabMatric(pcpContext.getConfigCTR().getConfig().getMatricVigiaConfig()).getNomeColab());
        } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){
            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                    "                textViewTituloNome.setText(\"NOME DO COLABORADOR\");\n" +
                    "                textViewNome.setText(pcpContext.getConfigCTR().getColab(pcpContext.getMovVeicProprioCTR().getMovEquipProprioAberto().getNroMatricColabMovEquipProprio()).getNomeColab());", getLocalClassName());
            textViewTituloNome.setText("NOME DO COLABORADOR");
            textViewNome.setText(pcpContext.getConfigCTR().getColabMatric(pcpContext.getMovVeicProprioCTR().getMovEquipProprioAberto().getNroMatricColabMovEquipProprio()).getNomeColab());
        } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 6L){
            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 6L){\n" +
                    "                textViewTituloNome.setText(\"NOME DO COLABORADOR\");\n" +
                    "                textViewNome.setText(pcpContext.getConfigCTR().getColabMatric(pcpContext.getMovVeicProprioCTR().getMovEquipProprioPassagDAO().getMovEquipProprioPassagBean().getMatricColabMovEquipProprioPassag()).getNomeColab());", getLocalClassName());
            textViewTituloNome.setText("NOME DO COLABORADOR");
            textViewNome.setText(pcpContext.getConfigCTR().getColabMatric(pcpContext.getMovVeicProprioCTR().getMovEquipProprioPassagDAO().getMovEquipProprioPassagBean().getNroMatricMovEquipProprioPassag()).getNomeColab());
        } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){
            LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){\n" +
                    "                textViewTituloNome.setText(\"NOME DO COLABORADOR\");\n" +
                    "                textViewNome.setText(pcpContext.getConfigCTR().getColabMatric(pcpContext.getMovVeicProprioCTR().getMovEquipProprioFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getNroMatricColabMovEquipProprio()).getNomeColab());", getLocalClassName());
            textViewTituloNome.setText("NOME DO COLABORADOR");
            textViewNome.setText(pcpContext.getConfigCTR().getColabMatric(pcpContext.getMovVeicProprioCTR().getMovEquipProprioFechado(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue()).getNroMatricColabMovEquipProprio()).getNomeColab());
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "                textViewTituloNome.setText(\"NOME DO COLABORADOR\");\n" +
                    "                textViewNome.setText(pcpContext.getConfigCTR().getColabMatric(pcpContext.getMovVeicProprioCTR().getMovEquipProprioPassagDAO().getMovEquipProprioPassagBean().getMatricColabMovEquipProprioPassag()).getNomeColab());", getLocalClassName());
            textViewTituloNome.setText("NOME DO COLABORADOR");
            textViewNome.setText(pcpContext.getConfigCTR().getColabMatric(pcpContext.getMovVeicProprioCTR().getMovEquipProprioPassagDAO().getMovEquipProprioPassagBean().getNroMatricMovEquipProprioPassag()).getNomeColab());
        }

        buttonOkNome.setOnClickListener(v -> {

            LogProcessoDAO.getInstance().insertLogProcesso("buttonOkNome.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {", getLocalClassName());
            Intent it;
            if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 3L) {
                LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 3L){\n" +
                        "                        it = new Intent(NomeColabVisitTercActivity.this, ListaLocalActivity.class);", getLocalClassName());
                it = new Intent(NomeColabActivity.this, ListaLocalActivity.class);
            } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L) {
                LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 4L){\n" +
                        "                    it = new Intent(NomeColabVisitTercActivity.this, ListaPassagColabVisitTercActivity.class);", getLocalClassName());
                it = new Intent(NomeColabActivity.this, ListaPassagColabVisitTercActivity.class);
            } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 6L) {
                LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 6L){\n" +
                        "                pcpContext.getMovVeicProprioCTR().inserirMovEquipProprioPassag(pcpContext.getMovVeicProprioCTR().getMovEquipProprioPassagDAO().getMovEquipProprioPassagBean().getNroMatricMovEquipProprioPassag());\n" +
                        "                it = new Intent(NomeColabActivity.this, ListaPassagColabVisitTercActivity.class);", getLocalClassName());
                pcpContext.getMovVeicProprioCTR().inserirMovEquipProprioPassag(pcpContext.getMovVeicProprioCTR().getMovEquipProprioPassagDAO().getMovEquipProprioPassagBean().getNroMatricMovEquipProprioPassag());
                it = new Intent(NomeColabActivity.this, ListaPassagColabVisitTercActivity.class);
            } else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L) {
                LogProcessoDAO.getInstance().insertLogProcesso("} else if(pcpContext.getConfigCTR().getConfig().getPosicaoTela() == 7L){\n" +
                        "                    it = new Intent(NomeColabVisitTercActivity.this, DescrMovActivity.class);", getLocalClassName());
                it = new Intent(NomeColabActivity.this, DescrMovActivity.class);
            } else {
                LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                        "                pcpContext.getMovVeicProprioCTR().inserirMovEquipProprioPassag(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), pcpContext.getMovVeicProprioCTR().getMovEquipProprioPassagDAO().getMovEquipProprioPassagBean().getNroMatricMovEquipProprioPassag());\n" +
                        "                it = new Intent(NomeColabActivity.this, ListaPassagColabVisitTercActivity.class);", getLocalClassName());
                pcpContext.getMovVeicProprioCTR().inserirMovEquipProprioPassag(pcpContext.getConfigCTR().getConfig().getPosicaoListaMov().intValue(), pcpContext.getMovVeicProprioCTR().getMovEquipProprioPassagDAO().getMovEquipProprioPassagBean().getNroMatricMovEquipProprioPassag());
                it = new Intent(NomeColabActivity.this, ListaPassagColabVisitTercActivity.class);
            }
            startActivity(it);
            finish();
        });

        buttonCancNome.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonCancNome.setOnClickListener(v -> {\n" +
                    "            Intent it = new Intent(NomeColabActivity.this, MatricColabActivity.class);", getLocalClassName());
            Intent it = new Intent(NomeColabActivity.this, MatricColabActivity.class);
            startActivity(it);
            finish();
        });

    }

    public void onBackPressed() {
    }

}