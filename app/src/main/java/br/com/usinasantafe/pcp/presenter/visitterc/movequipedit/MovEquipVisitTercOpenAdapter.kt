package br.com.usinasantafe.pcp.presenter.visitterc.movequipedit

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.usinasantafe.pcp.databinding.ItemRowMovEquipVisitTercStartedBinding
import br.com.usinasantafe.pcp.presenter.visitterc.movequip.MovEquipVisitTercModel

class MovEquipVisitTercOpenAdapter(
    private val dataSet: List<MovEquipVisitTercModel>
) : RecyclerView.Adapter<MovEquipVisitTercOpenAdapter.ViewHolder>() {

    var onItemClick: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemRowMovEquipVisitTercStartedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindView(dataSet[position], position)
    }

    override fun getItemCount() = dataSet.size

    inner class ViewHolder(
        itemRowMovEquipVisitTercStartedBinding: ItemRowMovEquipVisitTercStartedBinding
    ) : RecyclerView.ViewHolder(itemRowMovEquipVisitTercStartedBinding.root) {

        private val textViewDthrMov = itemRowMovEquipVisitTercStartedBinding.textViewDthrMov
        private val textViewMotoristaMov = itemRowMovEquipVisitTercStartedBinding.textViewMotoristaMov
        private val textViewVeiculoMov = itemRowMovEquipVisitTercStartedBinding.textViewVeiculoMov
        private val textViewPlacaMov = itemRowMovEquipVisitTercStartedBinding.textViewPlacaMov
        private val textViewTipoMov = itemRowMovEquipVisitTercStartedBinding.textViewTipoMov

        fun bindView(movEquipVisitTercModel: MovEquipVisitTercModel, position: Int) {

            textViewDthrMov.text = movEquipVisitTercModel.dthr
            textViewMotoristaMov.text = movEquipVisitTercModel.motorista
            textViewVeiculoMov.text = movEquipVisitTercModel.placa
            textViewPlacaMov.text = movEquipVisitTercModel.veiculo
            textViewTipoMov.text = movEquipVisitTercModel.tipo
            if(movEquipVisitTercModel.tipo == "ENTRADA"){
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