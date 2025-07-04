package com.example.mayanmp.view

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mayanmp.databinding.FragmentAddExpensesBinding
import com.example.mayanmp.model.Budget
import com.example.mayanmp.model.Expenses
import com.example.mayanmp.model.SharePreferences
import com.example.mayanmp.viewmodel.BudgetViewModel
import com.example.mayanmp.viewmodel.ExpensesViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddExpensesFragment : Fragment() {
//keknya selesai coba check lagi deh -jocce
    private lateinit var binding: FragmentAddExpensesBinding
    private lateinit var expensesViewModel: ExpensesViewModel
    private lateinit var budgetViewModel: BudgetViewModel
    private lateinit var selectedBudget: Budget

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddExpensesBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expensesViewModel = ViewModelProvider(this)[ExpensesViewModel::class.java]
        budgetViewModel = ViewModelProvider(this)[BudgetViewModel::class.java]
        val sharePreferences = SharePreferences(requireContext())
        val userId = sharePreferences.getUserId()
        budgetViewModel.getAllBudget(userId)
        observeViewModel()
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        binding.etExpenseDate.setText(dateFormat.format(calendar.time))

        binding.etExpenseDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                binding.etExpenseDate.setText(dateFormat.format(calendar.time))
            }, year, month, day)

            datePicker.show()
        }
        binding.btnSubmitExpense.setOnClickListener {
            val date = dateStringToLong(binding.etExpenseDate.text.toString())
            val idBudget = selectedBudget.id
            val nominal = binding.etExpenseAmount.text.toString().toIntOrNull()
            val desc = binding.etExpenseDescription.text.toString()

            if (date == null || nominal == null || idBudget == null || desc == null) {
                Toast.makeText(requireContext(), "Please Fill All The Blanks", Toast.LENGTH_SHORT).show()
            } else if (nominal > selectedBudget.budgetLeft) {
                Toast.makeText(requireContext(), "nominal is more than your left budget", Toast.LENGTH_SHORT).show()
            } else {
                val expensesEntity = Expenses(
                    date = date,
                    idBudget = idBudget,
                    nominal = nominal,
                    desc = desc,
                    idUser = userId
                )
                expensesViewModel.addExpenses(expensesEntity)
                Toast.makeText(requireContext(), "Expenses Added Succesfully", Toast.LENGTH_LONG).show()
                Navigation.findNavController(it).popBackStack() // lebih clean
            }
        }
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }
    fun observeViewModel() {
        budgetViewModel.dataBudget.observe(viewLifecycleOwner, Observer { budgets ->
            if (budgets != null) {
                val budgetNames = budgets.map { it.name }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, budgetNames)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                binding.spinnerBudget.adapter = adapter

                binding.spinnerBudget.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                        selectedBudget = budgets[position]
                        budgetViewModel.selectedBudget.value = selectedBudget
                        binding.tvRemaining.text = formatRupiah(selectedBudget.budgetLeft)
                        binding.tvTotalBudget.text = formatRupiah(selectedBudget.nominal)
                        binding.pbBudget.max = selectedBudget.nominal
                        binding.pbBudget.progress = selectedBudget.budgetLeft
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        budgetViewModel.selectedBudget.value = null
                    }
                }
            }
        })
    }
    fun formatRupiah(nominal: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
        return "Rp${formatter.format(nominal)}"
    }
    fun dateStringToLong(dateStr: String, pattern: String = "dd-MMM-yyyy"): Long {
        return try {
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            val date = sdf.parse(dateStr)
            date?.time ?: 0L  // kalo null return 0
        } catch (e: Exception) {
            0L
        }
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddExpensesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}