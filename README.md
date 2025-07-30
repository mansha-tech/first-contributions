# Daily Quotes App 📱✨

A personalized daily quote delivery app built with **Kotlin** and **Jetpack Compose** that sends users inspirational quotes based on their preferences and survey responses.

## 🌟 Features

### 📋 **Personalized Onboarding Survey**
- **User Profile Setup**: Name and preferred notification time
- **Motivation & Stress Assessment**: 1-5 scale rating system
- **Goal Selection**: Career, health, relationships, creativity, and more
- **Challenge Identification**: Stress, procrastination, self-doubt, etc.
- **Tone Preferences**: Uplifting, serious, humorous, or spiritual
- **Category Selection**: 9 different quote categories

### 📱 **Quote Categories**
- 🎯 **Motivational** - Inspire and energize
- 😄 **Humor** - Lighten the mood
- ⚡ **Productivity** - Focus and achievement
- 🙏 **Religious** - Spiritual guidance
- 🧘 **Wellness** - Health and mindfulness
- 🏆 **Success** - Achievement and growth
- 🧠 **Wisdom** - Life insights
- ❤️ **Love** - Relationships and self-love
- ✨ **Daily Affirmations** - Positive self-talk

### 🔔 **Smart Notification System**
- **Scheduled Delivery**: Daily quotes at user's preferred time
- **WorkManager Integration**: Reliable background scheduling
- **Category-Based Selection**: Quotes matched to user preferences
- **Notification Channels**: Organized and manageable

### 🎨 **Modern UI/UX**
- **Material Design 3**: Latest design system
- **Jetpack Compose**: Modern declarative UI
- **Dynamic Theming**: Adapts to system preferences
- **Responsive Design**: Works on all screen sizes

### 💾 **Data Management**
- **Room Database**: Local storage for offline access
- **User Preferences**: Persistent settings and favorites
- **Quote History**: Track viewed and favorite quotes
- **Survey Responses**: Store personality insights

## 🏗️ **Architecture**

### **Modern Android Architecture**
- **MVVM Pattern**: ViewModel, Repository, and UI layers
- **Dependency Injection**: Hilt for clean dependency management
- **Reactive Programming**: Flow and StateFlow for reactive updates
- **Single Source of Truth**: Room database as primary data source

### **Tech Stack**
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Database**: Room
- **Background Work**: WorkManager
- **Dependency Injection**: Hilt
- **Architecture**: MVVM + Repository Pattern
- **Async**: Kotlin Coroutines & Flow

## 📱 **App Flow**

1. **First Launch**: Comprehensive onboarding survey
2. **Profile Creation**: User setup with preferences
3. **Category Selection**: Choose favorite quote types
4. **Daily Experience**: Receive personalized quotes
5. **Interaction**: Mark favorites, refresh quotes
6. **Settings Management**: Adjust notifications and preferences

## 🚀 **Getting Started**

### **Prerequisites**
- Android Studio Arctic Fox or newer
- Android SDK 24+ (Android 7.0)
- Kotlin 1.9.25+

### **Installation**
1. Clone the repository
```bash
git clone https://github.com/yourusername/daily-quotes-app.git
```

2. Open in Android Studio
3. Sync the project with Gradle files
4. Run the app on device or emulator

### **Permissions**
The app requires the following permissions:
- `POST_NOTIFICATIONS` - For daily quote notifications
- `SCHEDULE_EXACT_ALARM` - For precise notification timing

## 🔧 **Configuration**

### **Database Initialization**
The app comes pre-loaded with 45+ quotes across 9 categories. New quotes are automatically populated on first launch.

### **Notification Scheduling**
- Uses WorkManager for reliable background execution
- Calculates optimal delivery times based on user preferences
- Handles device restarts and app updates

### **Customization**
Easy to extend with:
- New quote categories
- Additional survey questions
- Different notification styles
- Custom themes and colors

## 📊 **Key Components**

### **Data Layer**
- `User` - User profile and preferences
- `Quote` - Quote content and metadata
- `SurveyResponse` - Onboarding survey data
- `QuoteRepository` - Quote management operations
- `UserRepository` - User data operations

### **UI Layer**
- `OnboardingScreen` - Survey and setup flow
- `MainScreen` - Daily quote display and settings
- `QuoteCard` - Reusable quote display component

### **Background Services**
- `DailyQuoteWorker` - Scheduled quote delivery
- `NotificationScheduler` - Notification timing management

## 🎯 **Future Enhancements**

- **Quote Sharing**: Share favorite quotes on social media
- **Streak Tracking**: Monitor daily engagement
- **Mood Tracking**: Correlate quotes with user mood
- **Custom Quotes**: Allow users to add personal quotes
- **Widget Support**: Home screen quote widgets
- **Cloud Sync**: Backup preferences across devices
- **Analytics**: Usage insights and improvement suggestions

## 🤝 **Contributing**

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 **Acknowledgments**

- **Jetpack Compose** team for the amazing UI toolkit
- **Material Design** for the beautiful design system
- **Quote contributors** for inspirational content
- **Android Architecture** team for the guidance

---

**Built with ❤️ using Kotlin and Jetpack Compose**

*Transform your daily routine with personalized inspiration!* ✨
