package com.leansoft.draw.drawart.presentation.ui.create

import android.net.Uri
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentCreateBinding
import com.leansoft.draw.drawart.presentation.ui.background.BgViewModel
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import com.leansoft.draw.drawart.utils.ext.safeOnClickListener
import kotlin.getValue

class FragmentCreate : BaseFragment<FragmentCreateBinding, BgViewModel>() {

    private var imageUri: Uri? = null

    override fun getClassVM(): Class<BgViewModel> {
        return BgViewModel::class.java
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
            imgBackground.safeOnClickListener {
                navVM.navigate(R.id.action_fragmentCreate_to_fragmentBg)
            }
            imgPaint.safeOnClickListener {}
            imgImageEdit.safeOnClickListener {}
            imgCamera.safeOnClickListener {}
        }


        setFragmentResultListener("fps_result") { _, bundle ->
            val fps = bundle.getInt("frame_per_second")
            mViewModel.setPfsSelected(fps)
        }

        setFragmentResultListener("bg_result") { _, bundle ->
            val bg = bundle.getInt("background")
            binding.imgResult.setBackgroundResource(bg)
        }

    }

    private fun observe() {
        mViewModel.pfsSelected.observe(viewLifecycleOwner) {
            binding.tvSecond.text = it.toString()
        }
    }

    override fun initViewBinding(): FragmentCreateBinding {
        return FragmentCreateBinding.inflate(layoutInflater)
    }
}