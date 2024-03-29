package br.com.usinasantafe.pcp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class TipoVisitTercActivity extends ActivityGeneric {

    private PCPContext pcpContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_visit_terc);

        pcpContext = (PCPContext) getApplication();

        Button buttonRetornarTipo = findViewById(R.id.buttonRetornarTipo);

        ArrayList<String> itens = new ArrayList<String>();

        itens.add("TERCEIRO");
        itens.add("VISITANTE");

        AdapterList adapterList = new AdapterList(this, itens);
        ListView listaTipo = findViewById(R.id.listViewTipo);
        listaTipo.setAdapter(adapterList);

        listaTipo.setOnItemClickListener((l, v, position, id) -> {

            LogProcessoDAO.getInstance().insertLogProcesso("lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                    "                                    long id) {\n" +
                    "                TextView textView = v.findViewById(R.id.textViewItemList);\n" +
                    "                String text = textView.getText().toString();", getLocalClassName());
            TextView textView = v.findViewById(R.id.textViewItemList);
            String text = textView.getText().toString();

            if (text.equals("TERCEIRO")) {
                LogProcessoDAO.getInstance().insertLogProcesso("if (text.equals(\"TERCEIRO\")) {\n" +
                        "                    pcpContext.getMovimentacaoVeicVisTercCTR().setTipoVisitTerc(1L);", getLocalClassName());
                pcpContext.getMovVeicVisitTercCTR().setTipoVisitTerc(2L);
            } else if (text.equals("VISITANTE")) {
                LogProcessoDAO.getInstance().insertLogProcesso("} else if (text.equals(\"VISITANTE\")) {\n" +
                        "                    pcpContext.getMovVeicVisitTercCTR().setTipoVisitTerc(2L);", getLocalClassName());
                pcpContext.getMovVeicVisitTercCTR().setTipoVisitTerc(1L);
            }

            LogProcessoDAO.getInstance().insertLogProcesso("Intent it = new Intent(TipoActivity.this, VisitanteTerceiroActivity.class);", getLocalClassName());
            Intent it = new Intent(TipoVisitTercActivity.this, CPFVisitTercActivity.class);
            startActivity(it);
            finish();

        });

        buttonRetornarTipo.setOnClickListener(v -> {
            LogProcessoDAO.getInstance().insertLogProcesso("buttonRetornarTipo.setOnClickListener(new View.OnClickListener() {\n" +
                    "            @Override\n" +
                    "            public void onClick(View v) {\n" +
                    "                Intent it = new Intent(TipoVisitTercActivity.this, PlacaVisitTercResidActivity.class);", getLocalClassName());
            Intent it = new Intent(TipoVisitTercActivity.this, PlacaVisitTercResidActivity.class);
            startActivity(it);
            finish();
        });

    }
    
    public void onBackPressed() {
    }

}