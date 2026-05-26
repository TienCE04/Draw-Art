package com.leansoft.draw.drawart.presentation.ui.home

import android.os.Bundle
import android.util.Log
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentListCateBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import okhttp3.internal.notifyAll

class FragmentListCate : BaseFragment<FragmentListCateBinding, NothingViewModel>() {

    private var nameCate: String? = null
    private var level: String? = null
    private var adapter: ListCateAdapter? = null
    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        nameCate = arguments?.getString(NAME_CATE) ?: "Christmas"
        level = arguments?.getString(LEVEL)
        adapter = ListCateAdapter(
            level = level ?: "",
        ) { item ->
            mainVM.setItemAnimSelected(item)
            navVM.navigate(R.id.action_fragmentMain_to_fragmentPreview)
        }
        binding.rcvAnim.adapter=adapter
        observe()
    }

    private fun observe() {
        mainVM.animations.observe(viewLifecycleOwner) { data ->
            val list = data.filter { it.category == nameCate  && it.level == level}[0].animation
            Log.d("DEBUG", "observe: $list")
            adapter?.submitList(list)
        }

    }

    override fun initViewBinding(): FragmentListCateBinding {
        return FragmentListCateBinding.inflate(layoutInflater)
    }

    companion object {

        const val NAME_CATE = "NAME_CATE"
        const val LEVEL = "LEVEL"

        fun newInstance(
            nameCate: String,
            level: String
        ): FragmentListCate {

            return FragmentListCate().apply {

                arguments = Bundle().apply {

                    putString(NAME_CATE, nameCate)
                    putString(LEVEL, level)
                }
            }
        }
    }
}