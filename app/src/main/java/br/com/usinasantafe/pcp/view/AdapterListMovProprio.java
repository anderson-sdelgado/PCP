package br.com.usinasantafe.pcp.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.control.ConfigCTR;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;

public class AdapterListMovProprio extends BaseAdapter {

    private List itens;
    private LayoutInflater layoutInflater;
    private TextView textViewDthrMov;
    private TextView textViewTipoMov;
    private TextView textViewColabMov;
    private TextView textViewEquipMov;

    public AdapterListMovProprio(Context context, List itens) {
        this.itens = itens;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = layoutInflater.inflate(R.layout.activity_item_mov_proprio, null);
        textViewDthrMov = view.findViewById(R.id.textViewDthrMov);
        textViewTipoMov = view.findViewById(R.id.textViewTipoMov);
        textViewColabMov = view.findViewById(R.id.textViewMotoristaMov);
        textViewEquipMov = view.findViewById(R.id.textViewEquipMov);

        MovEquipProprioBean movEquipProprioBean = (MovEquipProprioBean) itens.get(position);
        textViewDthrMov.setText("DTHR: " + movEquipProprioBean.getDthrMovEquipProprio());
        tipoMov(movEquipProprioBean.getTipoMovEquipProprio());
        colabMov(movEquipProprioBean.getNroMatricColabMovEquipProprio());
        equipMov(movEquipProprioBean.getIdEquipMovEquipProprio());

        return view;
    }

    public void tipoMov(Long tipoMov){
        if(tipoMov == 2L) {
            textViewTipoMov.setText("ENTRADA");
            textViewTipoMov.setTextColor(Color.BLUE);
        } else {
            textViewTipoMov.setText("SAÍDA");
            textViewTipoMov.setTextColor(Color.RED);
        }
    }

    public void colabMov(Long nroMatric){
        ConfigCTR configCTR = new ConfigCTR();
        textViewColabMov.setText("COLABORADOR: " + nroMatric + " - " + configCTR.getColabMatric(nroMatric).getNomeColab());
    }

    public void equipMov(Long idEquip){
        ConfigCTR configCTR = new ConfigCTR();
        textViewEquipMov.setText("VEÍCULO: " + configCTR.getEquipId(idEquip).getNroEquip());
    }

}
