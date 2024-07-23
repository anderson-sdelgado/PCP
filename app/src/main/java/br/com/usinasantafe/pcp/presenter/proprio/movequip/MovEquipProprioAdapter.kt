package br.com.usinasantafe.pcp.presenter.proprio.movequip

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.usinasantafe.pcp.databinding.ItemRowMovEquipProprioBinding

class MovEquipProprioAdapter(
    private val dataSet: List<MovEquipProprioModel>
) : RecyclerView.Adapter<MovEquipProprioAdapter.ViewHolder>() {

    var onItemClick: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemRowMovEquipProprioBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindView(dataSet[position], position)
    }

    override fun getItemCount() = dataSet.size

    inner class ViewHolder(
        itemRowMovEquipProprioBinding: ItemRowMovEquipProprioBinding
    ) : RecyclerView.ViewHolder(itemRowMovEquipProprioBinding.root) {

        private val textViewDthrMov = itemRowMovEquipProprioBinding.textViewDthrMov
        private val textViewTipoMov = itemRowMovEquipProprioBinding.textViewTipoMov
        private val textViewEquipMov = itemRowMovEquipProprioBinding.textViewEquipMov
        private val textViewMotoristaMov = itemRowMovEquipProprioBinding.textViewMotoristaMov

        fun bindView(movEquipProprioModel: MovEquipProprioModel, position: Int) {

            textViewDthrMov.text = movEquipProprioModel.dthr
            textViewTipoMov.text = movEquipProprioModel.tipo
            if(movEquipProprioModel.tipo == "ENTRADA"){
                textViewTipoMov.setTextColor(Color.BLUE)
            } else {
                textViewTipoMov.setTextColor(Color.RED)
            }
            textViewEquipMov.text = movEquipProprioModel.equip
            textViewMotoristaMov.text = movEquipProprioModel.colab

            itemView.setOnClickListener {
                onItemClick?.invoke(position)
            }

        }

    }

}