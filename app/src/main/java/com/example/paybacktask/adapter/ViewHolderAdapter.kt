package com.example.paybacktask.adapter

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paybacktask.R
import com.example.paybacktask.databinding.ItemDataBinding
import com.example.paybacktask.model.Data
import com.example.paybacktask.view.DetailsActivity


class DataAdapter(private var dataList: ArrayList<Data>) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    private lateinit var mParent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemDataBinding: ItemDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_data,
            parent,
            false
        )
        mParent = parent
        return DataViewHolder(itemDataBinding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.itemdataBinding.data = dataList[position]

        holder.itemView.setOnClickListener {
            popUp(dataList[position])
        }
    }

    fun setUpdatas(listOfData: List<Data>) {
        dataList.clear()
        dataList.addAll(listOfData)
        notifyDataSetChanged()
    }


    private fun popUp(data: Data?) {
        val builder = AlertDialog.Builder(mParent.context)

        builder.setMessage(mParent.context.getString(R.string.more_details))
        builder.setPositiveButton(mParent.context.getString(R.string.details)) { dialog, which ->
            val intent = Intent(mParent.context, DetailsActivity::class.java)
            intent.putExtra("data", data)
            mParent.context.startActivity(intent)
        }
        builder.setNegativeButton(mParent.context.getString(R.string.cancel)) { dialog, which ->

        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    class DataViewHolder(val itemdataBinding: ItemDataBinding) :
        RecyclerView.ViewHolder(itemdataBinding.root)
}