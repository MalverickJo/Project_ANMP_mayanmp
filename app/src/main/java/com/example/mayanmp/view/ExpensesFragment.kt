package com.example.mayanmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mayanmp.databinding.FragmentExpensesBinding
import com.example.mayanmp.model.SharePreferences
import com.example.mayanmp.viewmodel.ExpensesViewModel


class ExpensesFragment : Fragment() {
    private lateinit var binding : FragmentExpensesBinding
    private lateinit var expensesViewModel: ExpensesViewModel
    private val expensesListAdapter  = ExpensesAdapter(arrayListOf())
    private lateinit var sharePreferences: SharePreferences
    //keknya selesai coba check lagi deh -jocce
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expensesViewModel = ViewModelProvider(this)[ExpensesViewModel::class.java]
        sharePreferences = SharePreferences(requireContext())

        val idUser = sharePreferences.getUserId()

        expensesViewModel.getAllExpenses(idUser)

        binding.recExpenses.layoutManager = LinearLayoutManager(context)
        binding.recExpenses.adapter = expensesListAdapter

        binding.btnAddExpense.setOnClickListener {
            val action = ExpensesFragmentDirections.actionToAddExpenses()
            Navigation.findNavController(it).navigate(action)
        }
        observeViewModel()
    }
    fun observeViewModel() {
        expensesViewModel.dataExpenses.observe(viewLifecycleOwner, Observer {
            expensesListAdapter.updateListBudget(it)
            if (it.isEmpty()) {
                binding.recExpenses?.visibility = View.GONE
            } else {
                binding.recExpenses?.visibility = View.VISIBLE
            }
        })
    }

}