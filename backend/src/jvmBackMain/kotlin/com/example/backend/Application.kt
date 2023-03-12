package com.example.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories("com.example.backend.repository")
@ComponentScan(basePackages = ["com.example.backend"])
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
