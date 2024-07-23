package br.com.usinasantafe.pcp.presenter.residencia.movequip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.usinasantafe.pcp.databinding.ItemRowMovEquipResidenciaBinding

class MovEquipResidenciaAdapter(
private val dataSet: List<MovEquipResidenciaModel>
) : RecyclerView.Adapter<MovEquipResidenciaAdapter.ViewHolder>() {

    var onItemClick: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemRowMovEquipResidenciaBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindView(dataSet[position], position)
    }

    override fun getItemCount() = dataSet.size

    inner class ViewHolder(
        itemRowMovEquipResidenciaBinding: ItemRowMovEquipResidenciaBinding
    ) : RecyclerView.ViewHolder(itemRowMovEquipResidenciaBinding.root) {

        private val textViewDthrMov = itemRowMovEquipResidenciaBinding.textViewDthrMov
        private val textViewMotoristaMov = itemRowMovEquipResidenciaBinding.textViewMotoristaMov
        private val textViewVeiculoMov = itemRowMovEquipResidenciaBinding.textViewVeiculoMov
        private val textViewPlacaMov = itemRowMovEquipResidenciaBinding.textViewPlacaMov

        fun bindView(movEquipResidenciaModel: MovEquipResidenciaModel, position: Int) {

            textViewDthrMov.text = movEquipResidenciaModel.dthr
            textViewMotoristaMov.text = movEquipResidenciaModel.motorista
            textViewVeiculoMov.text = movEquipResidenciaModel.placa
            textViewPlacaMov.text = movEquipResidenciaModel.veiculo

            itemView.setOnClickListener {
                onItemClick?.invoke(position)
            }

        }

    }

}