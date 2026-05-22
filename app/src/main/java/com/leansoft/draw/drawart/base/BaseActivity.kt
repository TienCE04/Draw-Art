package com.leansoft.draw.drawart.base

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected lateinit var mViewModel: VM
    abstract fun initViewBinding(): VB
    abstract fun getClassVM(): Class<VM>
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = initViewBinding()
        setContentView(_binding!!.root)
        mViewModel = ViewModelProvider(this)[getClassVM()]
        initView()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}