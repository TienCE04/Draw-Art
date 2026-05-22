package com.leansoft.draw.drawart.presentation.ui.home

import android.os.Bundle
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentListCateBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel

class FragmentListCate : BaseFragment<FragmentListCateBinding, NothingViewModel>() {
    private var nameCate: String? = null
    private var adapter: FragmentListCateAdapter? = null
    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        nameCate = arguments?.getString(NAME_CATE) ?: "Christmas"
        adapter = FragmentListCateAdapter(requireContext()) { item ->
            mainVM.setItemAnimSelected(item)
            navVM.navigate(R.id.action_fragmentMain_to_fragmentPreview)
        }
        observe()
    }

    private fun observe() {
        mainVM.categories.observe(viewLifecycleOwner) { data ->
            val list = data.filter { it.nameCategory == nameCate }[0].data
            adapter?.submitList(list)
        }

    }

    override fun initViewBinding(): FragmentListCateBinding {
        return FragmentListCateBinding.inflate(layoutInflater)
    }

    companion object {
        const val NAME_CATE = "NAME_CATE"
        fun newInstance(nameCate: String): FragmentListCate {
            val fragment = FragmentListCate()
            val bundle = Bundle()
            bundle.putString(NAME_CATE, nameCate)
            fragment.arguments = bundle
            return fragment
        }
    }
}