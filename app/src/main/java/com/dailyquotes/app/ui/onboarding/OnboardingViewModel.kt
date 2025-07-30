package com.dailyquotes.app.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailyquotes.app.data.entities.QuoteCategory
import com.dailyquotes.app.data.entities.SurveyResponse
import com.dailyquotes.app.data.repository.QuoteRepository
import com.dailyquotes.app.data.repository.UserRepository
import com.dailyquotes.app.utils.NotificationScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val quoteRepository: QuoteRepository,
    private val notificationScheduler: NotificationScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        // Initialize quotes when ViewModel is created
        viewModelScope.launch {
            quoteRepository.initializeQuotes()
        }
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updatePreferredTime(time: String) {
        _uiState.value = _uiState.value.copy(preferredTime = time)
    }

    fun updateMotivationLevel(level: Int) {
        _uiState.value = _uiState.value.copy(motivationLevel = level)
    }

    fun updateStressLevel(level: Int) {
        _uiState.value = _uiState.value.copy(stressLevel = level)
    }

    fun updateMainGoals(goals: List<String>) {
        _uiState.value = _uiState.value.copy(mainGoals = goals)
    }

    fun updateCurrentChallenges(challenges: List<String>) {
        _uiState.value = _uiState.value.copy(currentChallenges = challenges)
    }

    fun updatePreferredTone(tone: String) {
        _uiState.value = _uiState.value.copy(preferredTone = tone)
    }

    fun updateSelectedCategories(categories: List<QuoteCategory>) {
        _uiState.value = _uiState.value.copy(selectedCategories = categories)
    }

    fun completeOnboarding() {
        val state = _uiState.value
        if (!state.isValid()) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Create user
                userRepository.createUser(state.name, state.preferredTime)

                // Save survey response
                val surveyResponse = SurveyResponse(
                    motivationLevel = state.motivationLevel,
                    stressLevel = state.stressLevel,
                    mainGoals = state.mainGoals,
                    currentChallenges = state.currentChallenges,
                    preferredTone = state.preferredTone
                )
                userRepository.saveSurveyResponse(surveyResponse)

                // Update selected categories
                val categoryNames = state.selectedCategories.map { it.name }
                userRepository.updateSelectedCategories(categoryNames)

                // Complete onboarding
                userRepository.completeOnboarding()

                // Schedule notifications
                notificationScheduler.scheduleDaily(state.preferredTime)

                _uiState.value = _uiState.value.copy(isCompleted = true)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}

data class OnboardingUiState(
    val currentStep: Int = 0,
    val name: String = "",
    val preferredTime: String = "09:00",
    val motivationLevel: Int = 3,
    val stressLevel: Int = 3,
    val mainGoals: List<String> = emptyList(),
    val currentChallenges: List<String> = emptyList(),
    val preferredTone: String = "uplifting",
    val selectedCategories: List<QuoteCategory> = listOf(QuoteCategory.MOTIVATIONAL),
    val isCompleted: Boolean = false
) {
    fun isValid(): Boolean {
        return name.isNotBlank() && 
               preferredTime.isNotBlank() && 
               selectedCategories.isNotEmpty()
    }
}