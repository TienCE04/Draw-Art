package com.leansoft.draw.drawart.presentation.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.leansoft.draw.drawart.utils.ext.Event

class NavigationViewModel : ViewModel() {
    private val _actionDestination = MutableLiveData<Event<ActionNavigate>>()
    val actionDestination = _actionDestination

    private val _naviDirection = MutableLiveData<Event<NavDirections>>()
    val naviDirection = _naviDirection

    private val _actionBack = MutableLiveData<Event<Unit>>()
    val actionBack = _actionBack


    private val _actionBackToFrag = MutableLiveData<Event<Int>>()
    val actionBackToFrag = _actionBackToFrag


    //xoa cac fragment tren backstack
    private val _actionBackToFrag2 = MutableLiveData<Event<Int>>()
    val actionBackToFrag2: LiveData<Event<Int>> = _actionBackToFrag2

    fun back() {
        _actionBack.value = Event(Unit)
    }

    fun fragFrag(destination: Int) {
        _actionBackToFrag.value = Event(destination)
    }

    fun backFrg2(destinationId: Int) {
        _actionBackToFrag2.postValue(Event(destinationId))
    }

    fun navigate(destination: Int, bundle: Bundle? = null, navOptions: NavOptions? = null) {
        _actionDestination.postValue(Event(ActionNavigate(destination, bundle, navOptions)))
    }

    fun navigate(naviDirections: NavDirections) {
        _naviDirection.postValue(Event(naviDirections))
    }

    fun navigate(action: ActionNavigate) {
        _actionDestination.postValue(Event(action))
    }
}

data class ActionNavigate(
    val destination: Int,
    val args: Bundle? = null,
    var navOptions: NavOptions? = null
)