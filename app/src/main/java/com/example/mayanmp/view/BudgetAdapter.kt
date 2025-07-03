package com.example.mayanmp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mayanmp.databinding.CardbudgetBinding
import com.example.mayanmp.model.Budget
import java.text.NumberFormat
import java.util.Locale

class BudgetAdapter(val listBudget:ArrayList<Budget>)
    : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {
    class BudgetViewHolder(var binding: CardbudgetBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        var binding = CardbudgetBinding.inflate(
            LayoutInflater.from(parent.context), parent,false)
        return BudgetViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listBudget.size
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.binding.tvBudgetName.text = listBudget[position].name
        holder.binding.tvBudgetNominal.text = formatRupiah(listBudget[position].nominal)
        holder.binding.selectedBudget.setOnClickListener{
            val action = BudgetFragmentDirections.actionToEditBudget(listBudget[position].id)
            Navigation.findNavController(it).navigate(action)
        }
    }


    fun updateListBudget(newTodoList: List<Budget>) {
        listBudget.clear()
        listBudget.addAll(newTodoList)
        notifyDataSetChanged()
    }

    fun formatRupiah(nominal: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
        return "Rp${formatter.format(nominal)}"
    }
}