package com.example.mayanmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mayanmp.databinding.FragmentAddBudgetBinding
import com.example.mayanmp.model.Budget
import com.example.mayanmp.model.SharePreferences
import com.example.mayanmp.viewmodel.BudgetViewModel

class AddBudgetFragment : Fragment() {
    //badjet done wak -alex
    private lateinit var binding: FragmentAddBudgetBinding
    private lateinit var budgetViewModel: BudgetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBudgetBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        budgetViewModel = ViewModelProvider(this)[BudgetViewModel::class.java]

        val sharePreferences = SharePreferences(requireContext())
        val userId = sharePreferences.getUserId()

        binding.btnSubmitBudget.setOnClickListener {
            val name = binding.etBudgetName.text.toString()
            val nominal = binding.etBudgetNominal.text.toString().toIntOrNull()
            val budgetLeft = nominal


            if (name.isBlank() || nominal == null || userId == null) {
                Toast.makeText(requireContext(), "Please Fill All The Blanks", Toast.LENGTH_SHORT).show()
            } else {
                val budgetEntity = Budget(
                    name = name,
                    nominal = nominal,
                    budgetLeft = budgetLeft!!,
                    idUser = userId
                )
                budgetViewModel.addBudget(budgetEntity)
                Toast.makeText(requireContext(), "Budget Added Succesfully", Toast.LENGTH_LONG).show()
                Navigation.findNavController(it).popBackStack() // lebih clean
            }
        }
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }
}