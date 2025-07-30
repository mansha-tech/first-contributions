package com.dailyquotes.app.data.dao

import androidx.room.*
import com.dailyquotes.app.data.entities.Quote
import com.dailyquotes.app.data.entities.QuoteCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quotes WHERE category IN (:categories) ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuoteByCategories(categories: List<QuoteCategory>): Quote?
    
    @Query("SELECT * FROM quotes WHERE category = :category ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuoteByCategory(category: QuoteCategory): Quote?
    
    @Query("SELECT * FROM quotes WHERE isFavorite = 1 ORDER BY lastShown DESC")
    fun getFavoriteQuotes(): Flow<List<Quote>>
    
    @Query("SELECT * FROM quotes ORDER BY lastShown DESC LIMIT 10")
    fun getRecentQuotes(): Flow<List<Quote>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes: List<Quote>)
    
    @Update
    suspend fun updateQuote(quote: Quote)
    
    @Query("UPDATE quotes SET isFavorite = :isFavorite WHERE id = :quoteId")
    suspend fun updateFavoriteStatus(quoteId: Long, isFavorite: Boolean)
    
    @Query("UPDATE quotes SET lastShown = :timestamp WHERE id = :quoteId")
    suspend fun updateLastShown(quoteId: Long, timestamp: Long)
    
    @Query("SELECT COUNT(*) FROM quotes")
    suspend fun getQuoteCount(): Int
}