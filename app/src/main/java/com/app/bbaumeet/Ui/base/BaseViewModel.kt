package com.app.befit.ui.base

import androidx.lifecycle.ViewModel
import com.app.befit.data.network.ApiCall.UserApi
import com.app.befit.data.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel(private val repository: BaseRepository) : ViewModel() {
    suspend fun logout(api: UserApi) = withContext(Dispatchers.IO) {  }
}