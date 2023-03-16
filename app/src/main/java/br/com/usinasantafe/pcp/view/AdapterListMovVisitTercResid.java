package br.com.usinasantafe.pcp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.control.ConfigCTR;
import br.com.usinasantafe.pcp.control.MovVeicVisitTercCTR;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipResidenciaBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercBean;

public class AdapterListMovVisitTercResid extends BaseAdapter {

    private List itens;
    private LayoutInflater layoutInflater;
    private TextView textViewDthrMov;
    private TextView textViewMotoristaMov;
    private TextView textViewVeiculoMov;
    private TextView textViewPlacaMov;

    public AdapterListMovVisitTercResid(Context context, List itens) {
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

        view = layoutInflater.inflate(R.layout.activity_item_mov_visit_terc_resid, null);
        textViewDthrMov = view.findViewById(R.id.textViewDthrMov);
        textViewMotoristaMov = view.findViewById(R.id.textViewMotoristaMov);
        textViewVeiculoMov = view.findViewById(R.id.textViewVeiculoMov);
        textViewPlacaMov = view.findViewById(R.id.textViewPlacaMov);

        ConfigCTR configCTR = new ConfigCTR();
        if(configCTR.getConfig().getTipoMov() == 2L){
            MovEquipVisitTercBean movEquipVisitTercBean = (MovEquipVisitTercBean) itens.get(position);
            textViewDthrMov.setText("DTHR: " + movEquipVisitTercBean.getDthrMovEquipVisitTerc());
            visitTercMov(movEquipVisitTercBean);
            textViewVeiculoMov.setText("VEÍCULO: " + movEquipVisitTercBean.getVeiculoMovEquipVisitTerc());
            textViewPlacaMov.setText("PLACA: " + movEquipVisitTercBean.getPlacaMovEquipVisitTerc());
        } else {
            MovEquipResidenciaBean movEquipResidenciaBean = (MovEquipResidenciaBean) itens.get(position);
            textViewDthrMov.setText("DTHR: " + movEquipResidenciaBean.getDthrMovEquipResidencia());
            textViewMotoristaMov.setText("VISITANTE: " + movEquipResidenciaBean.getNomeVisitanteMovEquipResidencia());
            textViewVeiculoMov.setText("VEÍCULO: " + movEquipResidenciaBean.getVeiculoMovEquipResidencia());
            textViewPlacaMov.setText("PLACA: " + movEquipResidenciaBean.getPlacaMovEquipResidencia());
        }
        return view;
    }

    public void visitTercMov(MovEquipVisitTercBean movEquipVisitTercBean){
        MovVeicVisitTercCTR movVeicVisitTercCTR = new MovVeicVisitTercCTR();
        if(movEquipVisitTercBean.getTipoVisitTercMovEquipVisitTerc() == 2L){
            textViewMotoristaMov.setText("TERCEIRO: " + movVeicVisitTercCTR.getTerceiroId(movEquipVisitTercBean.getIdVisitTercMovEquipVisitTerc()).getCpfTerceiro() + " - " + movVeicVisitTercCTR.getTerceiroId(movEquipVisitTercBean.getIdVisitTercMovEquipVisitTerc()).getNomeTerceiro());
        } else {
            textViewMotoristaMov.setText("VISITANTE: " + movVeicVisitTercCTR.getVisitanteId(movEquipVisitTercBean.getIdVisitTercMovEquipVisitTerc()).getCpfVisitante() + " - " + movVeicVisitTercCTR.getVisitanteId(movEquipVisitTercBean.getIdVisitTercMovEquipVisitTerc()).getNomeVisitante());
        }
    }

}
