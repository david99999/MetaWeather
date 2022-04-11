package com.demo.dependencies

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Plugin for providing dependencies on gradle files,
 * this helps getting the same dependency versions for all modules
 */
class Dependencies : Plugin<Project> {
    override fun apply(p0: Project) {

    }

    companion object {
        private const val CoilVersion = "1.2.0"
        private const val DaggerVersion = "2.38.1"
        private const val MaterialVersion = "1.5.0"
        private const val FragmentKtxVersion = "1.4.1"
        private const val NavigationVersion = "2.4.2"
        private const val ConstraintLayoutVersion = "2.1.3"
        private const val AppCompatVersion = "1.4.1"
        private const val CoreKtxVersion = "1.7.0"
        private const val SplashScreenVersion = "1.0.0-beta02"
        private const val ViewModelVersion = "2.4.1"
        private const val CoroutinesVersion = "1.5.2"
        private const val BaseJavaxVersion = "1"
        private const val JunitVersion = "4.13.2"
        private const val MockkVersion = "1.12.3"
        private const val RetrofitVersion = "2.9.0"
        private const val SerializationVersion = "1.3.2"
        private const val SerializationConverterVersion = "0.8.0"


        const val AndroidXViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$ViewModelVersion"
        const val CoroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$CoroutinesVersion"
        const val JavaXInject = "javax.inject:javax.inject:$BaseJavaxVersion"
        const val Junit = "junit:junit:$JunitVersion"
        const val Mockk = "io.mockk:mockk:$MockkVersion"
        const val Retrofit = "com.squareup.retrofit2:retrofit:$RetrofitVersion"
        const val KotlinXSerialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:$SerializationVersion"
        const val KotlinXSerializationConverter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$SerializationConverterVersion"


        const val SplashScreen = "androidx.core:core-splashscreen:$SplashScreenVersion"
        const val CoreKtx = "androidx.core:core-ktx:$CoreKtxVersion"
        const val AppCompat = "androidx.appcompat:appcompat:$AppCompatVersion"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:$ConstraintLayoutVersion"
        const val NavigationFragment =
            "androidx.navigation:navigation-fragment-ktx:$NavigationVersion"
        const val NavigationUI = "androidx.navigation:navigation-ui-ktx:$NavigationVersion"
        const val FragmentKtx = "androidx.fragment:fragment-ktx:$FragmentKtxVersion"
        const val Material = "com.google.android.material:material:$MaterialVersion"


        const val CoroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$CoroutinesVersion"

        const val DaggerHilt = "com.google.dagger:hilt-android:$DaggerVersion"
        const val DaggerHiltCompiler = "com.google.dagger:hilt-android-compiler:$DaggerVersion"

        const val Coil = "io.coil-kt:coil:$CoilVersion"
        const val CoilSVG = "io.coil-kt:coil-svg:$CoilVersion"

    }
}