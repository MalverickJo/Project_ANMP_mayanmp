package com.example.mayanmp.view

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mayanmp.databinding.CardexpensesBinding
import com.example.mayanmp.databinding.DialogexpensesBinding
import com.example.mayanmp.model.ExpensesWithBudget
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExpensesAdapter(val listExpenses:ArrayList<ExpensesWithBudget>)
    : RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder>() {
    class ExpensesViewHolder(var binding: CardexpensesBinding) :
        RecyclerView.ViewHolder(binding.root)
    //keknya selesai coba check lagi deh -jocce
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        var binding = CardexpensesBinding.inflate(
            LayoutInflater.from(parent.context), parent,false)
        return ExpensesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listExpenses.size
    }

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        holder.binding.tvExpenseDate.text = formatDate(listExpenses[position].expenses.date, "dd MMM yyyy")
        holder.binding.tvExpenseAmount.text = formatRupiah(listExpenses[position].expenses.nominal)
        holder.binding.chipBudgetName.text = listExpenses[position].budget.name

        holder.binding.selectedExpneses.setOnClickListener {
            val context = holder.itemView.context
            val dialogBinding = DialogexpensesBinding.inflate(LayoutInflater.from(context))

            val dialog = AlertDialog.Builder(context)
                .setView(dialogBinding.root)
                .create()

            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val expense = listExpenses[position].expenses
            val budget = listExpenses[position].budget

            dialogBinding.chipBudgetName.text = budget.name
            dialogBinding.tvDialogAmount.text = formatRupiah(expense.nominal)
            dialogBinding.tvDialogDate.text = formatDate(expense.date, "dd MMM yyyy")
            dialogBinding.tvDialogDesc.text = expense.desc

            dialogBinding.btnCloseDialog.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }
    fun updateListBudget(newTodoList: List<ExpensesWithBudget>) {
        listExpenses.clear()
        listExpenses.addAll(newTodoList)
        notifyDataSetChanged()
    }

    fun formatDate(timestamp: Long, pattern: String = "dd-MM-yyyy"): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
    fun formatRupiah(nominal: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
        return "Rp${formatter.format(nominal)}"
    }
}