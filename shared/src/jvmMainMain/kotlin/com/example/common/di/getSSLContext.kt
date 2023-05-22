package com.example.common.di

import javax.net.ssl.SSLContext

actual fun getSSLContext(context: Any): SSLContext = SSLContext.getDefault()