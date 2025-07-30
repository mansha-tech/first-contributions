package com.dailyquotes.app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailyquotes.app.data.entities.Quote
import com.dailyquotes.app.data.entities.QuoteCategory
import com.dailyquotes.app.data.entities.User
import com.dailyquotes.app.data.repository.QuoteRepository
import com.dailyquotes.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    val user: StateFlow<User?> = userRepository.getUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _currentQuote = MutableStateFlow<Quote?>(null)
    val currentQuote: StateFlow<Quote?> = _currentQuote.asStateFlow()

    val favoriteQuotes: StateFlow<List<Quote>> = quoteRepository.getFavoriteQuotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val recentQuotes: StateFlow<List<Quote>> = quoteRepository.getRecentQuotes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadTodaysQuote()
    }

    fun loadTodaysQuote() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentUser = user.value
                val quote = if (currentUser?.selectedCategories?.isNotEmpty() == true) {
                    val categories = currentUser.selectedCategories.map { QuoteCategory.valueOf(it) }
                    quoteRepository.getRandomQuoteByCategories(categories)
                } else {
                    quoteRepository.getRandomQuoteByCategory(QuoteCategory.MOTIVATIONAL)
                }
                
                quote?.let {
                    quoteRepository.markQuoteAsShown(it.id)
                    _currentQuote.value = it
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(quote: Quote) {
        viewModelScope.launch {
            try {
                quoteRepository.toggleFavorite(quote.id, !quote.isFavorite)
                // Update current quote if it's the same one
                if (_currentQuote.value?.id == quote.id) {
                    _currentQuote.value = quote.copy(isFavorite = !quote.isFavorite)
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun updateNotificationSettings(enabled: Boolean) {
        viewModelScope.launch {
            try {
                userRepository.updateNotificationSettings(enabled)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}