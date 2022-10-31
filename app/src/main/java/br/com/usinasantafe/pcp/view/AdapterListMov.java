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
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercBean;

public class AdapterListMov extends BaseAdapter {

    private List itens;
    private LayoutInflater layoutInflater;
    private Long tipo;
    private TextView textViewDthrMov;
    private TextView textViewTipoMov;
    private TextView textViewEquipMov;

    public AdapterListMov(Context context, List itens, Long tipo) {
        this.itens = itens;
        layoutInflater = LayoutInflater.from(context);
        this.tipo = tipo;
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

        view = layoutInflater.inflate(R.layout.activity_item_mov, null);
        textViewDthrMov = view.findViewById(R.id.textViewDthrMov);
        textViewTipoMov = view.findViewById(R.id.textViewTipoMov);
        textViewEquipMov = view.findViewById(R.id.textViewEquipMov);

        if(tipo == 1L){
            MovEquipProprioBean movEquipProprioBean = (MovEquipProprioBean) itens.get(position);
            textViewDthrMov.setText("DTHR: " + movEquipProprioBean.getDthrMovEquipProprio());
            tipoMov(movEquipProprioBean.getTipoMovEquipProprio());
            equipMov(movEquipProprioBean.getIdEquipMovEquipProprio());
        } else {
            MovEquipVisitTercBean movEquipVisitTercBean = (MovEquipVisitTercBean) itens.get(position);
            textViewDthrMov.setText("DTHR: " + movEquipVisitTercBean.getDthrMovEquipVisitTerc());
            tipoMov(movEquipVisitTercBean.getTipoMovEquipVisitTerc());
            textViewEquipMov.setText("VEÍCULO: " + movEquipVisitTercBean.getVeiculoMovEquipVisitTerc() + "\n" +
                    "PLACA: " + movEquipVisitTercBean.getPlacaMovEquipVisitTerc());
        }

        return view;
    }

    public void tipoMov(Long tipoMov){
        if(tipoMov == 1L) {
            textViewTipoMov.setText("ENTRADA");
            textViewTipoMov.setTextColor(Color.BLUE);
        } else {
            textViewTipoMov.setText("SAÍDA");
            textViewTipoMov.setTextColor(Color.RED);
        }
    }

    public void equipMov(Long idEquip){
        ConfigCTR configCTR = new ConfigCTR();
        textViewEquipMov.setText("VEÍCULO: " + configCTR.getEquipId(idEquip).getNroEquip());
    }

}
