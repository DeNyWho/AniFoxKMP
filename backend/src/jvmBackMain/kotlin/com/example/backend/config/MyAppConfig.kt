package com.example.backend.config

//import org.apache.http.HttpHost
//import org.elasticsearch.client.RestClient
//import org.elasticsearch.client.RestHighLevelClient
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories
//
//@Configuration
//@EnableJpaRepositories(basePackages = ["com.example.backend.repository"])
//@EnableElasticsearchRepositories(basePackages = ["com.example.backend.elasticRepository"])
//class MyAppConfig {
//    @Bean
//    fun restHighLevelClient(): RestHighLevelClient {
//        return RestHighLevelClient(
//            RestClient.builder(HttpHost("localhost", 9200, "http"))
//        )
//    }
//}