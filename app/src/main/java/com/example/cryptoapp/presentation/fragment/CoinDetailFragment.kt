package com.example.cryptoapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.example.cryptoapp.presentation.viewmodel.CoinViewModel
import com.example.cryptoapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class CoinDetailFragment : Fragment() {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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

    private fun getSymbol(): String = requireArguments().getString(EXTRA_FROM_SYMBOL, EMPTY_RESULT)

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