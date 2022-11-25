package br.com.usinasantafe.pcp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pcp.R;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipResidenciaBean;

public class AdapterListMovResidencia extends BaseAdapter {

    private List itens;
    private LayoutInflater layoutInflater;
    private TextView textViewDthrMov;
    private TextView textViewVisitResidenciaMov;
    private TextView textViewVeiculoMov;
    private TextView textViewPlacaMov;

    public AdapterListMovResidencia(Context context, List itens) {
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

        view = layoutInflater.inflate(R.layout.activity_item_mov_residencia, null);
        textViewDthrMov = view.findViewById(R.id.textViewDthrMov);
        textViewVisitResidenciaMov = view.findViewById(R.id.textViewVisitResidenciaMov);
        textViewVeiculoMov = view.findViewById(R.id.textViewVeiculoMov);
        textViewPlacaMov = view.findViewById(R.id.textViewPlacaMov);

        MovEquipResidenciaBean movEquipResidenciaBean = (MovEquipResidenciaBean) itens.get(position);
        textViewDthrMov.setText("DTHR: " + movEquipResidenciaBean.getDthrMovEquipResidencia());
        textViewVisitResidenciaMov.setText("VISITANTE: " + movEquipResidenciaBean.getNomeVisitanteMovEquipResidencia());
        textViewVeiculoMov.setText("VEÍCULO: " + movEquipResidenciaBean.getVeiculoMovEquipResidencia());
        textViewPlacaMov.setText("PLACA: " + movEquipResidenciaBean.getPlacaMovEquipResidencia());

        return view;
    }

}
