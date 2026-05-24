package com.leansoft.draw.drawart.presentation.ui.create

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentCreateBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import com.leansoft.draw.drawart.utils.ext.safeOnClickListener
import kotlin.getValue

class FragmentCreate : BaseFragment<FragmentCreateBinding, NothingViewModel>() {
    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        createVM.getListFrameTemp()
        register()
        observe()
    }

    private fun register() {
        with(binding) {
            btnCreateNow.safeOnClickListener {
                createVM.convertBitmap()
            }
            lnFramePerSecond.safeOnClickListener {
                navVM.navigate(R.id.action_fragmentCreate_to_fragmentRate)
            }
        }

        setFragmentResultListener("result_key") { _, bundle ->
            val fps = bundle.getInt("frame_per_second")

        }

    }

    private fun observe() {

    }

    override fun initViewBinding(): FragmentCreateBinding {
        return FragmentCreateBinding.inflate(layoutInflater)
    }
}