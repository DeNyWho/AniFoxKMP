package com.example.backend.util

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

private var context: ApplicationContext? = null

fun <T> resolve(clazz: Class<T>): T {
    val bean = context?.getBean(clazz)
    if (bean != null) {
        return bean
    }
    throw IllegalStateException("Spring utility class not initialized.")
}

@Component
class Resolver : ApplicationContextAware {

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

}
