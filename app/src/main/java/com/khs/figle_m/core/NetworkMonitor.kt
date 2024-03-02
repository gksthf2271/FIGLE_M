package com.khs.figle_m.core

import kotlinx.coroutines.flow.Flow
interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}
