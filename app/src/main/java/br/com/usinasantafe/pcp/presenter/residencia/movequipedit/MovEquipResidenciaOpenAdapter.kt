package br.com.usinasantafe.pcp.presenter.residencia.movequipedit

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.usinasantafe.pcp.databinding.ItemRowMovEquipResidenciaStartedBinding
import br.com.usinasantafe.pcp.presenter.residencia.movequip.MovEquipResidenciaModel

class MovEquipResidenciaOpenAdapter(
private val dataSet: List<MovEquipResidenciaModel>
) : RecyclerView.Adapter<MovEquipResidenciaOpenAdapter.ViewHolder>() {

    var onItemClick: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemRowMovEquipResidenciaStartedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindView(dataSet[position], position)
    }

    override fun getItemCount() = dataSet.size

    inner class ViewHolder(
        itemRowMovEquipResidenciaStartedBinding: ItemRowMovEquipResidenciaStartedBinding
    ) : RecyclerView.ViewHolder(itemRowMovEquipResidenciaStartedBinding.root) {

        private val textViewDthrMov = itemRowMovEquipResidenciaStartedBinding.textViewDthrMov
        private val textViewMotoristaMov = itemRowMovEquipResidenciaStartedBinding.textViewMotoristaMov
        private val textViewVeiculoMov = itemRowMovEquipResidenciaStartedBinding.textViewVeiculoMov
        private val textViewPlacaMov = itemRowMovEquipResidenciaStartedBinding.textViewPlacaMov
        private val textViewTipoMov = itemRowMovEquipResidenciaStartedBinding.textViewTipoMov

        fun bindView(movEquipResidenciaModel: MovEquipResidenciaModel, position: Int) {

            textViewDthrMov.text = movEquipResidenciaModel.dthr
            textViewMotoristaMov.text = movEquipResidenciaModel.motorista
            textViewVeiculoMov.text = movEquipResidenciaModel.placa
            textViewPlacaMov.text = movEquipResidenciaModel.veiculo
            textViewTipoMov.text = movEquipResidenciaModel.tipo
            if(movEquipResidenciaModel.tipo == "ENTRADA"){
                textViewTipoMov.setTextColor(Color.BLUE)
            } else {
                textViewTipoMov.setTextColor(Color.RED)
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(position)
            }

        }

    }

}