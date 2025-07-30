package com.dailyquotes.app.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dailyquotes.app.data.entities.Quote

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsStateWithLifecycle()
    val currentQuote by viewModel.currentQuote.collectAsStateWithLifecycle()
    val favoriteQuotes by viewModel.favoriteQuotes.collectAsStateWithLifecycle()
    val recentQuotes by viewModel.recentQuotes.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        user?.let {
            Text(
                text = "Good day, ${it.name}! 👋",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "Here's your daily inspiration",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Today's Quote Card
            item {
                TodaysQuoteCard(
                    quote = currentQuote,
                    isLoading = isLoading,
                    onFavoriteClick = { quote -> viewModel.toggleFavorite(quote) },
                    onRefreshClick = { viewModel.loadTodaysQuote() }
                )
            }

            // Settings Card
            item {
                user?.let {
                    SettingsCard(
                        user = it,
                        onNotificationToggle = viewModel::updateNotificationSettings
                    )
                }
            }

            // Favorite Quotes Section
            if (favoriteQuotes.isNotEmpty()) {
                item {
                    Text(
                        text = "Your Favorite Quotes",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(favoriteQuotes.take(3)) { quote ->
                    QuoteCard(
                        quote = quote,
                        onFavoriteClick = { viewModel.toggleFavorite(quote) }
                    )
                }
            }

            // Recent Quotes Section
            if (recentQuotes.isNotEmpty()) {
                item {
                    Text(
                        text = "Recent Quotes",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(recentQuotes.take(3)) { quote ->
                    QuoteCard(
                        quote = quote,
                        onFavoriteClick = { viewModel.toggleFavorite(quote) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TodaysQuoteCard(
    quote: Quote?,
    isLoading: Boolean,
    onFavoriteClick: (Quote) -> Unit,
    onRefreshClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Today's Quote",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
                IconButton(onClick = onRefreshClick) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Get new quote",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(32.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            } else if (quote != null) {
                Text(
                    text = "\"${quote.text}\"",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "— ${quote.author}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Chip(
                    onClick = { },
                    label = { Text(quote.category.displayName) },
                    colors = ChipDefaults.chipColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        labelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                IconButton(
                    onClick = { onFavoriteClick(quote) }
                ) {
                    Icon(
                        imageVector = if (quote.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Toggle favorite",
                        tint = if (quote.isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            } else {
                Text(
                    text = "No quote available. Tap refresh to try again.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }
    }
}

@Composable
private fun SettingsCard(
    user: com.dailyquotes.app.data.entities.User,
    onNotificationToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Daily Notifications",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Receive quotes at ${user.preferredTime}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Switch(
                    checked = user.isNotificationsEnabled,
                    onCheckedChange = onNotificationToggle
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Category,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Categories: ${user.selectedCategories.size} selected",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun QuoteCard(
    quote: Quote,
    onFavoriteClick: (Quote) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "\"${quote.text}\"",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "— ${quote.author}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Chip(
                        onClick = { },
                        label = { Text(quote.category.displayName) }
                    )
                }

                IconButton(
                    onClick = { onFavoriteClick(quote) }
                ) {
                    Icon(
                        imageVector = if (quote.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Toggle favorite",
                        tint = if (quote.isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}