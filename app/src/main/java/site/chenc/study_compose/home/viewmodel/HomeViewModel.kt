package site.chenc.study_compose.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
): ViewModel() {
    fun showSnackBar(msg: String) {
        Log.d("HomeViewModel", "showSnackBar: $msg")
    }
}