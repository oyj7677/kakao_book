package com.oyj.kakaobook

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oyj.domain.usecase.GetBookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetBookListUseCase
) : ViewModel() {
    fun test() {
        Log.d(TAG, "test: ")
        viewModelScope.launch {
            useCase.invoke("도서", "accuracy").collect {
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}