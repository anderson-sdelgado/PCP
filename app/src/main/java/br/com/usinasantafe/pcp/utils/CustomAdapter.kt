package br.com.usinasantafe.pcp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.usinasantafe.pcp.databinding.ItemRowTextBinding

class CustomAdapter(
    private val dataSet: List<String>
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var onItemClick: ((text: String, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemRowTextBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindView(dataSet[position], position)
    }

    override fun getItemCount() = dataSet.size

    inner class ViewHolder(
        textRowItemBinding: ItemRowTextBinding
    ) : RecyclerView.ViewHolder(textRowItemBinding.root) {

        private val textView: TextView = textRowItemBinding.textView

        fun bindView(text: String, position: Int) {
            textView.text = text

            itemView.setOnClickListener {
                onItemClick?.invoke(text, position)
            }

        }

    }

}