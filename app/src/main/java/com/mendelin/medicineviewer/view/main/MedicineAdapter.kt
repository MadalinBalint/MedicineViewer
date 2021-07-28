package com.mendelin.medicineviewer.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.medicineviewer.ItemMedicineBinding
import com.mendelin.medicineviewer.model.data.AnmdmMedicine
import timber.log.Timber

class MedicineAdapter: ListAdapter<AnmdmMedicine, MedicineAdapter.MedicineViewHolder>(MedicineDiffCallBack()) {
    private val items: MutableList<AnmdmMedicine> = mutableListOf()

    class MedicineViewHolder(var binding: ItemMedicineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(drug: AnmdmMedicine) {
            binding.property = drug
            binding.executePendingBindings()
        }
    }

    class MedicineDiffCallBack : DiffUtil.ItemCallback<AnmdmMedicine>() {
        override fun areItemsTheSame(oldItem: AnmdmMedicine, newItem: AnmdmMedicine): Boolean {
            return oldItem.cim == newItem.cim
        }

        override fun areContentsTheSame(oldItem: AnmdmMedicine, newItem: AnmdmMedicine): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        return MedicineViewHolder(ItemMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun add(list: List<AnmdmMedicine>) {
        Timber.e("${list.size}")
        items.addAll(list)
        submitList(items)
        notifyDataSetChanged()
    }
}