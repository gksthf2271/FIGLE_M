object Jvm {
    const val target = "17"
    const val inject = "1"
}

object Sdk {
    const val min = 23
    const val target = 34
    const val compile = 34
}

object Kotlin {
    const val compiler = "1.5.31"
    const val compilerExtension = "1.1.0-beta04"
    const val gradle = "1.6.0"
    const val coroutines = "1.6.4"
}

object BuildTools {
    const val gradle = "8.1.3"
}

object Google {
    const val hilt = "2.48"
    const val android_hilt = "1.2.0"
    const val material = "1.6.0"
    const val datastore = "1.0.0"
}

object AndroidX {
    const val core = "1.12.0"
    const val splash = "1.0.0"
    const val fragment_ktx = "1.6.1"
    const val annotation_lib = "1.5.0"
    const val appcompat = "1.4.1"
    const val compose = "1.3.2"
    const val activityCompose = "1.6.1"
    const val hiltNavigationCompose = "1.1.0-alpha01"
    const val lifecycleCompose = "2.5.1"
    const val pagingCompose = "1.0.0-alpha17"
    const val room = "2.6.1"
    const val constraint = "2.2.0"
    const val recyclerview = "1.3.0"
    const val lifecycle = "2.7.0"
    const val navVersion = "2.7.7"
    const val composeConstraintVersion = "1.1.0"

    const val composeBom = "androidx.compose:compose-bom:2024.02.01"
    const val material3 = "androidx.compose.material3:material3"
    object Navigation {
        private const val navVersion = "2.7.7"
        const val navigation = "androidx.navigation:navigation-compose:$navVersion"
    }

    object Lifecycle {
        const val lifecycle = "2.7.0"
        const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${lifecycle}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycle}"
        const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${lifecycle}"
    }

    const val preview = "androidx.compose.ui:ui-tooling-preview"
    const val tooling = "androidx.compose.ui:ui-tooling"
}

object Glide {
    const val glide = "4.14.2"
    const val glide_compiler = "4.12.0"
}

object Network {
    const val retrofit = "2.9.0"
    const val okhttpLogging = "4.9.3"
    const val gson = "2.9.0"
    const val moshi = "1.12.0"
}

object Testing {
    const val junitExt = "1.1.4"
    const val espresso = "3.5.0"
    const val junit = "4.13.2"
}
