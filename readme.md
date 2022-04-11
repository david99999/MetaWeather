# MetaWeather demo app 

Demo application showcasing a functional reactive approach, focusing on isolating the state management and the component's responsibilities.
<br/>
Inspired by [mobius] and other reactive state management frameworks

# Demo
<img src="https://github.com/david99999/MetaWeather/blob/master/assets/demo.gif" width=400 />

### What's inside this repo

- Coroutines and Flow
- Android Jetpack navigation
- Custom MVI module with basic state management components
- KtLint code style checks and formatting
- Custom Gradle task for installing Git pre-push (running KtLint before a push)
- Hilt dependency injection
- Location searching Screen
- Weather screen
- So much love


### Modules

```sh
:app
```
Contains the Android framework code, only UI logic and D.I. modules


```sh
:domain
```
Contains the business logic (reusable to be used from different UI implementations), also the models used by the domain and upper layers

```sh
:data
```
Contains the data-related elements, also the DTO used by the Remote Data Sources

```sh
:mvi
```
Contains the scaffolding of the MVI proposal for centralizing state management and encourage SOLID implementations

## TODO
Provide local persistence support so we can show cached information to the user while loading remote data 

[mobius]: <https://github.com/spotify/mobius>
