package vn.ngphong.musiccc.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import vn.ngphong.musiccc.base.BaseViewModel
import vn.ngphong.musiccc.util.NetworkHelper
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var networkHelper: NetworkHelper
    val TAG = "ngphong"
}