package com.example.common.di

import android.content.Context
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

actual fun getSSLContext(context: Any): SSLContext {

    val cont = context as Context

    val certificateFileName = "certificate.crt"

    // Загрузка сертификата из assets
    val certificateFactory = CertificateFactory.getInstance("X.509")
    val inputStream: InputStream = cont.assets.open(certificateFileName)
    val certificate = certificateFactory.generateCertificate(inputStream)
    inputStream.close()

    // Создание keystore и добавление сертификата
    val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
    keyStore.load(null)
    keyStore.setCertificateEntry("myalias", certificate)

    // Создание SSL контекста
    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(keyStore)

    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())

    return sslContext
}