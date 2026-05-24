package com.leansoft.draw.drawart.presentation.ui.project

import android.os.Bundle
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentProjectDetailBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel

class FragmentProjectDetail : BaseFragment<FragmentProjectDetailBinding, NothingViewModel>() {

    private var adapter: ProjectAdapter? = null
    private lateinit var typeProject: String
    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        typeProject = arguments?.getString(KEY_TYPE_PROJECT) ?: TypeProject.PROJECT.name
        observe()
    }

    private fun observe() {

    }

    override fun initViewBinding(): FragmentProjectDetailBinding {
        return FragmentProjectDetailBinding.inflate(layoutInflater)
    }

    companion object {
        const val KEY_TYPE_PROJECT = "KEY_TYPE_PROJECT"
        fun newInstance(typeProject: TypeProject) = FragmentProjectDetail().apply {
            arguments = Bundle().apply {
                putString(KEY_TYPE_PROJECT, typeProject.name)
            }
        }
    }
}

enum class TypeProject {
    PROJECT,
    MOVIE
}