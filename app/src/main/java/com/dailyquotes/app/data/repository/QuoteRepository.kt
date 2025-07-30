package com.dailyquotes.app.data.repository

import com.dailyquotes.app.data.dao.QuoteDao
import com.dailyquotes.app.data.entities.Quote
import com.dailyquotes.app.data.entities.QuoteCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuoteRepository @Inject constructor(
    private val quoteDao: QuoteDao
) {
    fun getFavoriteQuotes(): Flow<List<Quote>> = quoteDao.getFavoriteQuotes()
    
    fun getRecentQuotes(): Flow<List<Quote>> = quoteDao.getRecentQuotes()
    
    suspend fun getRandomQuoteByCategories(categories: List<QuoteCategory>): Quote? {
        return quoteDao.getRandomQuoteByCategories(categories)
    }
    
    suspend fun getRandomQuoteByCategory(category: QuoteCategory): Quote? {
        return quoteDao.getRandomQuoteByCategory(category)
    }
    
    suspend fun toggleFavorite(quoteId: Long, isFavorite: Boolean) {
        quoteDao.updateFavoriteStatus(quoteId, isFavorite)
    }
    
    suspend fun markQuoteAsShown(quoteId: Long) {
        quoteDao.updateLastShown(quoteId, System.currentTimeMillis())
    }
    
    suspend fun initializeQuotes() {
        val currentCount = quoteDao.getQuoteCount()
        if (currentCount == 0) {
            val initialQuotes = getInitialQuotes()
            quoteDao.insertQuotes(initialQuotes)
        }
    }
    
    private fun getInitialQuotes(): List<Quote> {
        return listOf(
            // Motivational Quotes
            Quote(text = "The only way to do great work is to love what you do.", author = "Steve Jobs", category = QuoteCategory.MOTIVATIONAL),
            Quote(text = "Life is what happens to you while you're busy making other plans.", author = "John Lennon", category = QuoteCategory.MOTIVATIONAL),
            Quote(text = "The future belongs to those who believe in the beauty of their dreams.", author = "Eleanor Roosevelt", category = QuoteCategory.MOTIVATIONAL),
            Quote(text = "It is during our darkest moments that we must focus to see the light.", author = "Aristotle", category = QuoteCategory.MOTIVATIONAL),
            Quote(text = "Success is not final, failure is not fatal: it is the courage to continue that counts.", author = "Winston Churchill", category = QuoteCategory.MOTIVATIONAL),
            
            // Humor Quotes
            Quote(text = "I'm not arguing, I'm just explaining why I'm right.", author = "Unknown", category = QuoteCategory.HUMOR),
            Quote(text = "Coffee: because adulting is hard.", author = "Unknown", category = QuoteCategory.HUMOR),
            Quote(text = "I'm not lazy, I'm on energy saving mode.", author = "Unknown", category = QuoteCategory.HUMOR),
            Quote(text = "My bed is a magical place where I suddenly remember everything I forgot to do.", author = "Unknown", category = QuoteCategory.HUMOR),
            Quote(text = "I'm not late, everyone else is just early.", author = "Unknown", category = QuoteCategory.HUMOR),
            
            // Productivity Quotes
            Quote(text = "The way to get started is to quit talking and begin doing.", author = "Walt Disney", category = QuoteCategory.PRODUCTIVITY),
            Quote(text = "Don't wait for opportunity. Create it.", author = "George Bernard Shaw", category = QuoteCategory.PRODUCTIVITY),
            Quote(text = "Success is walking from failure to failure with no loss of enthusiasm.", author = "Winston Churchill", category = QuoteCategory.PRODUCTIVITY),
            Quote(text = "The only impossible journey is the one you never begin.", author = "Tony Robbins", category = QuoteCategory.PRODUCTIVITY),
            Quote(text = "Focus on being productive instead of busy.", author = "Tim Ferris", category = QuoteCategory.PRODUCTIVITY),
            
            // Religious Quotes
            Quote(text = "Faith is taking the first step even when you don't see the whole staircase.", author = "Martin Luther King Jr.", category = QuoteCategory.RELIGIOUS),
            Quote(text = "Trust in the Lord with all your heart and lean not on your own understanding.", author = "Proverbs 3:5", category = QuoteCategory.RELIGIOUS),
            Quote(text = "God has a plan for your life that is better than anything you can imagine.", author = "Unknown", category = QuoteCategory.RELIGIOUS),
            Quote(text = "Prayer is the key that opens the door to God's blessings.", author = "Unknown", category = QuoteCategory.RELIGIOUS),
            Quote(text = "Let your light shine before others.", author = "Matthew 5:16", category = QuoteCategory.RELIGIOUS),
            
            // Wellness Quotes
            Quote(text = "Your body hears everything your mind says. Stay positive.", author = "Unknown", category = QuoteCategory.WELLNESS),
            Quote(text = "Take care of your body. It's the only place you have to live.", author = "Jim Rohn", category = QuoteCategory.WELLNESS),
            Quote(text = "Healing is a matter of time, but it is sometimes also a matter of opportunity.", author = "Hippocrates", category = QuoteCategory.WELLNESS),
            Quote(text = "To keep the body in good health is a duty... otherwise we shall not be able to keep our mind strong and clear.", author = "Buddha", category = QuoteCategory.WELLNESS),
            Quote(text = "The greatest wealth is health.", author = "Virgil", category = QuoteCategory.WELLNESS),
            
            // Success Quotes
            Quote(text = "Success is not the key to happiness. Happiness is the key to success.", author = "Albert Schweitzer", category = QuoteCategory.SUCCESS),
            Quote(text = "The only place where success comes before work is in the dictionary.", author = "Vidal Sassoon", category = QuoteCategory.SUCCESS),
            Quote(text = "Success is going from failure to failure without losing your enthusiasm.", author = "Winston Churchill", category = QuoteCategory.SUCCESS),
            Quote(text = "The road to success and the road to failure are almost exactly the same.", author = "Colin R. Davis", category = QuoteCategory.SUCCESS),
            Quote(text = "Success is not in what you have, but who you are.", author = "Bo Bennett", category = QuoteCategory.SUCCESS),
            
            // Wisdom Quotes
            Quote(text = "The only true wisdom is in knowing you know nothing.", author = "Socrates", category = QuoteCategory.WISDOM),
            Quote(text = "Yesterday is history, tomorrow is a mystery, today is a gift.", author = "Eleanor Roosevelt", category = QuoteCategory.WISDOM),
            Quote(text = "In the end, we will remember not the words of our enemies, but the silence of our friends.", author = "Martin Luther King Jr.", category = QuoteCategory.WISDOM),
            Quote(text = "The journey of a thousand miles begins with one step.", author = "Lao Tzu", category = QuoteCategory.WISDOM),
            Quote(text = "It does not matter how slowly you go as long as you do not stop.", author = "Confucius", category = QuoteCategory.WISDOM),
            
            // Love Quotes
            Quote(text = "Being deeply loved by someone gives you strength, while loving someone deeply gives you courage.", author = "Lao Tzu", category = QuoteCategory.LOVE),
            Quote(text = "The best thing to hold onto in life is each other.", author = "Audrey Hepburn", category = QuoteCategory.LOVE),
            Quote(text = "Love is not about how many days, months, or years you have been together.", author = "Unknown", category = QuoteCategory.LOVE),
            Quote(text = "Where there is love there is life.", author = "Mahatma Gandhi", category = QuoteCategory.LOVE),
            Quote(text = "Love yourself first and everything else falls into line.", author = "Lucille Ball", category = QuoteCategory.LOVE),
            
            // Daily Affirmations
            Quote(text = "I am capable of achieving my dreams.", author = "Daily Affirmation", category = QuoteCategory.AFFIRMATIONS),
            Quote(text = "Today I choose to be kind to myself.", author = "Daily Affirmation", category = QuoteCategory.AFFIRMATIONS),
            Quote(text = "I am worthy of love and respect.", author = "Daily Affirmation", category = QuoteCategory.AFFIRMATIONS),
            Quote(text = "Every day I am becoming a better version of myself.", author = "Daily Affirmation", category = QuoteCategory.AFFIRMATIONS),
            Quote(text = "I have the power to create positive change in my life.", author = "Daily Affirmation", category = QuoteCategory.AFFIRMATIONS)
        )
    }
}