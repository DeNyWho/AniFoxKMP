package com.example.common.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Unconfined