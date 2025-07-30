package com.dailyquotes.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailyquotes.app.data.entities.User
import com.dailyquotes.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

    val user: StateFlow<User?> = userRepository.getUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}