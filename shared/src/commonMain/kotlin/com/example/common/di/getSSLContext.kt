package com.example.common.di

import javax.net.ssl.SSLContext

expect fun getSSLContext(context: Any): SSLContext