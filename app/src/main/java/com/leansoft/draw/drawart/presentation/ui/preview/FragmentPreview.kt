package com.leansoft.draw.drawart.presentation.ui.preview

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import com.leansoft.draw.drawart.R
import com.leansoft.draw.drawart.base.BaseFragment
import com.leansoft.draw.drawart.databinding.FragmentPreviewBinding
import com.leansoft.draw.drawart.presentation.ui.draw.FrameSmallAdapter
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import com.leansoft.draw.drawart.utils.FrameAnimationPlayer
import com.leansoft.draw.drawart.utils.ext.loadAsset
import com.leansoft.draw.drawart.utils.ext.loadImage
import com.leansoft.draw.drawart.utils.ext.safeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentPreview : BaseFragment<FragmentPreviewBinding, PreviewVM>() {
    private val frameAnimationPlayer = FrameAnimationPlayer()
    private var adapter: FramePreviewAdapter? = null
    override fun getClassVM(): Class<PreviewVM> {
        return PreviewVM::class.java
    }

    override fun initView() {
        adapter = FramePreviewAdapter { item, position -> }
        binding.rcvFrame.adapter = adapter
        register()
        observe()
    }

    private fun register() {
        with(binding) {
            layoutHeader.tvHeader.text = getString(R.string.msg_preview)
            modeDraw.safeOnClickListener {
                val directions =
                    FragmentPreviewDirections.actionFragmentPreviewToFragmentDrawFrame(useRecord = false)
                navVM.navigate(directions)
            }
            modeTemplate.safeOnClickListener {
                val directions =
                    FragmentPreviewDirections.actionFragmentPreviewToFragmentDrawFrame(useRecord = true)
                navVM.navigate(directions)
            }
            layoutHeader.ivLogo.safeOnClickListener {
                navVM.back()
            }
        }
    }

    private fun observe() {
        mainVM.itemAnimSelected.observe(viewLifecycleOwner) { data ->
            data?.let {
                with(binding) {
                    imgPreview.loadAsset(it.listFrame[0])
                    tvNumberFrame.text = it.listFrame.size.toString()
                    imgThumbUse.loadAsset(it.listFrame[0], placeholder = R.drawable.img_thumb_temp)
                    imgThumbDraw.loadAsset(
                        it.listFrame[0],
                        alphaValue = 0.4f,
                        placeholder = R.drawable.img_thumb_temp
                    )

                    Log.d("DEBUG", "observe-list: ${it.listFrame}")
                    adapter?.setList(it.listFrame)
                }
            }
        }
    }

    override fun initViewBinding(): FragmentPreviewBinding {
        return FragmentPreviewBinding.inflate(layoutInflater)
    }
}