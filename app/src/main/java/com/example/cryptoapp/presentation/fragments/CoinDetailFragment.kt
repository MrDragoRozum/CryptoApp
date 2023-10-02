package com.example.cryptoapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.example.cryptoapp.presentation.viewmodels.CoinViewModel
import com.example.cryptoapp.presentation.viewmodels.CoinViewModelFactory

class CoinDetailFragment : Fragment() {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding get() =  _binding!!

    private val viewModelFactory by lazy {
        CoinViewModelFactory(
            requireActivity().application,
            requireArguments().getString(EXTRA_FROM_SYMBOL, EMPTY_RESULT)
        )
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_RESULT = ""
        fun newInstance(fSym: String) = CoinDetailFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_FROM_SYMBOL, fSym)
            }
        }
    }

}