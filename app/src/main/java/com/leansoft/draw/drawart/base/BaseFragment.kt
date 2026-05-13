package com.leansoft.draw.drawart.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.leansoft.draw.drawart.presentation.viewmodel.MainViewModel
import com.leansoft.draw.drawart.presentation.viewmodel.NavigationViewModel

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    lateinit var mViewModel: VM
    val mainVM: MainViewModel by activityViewModels()
    val navVM: NavigationViewModel by activityViewModels()

    protected abstract fun getClassVM(): Class<VM>

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = initViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this)[getClassVM()]
        mViewModel.navigationViewModel = navVM
        mViewModel.mainViewModel = mainVM
        initView()
    }


    protected abstract fun initView()
    protected abstract fun initViewBinding(): VB

}