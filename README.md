# Conduit

## About
Android/Kotlin codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the RealWorld spec and API.
- Article/Story/Blog writing application like medium:page_with_curl:.
- Users can follow other users to see their articles in their feed:boy:.
- Users can also comment on the articles:pencil2:.
- Clean and Simple Material UI:sparkles::sparkles:.
- It supports dark theme too 🌗.

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [kotlin coroutines](https://github.com/Kotlin/kotlinx.coroutines) - that enable you to write clean, simplified asynchronous code that keeps your app responsive while managing long-running tasks such as network calls or disk operations.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [Jetpack Navigation Architecture](https://developer.android.com/guide/navigation) - Android Jetpack's Navigation component helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer. The Navigation component also ensures a consistent and predictable user experience by adhering to an established set of principles.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Moshi](https://github.com/square/moshi) - Moshi is a modern JSON library for Android and Java. It makes it easy to parse JSON into Java objects:
- [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) - A Converter which uses Moshi for serialization to and from JSON.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.

### Architecture
The project follows the general MVVM structure without any specifics. 

There are two _modules_ in the project 

* `app` - The UI of the app. The main project that forms the APK
* `api` - The REST API consumption library. Pure JVM library not Android-specific


## ScreenShots
<img src="https://user-images.githubusercontent.com/53184162/125424572-d30799ed-7fc6-4d36-8d59-9bf244f632c0.png">
