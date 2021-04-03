package com.app.bbaumeet.Ui.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.bbaumeet.Data.Repository.BaseRepository
import com.app.bbaumeet.Data.Repository.HomeRepository
import com.app.bbaumeet.Data.Repository.ProfileRepository
import com.app.bbaumeet.Data.Repository.SettingRepository
import com.app.bbaumeet.Ui.Home.HomeViewModel
import com.app.bbaumeet.Ui.Profile.ProfileViewModel
import com.app.bbaumeet.Ui.Setting.SettingViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> SettingViewModel(repository as SettingRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository as ProfileRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as HomeRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }

}