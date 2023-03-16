package br.com.usinasantafe.pcp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.usinasantafe.pcp.PCPContext;
import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;

public class ListaMovVisitTercResidManutActivity extends AppCompatActivity {

    private PCPContext pcpContext;
    private List movEquipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mov_visit_terc_resid_manut);

        pcpContext = (PCPContext) getApplication();

        Button buttonRetDescrMov = findViewById(R.id.buttonRetDescrMov);

        if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
            LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                    "            movEquipList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercFechadoList();", getLocalClassName());
            movEquipList = pcpContext.getMovVeicVisitTercCTR().movEquipVisitTercFechadoList();
        } else {
            LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                    "            movEquipList = pcpContext.getMovVeicResidenciaCTR().movEquipResidenciaFechadoList();", getLocalClassName());
            movEquipList = pcpContext.getMovVeicResidenciaCTR().movEquipResidenciaFechadoList();
        }

        ListView listViewMov = findViewById(R.id.listViewMov);
        AdapterListMovProprio adapterListMovProprio = new AdapterListMovProprio(this, movEquipList);
        listViewMov.setAdapter(adapterListMovProprio);
        listViewMov.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                LogProcessoDAO.getInstance().insertLogProcesso("listViewMov.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onItemClick(AdapterView<?> l, View v, int position,\n" +
                        "                                    long id) {\n" +
                        "                pcpContext.getConfigCTR().setPosicaoListaMov((long) (position));\n" +
                        "                Intent it = new Intent(ListaMovVisitTercResidManutActivity.this, DescrMovActivity.class);", getLocalClassName());
                pcpContext.getConfigCTR().setPosicaoListaMov((long) (position));
                Intent it = new Intent(ListaMovVisitTercResidManutActivity.this, DescrMovActivity.class);
                startActivity(it);
                finish();
            }

        });
        buttonRetDescrMov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogProcessoDAO.getInstance().insertLogProcesso("buttonRetDescrMov.setOnClickListener(new View.OnClickListener() {\n" +
                        "            @Override\n" +
                        "            public void onClick(View v) {", getLocalClassName());
                Intent it;
                if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){
                    LogProcessoDAO.getInstance().insertLogProcesso("if(pcpContext.getConfigCTR().getConfig().getTipoMov() == 2L){\n" +
                            "                    it = new Intent(ListaMovVisitTercResidManutActivity.this, ListaMovVisitTercActivity.class);", getLocalClassName());
                    it = new Intent(ListaMovVisitTercResidManutActivity.this, ListaMovVisitTercActivity.class);
                } else {
                    LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
                            "                    it = new Intent(ListaMovVisitTercResidManutActivity.this, ListaMovResidenciaActivity.class);", getLocalClassName());
                    it = new Intent(ListaMovVisitTercResidManutActivity.this, ListaMovResidenciaActivity.class);
                }
                startActivity(it);
                finish();
            }
        });
    }

    public void onBackPressed() {
    }

}