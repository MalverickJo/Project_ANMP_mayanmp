package com.example.mayanmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mayanmp.databinding.FragmentProfileBinding
import com.example.mayanmp.model.SharePreferences
import com.example.mayanmp.viewmodel.UserViewModel

class ProfileFragment : Fragment() {
    // profile harusnya sudah -yeda
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var sharePreferences: SharePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        binding.viewModel = userViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        sharePreferences = SharePreferences(requireContext())

        val idUser = sharePreferences.getUserId()

        userViewModel.getUserData(idUser)
        observeViewModel()

    }
    fun observeViewModel(){
        userViewModel.selectedUser.observe(viewLifecycleOwner, Observer {
            binding.tvFirstName.setText(it.firstname)
            binding.tvLastName.setText(it.lastname)
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}