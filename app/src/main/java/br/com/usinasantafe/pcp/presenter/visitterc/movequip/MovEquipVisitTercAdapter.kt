package br.com.usinasantafe.pcp.presenter.visitterc.movequip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.usinasantafe.pcp.databinding.ItemRowMovEquipVisitTercBinding

class MovEquipVisitTercAdapter(
    private val dataSet: List<MovEquipVisitTercModel>
) : RecyclerView.Adapter<MovEquipVisitTercAdapter.ViewHolder>() {

    var onItemClick: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemRowMovEquipVisitTercBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindView(dataSet[position], position)
    }

    override fun getItemCount() = dataSet.size

    inner class ViewHolder(
        itemRowMovEquipVisitTercBinding: ItemRowMovEquipVisitTercBinding
    ) : RecyclerView.ViewHolder(itemRowMovEquipVisitTercBinding.root) {

        private val textViewDthrMov = itemRowMovEquipVisitTercBinding.textViewDthrMov
        private val textViewMotoristaMov = itemRowMovEquipVisitTercBinding.textViewMotoristaMov
        private val textViewVeiculoMov = itemRowMovEquipVisitTercBinding.textViewVeiculoMov
        private val textViewPlacaMov = itemRowMovEquipVisitTercBinding.textViewPlacaMov

        fun bindView(movEquipVisitTercModel: MovEquipVisitTercModel, position: Int) {

            textViewDthrMov.text = movEquipVisitTercModel.dthr
            textViewMotoristaMov.text = movEquipVisitTercModel.motorista
            textViewVeiculoMov.text = movEquipVisitTercModel.placa
            textViewPlacaMov.text = movEquipVisitTercModel.veiculo

            itemView.setOnClickListener {
                onItemClick?.invoke(position)
            }

        }

    }

}