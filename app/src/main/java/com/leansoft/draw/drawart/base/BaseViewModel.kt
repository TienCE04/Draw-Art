package com.leansoft.draw.drawart.base

import androidx.lifecycle.ViewModel
import com.leansoft.draw.drawart.presentation.viewmodel.MainViewModel
import com.leansoft.draw.drawart.presentation.viewmodel.NavigationViewModel

open class BaseViewModel: ViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var navigationViewModel: NavigationViewModel
}