package com.singularitycoder.memoryjog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    val menuLiveData = MutableLiveData<MenuAction>()
    val questionLiveData = MutableLiveData<Question>()
    val questionAccidentBackupLiveData = MutableLiveData<Question?>()
}