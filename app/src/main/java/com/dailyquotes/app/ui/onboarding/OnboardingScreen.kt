package com.dailyquotes.app.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dailyquotes.app.data.entities.QuoteCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onCompleted: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isCompleted) {
        if (uiState.isCompleted) {
            onCompleted()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to Daily Quotes!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = "Let's personalize your daily inspiration",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                NameSection(
                    name = uiState.name,
                    onNameChange = viewModel::updateName
                )
            }

            item {
                TimePreferenceSection(
                    selectedTime = uiState.preferredTime,
                    onTimeChange = viewModel::updatePreferredTime
                )
            }

            item {
                MotivationSection(
                    motivationLevel = uiState.motivationLevel,
                    onMotivationChange = viewModel::updateMotivationLevel
                )
            }

            item {
                StressSection(
                    stressLevel = uiState.stressLevel,
                    onStressChange = viewModel::updateStressLevel
                )
            }

            item {
                GoalsSection(
                    selectedGoals = uiState.mainGoals,
                    onGoalsChange = viewModel::updateMainGoals
                )
            }

            item {
                ChallengesSection(
                    selectedChallenges = uiState.currentChallenges,
                    onChallengesChange = viewModel::updateCurrentChallenges
                )
            }

            item {
                ToneSection(
                    selectedTone = uiState.preferredTone,
                    onToneChange = viewModel::updatePreferredTone
                )
            }

            item {
                CategorySection(
                    selectedCategories = uiState.selectedCategories,
                    onCategoriesChange = viewModel::updateSelectedCategories
                )
            }
        }

        Button(
            onClick = viewModel::completeOnboarding,
            enabled = uiState.isValid() && !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Complete Setup")
            }
        }
    }
}

@Composable
private fun NameSection(
    name: String,
    onNameChange: (String) -> Unit
) {
    Column {
        Text(
            text = "What's your name?",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            placeholder = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun TimePreferenceSection(
    selectedTime: String,
    onTimeChange: (String) -> Unit
) {
    Column {
        Text(
            text = "When would you like to receive your daily quotes?",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        val timeOptions = listOf(
            "06:00" to "6:00 AM - Early Bird",
            "09:00" to "9:00 AM - Morning Motivation",
            "12:00" to "12:00 PM - Midday Boost",
            "18:00" to "6:00 PM - Evening Reflection",
            "21:00" to "9:00 PM - Night Inspiration"
        )

        Column(modifier = Modifier.selectableGroup()) {
            timeOptions.forEach { (time, label) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = selectedTime == time,
                            onClick = { onTimeChange(time) },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedTime == time,
                        onClick = null
                    )
                    Text(
                        text = label,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun MotivationSection(
    motivationLevel: Int,
    onMotivationChange: (Int) -> Unit
) {
    Column {
        Text(
            text = "How motivated do you feel currently? (1-5)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (1..5).forEach { level ->
                FilterChip(
                    onClick = { onMotivationChange(level) },
                    label = { Text(level.toString()) },
                    selected = motivationLevel == level
                )
            }
        }
    }
}

@Composable
private fun StressSection(
    stressLevel: Int,
    onStressChange: (Int) -> Unit
) {
    Column {
        Text(
            text = "How stressed do you feel lately? (1-5)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (1..5).forEach { level ->
                FilterChip(
                    onClick = { onStressChange(level) },
                    label = { Text(level.toString()) },
                    selected = stressLevel == level
                )
            }
        }
    }
}

@Composable
private fun GoalsSection(
    selectedGoals: List<String>,
    onGoalsChange: (List<String>) -> Unit
) {
    val goalOptions = listOf(
        "Career Growth", "Health & Fitness", "Personal Development",
        "Relationships", "Financial Freedom", "Learning & Education",
        "Creativity", "Spirituality", "Work-Life Balance"
    )

    Column {
        Text(
            text = "What are your main goals? (Select all that apply)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        goalOptions.chunked(3).forEach { rowGoals ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowGoals.forEach { goal ->
                    FilterChip(
                        onClick = {
                            if (selectedGoals.contains(goal)) {
                                onGoalsChange(selectedGoals - goal)
                            } else {
                                onGoalsChange(selectedGoals + goal)
                            }
                        },
                        label = { Text(goal) },
                        selected = selectedGoals.contains(goal),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ChallengesSection(
    selectedChallenges: List<String>,
    onChallengesChange: (List<String>) -> Unit
) {
    val challengeOptions = listOf(
        "Lack of Motivation", "Time Management", "Stress",
        "Self-Doubt", "Procrastination", "Work Pressure",
        "Loneliness", "Anxiety", "Focus Issues"
    )

    Column {
        Text(
            text = "What challenges are you facing? (Select all that apply)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        challengeOptions.chunked(3).forEach { rowChallenges ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowChallenges.forEach { challenge ->
                    FilterChip(
                        onClick = {
                            if (selectedChallenges.contains(challenge)) {
                                onChallengesChange(selectedChallenges - challenge)
                            } else {
                                onChallengesChange(selectedChallenges + challenge)
                            }
                        },
                        label = { Text(challenge) },
                        selected = selectedChallenges.contains(challenge),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ToneSection(
    selectedTone: String,
    onToneChange: (String) -> Unit
) {
    val toneOptions = listOf(
        "uplifting" to "Uplifting & Positive",
        "serious" to "Serious & Thoughtful",
        "humorous" to "Light & Humorous",
        "spiritual" to "Spiritual & Deep"
    )

    Column {
        Text(
            text = "What tone do you prefer for your quotes?",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.selectableGroup()) {
            toneOptions.forEach { (tone, label) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = selectedTone == tone,
                            onClick = { onToneChange(tone) },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedTone == tone,
                        onClick = null
                    )
                    Text(
                        text = label,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CategorySection(
    selectedCategories: List<QuoteCategory>,
    onCategoriesChange: (List<QuoteCategory>) -> Unit
) {
    Column {
        Text(
            text = "Choose your favorite quote categories:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        QuoteCategory.values().chunked(3).forEach { rowCategories ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowCategories.forEach { category ->
                    FilterChip(
                        onClick = {
                            if (selectedCategories.contains(category)) {
                                onCategoriesChange(selectedCategories - category)
                            } else {
                                onCategoriesChange(selectedCategories + category)
                            }
                        },
                        label = { Text(category.displayName) },
                        selected = selectedCategories.contains(category),
                        leadingIcon = if (selectedCategories.contains(category)) {
                            { Icon(Icons.Default.Check, contentDescription = null) }
                        } else null,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}