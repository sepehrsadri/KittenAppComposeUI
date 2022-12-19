# KittenAppComposeUI

The `KittenAppComposeUI` consuming
a [Kitten Api](https://docs.thecatapi.com/api-reference/images/images-search) to display kittens. it
has been built with `Modular` and `Clean Architecture` principles, Repository Pattern, and MVVM
pattern as well as Architecture Components also
utilized with `Android Jetpack` contains: Compose, Hilt, Navigation and ...

This app shows the usage of the new Android Jetpack and Architecture Components.

https://user-images.githubusercontent.com/41581915/208531865-20481c19-9534-4759-ae59-e0cf5cde0ea5.mp4

**App features:**

- List of Kittens Category
- Detail of each Kittens Category
- Light / Dark theme switch

## Architecture

Uses concepts of the notorious Uncle Bob's architecture
called [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
.

* Better separation of concerns. Each module has a clear API., Feature related classes life in
  different modules and can't be referenced without explicit module dependency.
* Features can be developed in parallel eg. by different teams
* Each feature can be developed in isolation, independently from other features
* faster compile time

## Modules:

<img width="609" alt="Modules Dependency" src="https://user-images.githubusercontent.com/41581915/208340968-e7cb862b-01a5-4460-a5bc-dfd080dcb74b.png">


* **app** - It uses all the components and classes related to Android Framework. It gets the data
  from other modules and shows on UI. (**access all the modules**)
* **features-category** - this feature module contains kittens category and shows different category
  of kittens use [API]("https://api.thecatapi.com/v1/categories")
* **features-kitten** - - this feature module shows kittens of different category
  use [API]("https://api.thecatapi.com/v1/images/search?limit=10&category_ids=5")
* **shared** - contains business logic of application and implemented in clean architecture (almost
  shared between other modules)
* **test-utils** - contains test utils for other modules test cases (other modules use this module
  as `testImplementation`)

## Tests

This project has been covered with
some [Local Unit Test](https://developer.android.com/training/testing/unit-testing/local-unit-tests)
to passing view model, repository and useCases.

## Tech stack - Library:

- [Kotlin](https://kotlinlang.org/) - Kotlin is a cross-platform, statically typed, general-purpose
  programming language with type inference. Kotlin is designed to interoperate fully with Java, and
  the JVM version of Kotlin's standard library depends on the Java Class Library, but type inference
  allows its syntax to be more concise.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - A coroutine is a concurrency design
  pattern that you can use on Android to simplify code that executes asynchronously
- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - for
  dependency injection.
- JetPack
    - [Compose]("https://developer.android.com/jetpack/compose") - Jetpack Compose is Android’s
      recommended modern toolkit for building native UI. It simplifies and accelerates UI
      development on Android. Quickly bring your app to life with less code, powerful tools, and
      intuitive Kotlin APIs.
    - [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Used get
      lifecyle event of an activity or fragment and performs some action in response to change
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores
      UI-related data that isn't destroyed on UI changes.
    - [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Used
      to navigate between fragments
- [Material-Components](https://github.com/material-components/material-components-android) -
  Material design components.
- [Retrofit](https://github.com/square/retrofit) - Used for REST api communication.
- [OkHttp](http://square.github.io/okhttp/) - HTTP client that's efficient by default: HTTP/2
  support allows all requests to the same host to share a socket
- [Gson](https://github.com/google/gson) - Used to convert Java Objects into their JSON
  representation and vice versa.
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin
  Coroutines.
- [Timber]("https://github.com/JakeWharton/timber") - This is a logger with a small, extensible API
  which provides utility on top of Android's normal Log class.

## Future Road Map

- Unit test cases are note complete and more test cases for critical components needs to be added
  such as mappers and viewModelStateTest cases.
- UI test for screens
- Offline first implementation
- improve performance issue on loading images
- use lottie animation for better UI/UX
- more features to be implemented


