package com.leansoft.draw.drawart

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.leansoft.draw.drawart.base.BaseActivity
import com.leansoft.draw.drawart.data.source.local.pref.PreferenceHelper
import com.leansoft.draw.drawart.data.source.remote.FirebaseMgr
import com.leansoft.draw.drawart.databinding.ActivityMainBinding
import com.leansoft.draw.drawart.databinding.FragmentMainBinding
import com.leansoft.draw.drawart.presentation.viewmodel.NavigationViewModel
import com.leansoft.draw.drawart.presentation.viewmodel.NothingViewModel
import com.leansoft.draw.drawart.utils.ext.EventObserver
import com.leansoft.draw.drawart.utils.ext.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, NothingViewModel>() {
    private val navViewModel: NavigationViewModel by viewModels()

    @Inject
    lateinit var pref: PreferenceHelper

    @Inject
    lateinit var firebaseMgr: FirebaseMgr
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            while (true) {
                logRam()
                delay(6000)
            }
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navViewModel.actionDestination.observe(this, EventObserver { action ->
            navController?.safeNavigate(action.destination)
        })

        navViewModel.naviDirection.observe(this, EventObserver { action ->
            navController?.safeNavigate(action)
        })

        navViewModel.actionBack.observe(this, EventObserver {
            navController?.popBackStack()
        })

        navViewModel.actionBackToFrag.observe(this, EventObserver {
            navController?.popBackStack(destinationId = it, inclusive = true)
        })

        navViewModel.actionBackToFrag2.observe(this, EventObserver {
            navController?.popBackStack(destinationId = it, inclusive = false)
        })



    }
    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initView() {
        hideNavigationBar()
    }
    private fun logRam() {
        val activityManager =
            this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val availMem = memoryInfo.availMem / (1024 * 1024)
        val totalMem = memoryInfo.totalMem / (1024 * 1024)
        val debugInfo = Debug.MemoryInfo()
        Debug.getMemoryInfo(debugInfo)
        val appRam = debugInfo.totalPss / 1024
        Log.d("RAM", "Available: ${availMem}MB / Total: ${totalMem}MB / App: ${appRam}MB")
    }
    private fun hideNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.window.insetsController?.hide(WindowInsets.Type.navigationBars())
            this.window.insetsController?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            @Suppress("DEPRECATION")
            this.window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}