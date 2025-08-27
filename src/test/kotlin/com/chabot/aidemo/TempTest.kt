package com.chabot.aidemo

import org.junit.jupiter.api.Test
import org.springframework.web.client.RestClient

class TempTest {

    @Test
    fun name() {
        val client = RestClient.builder()
            .baseUrl("http://localhost:8080")
            .build()

        val input = "쏘렌토의 상세 제원에 대해 알려줘"
        val response = client.get()
            .uri("/ai?input=$input&model=vertex")
//            .header("Accept-Language", "ko")
            .header("Accept-Language", "en")
            .retrieve()
            .body(String::class.java)

        println("body = ${response}")
    }
}