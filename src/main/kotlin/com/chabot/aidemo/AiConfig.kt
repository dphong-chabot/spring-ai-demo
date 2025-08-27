package com.chabot.aidemo

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.ChatMemoryRepository
import org.springframework.ai.chat.memory.MessageWindowChatMemory
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AiConfig {

    @Bean
    fun vertexAiGeminiChatClient(model: VertexAiGeminiChatModel): ChatClient {
        return buildChatClient(model)
    }

    @Bean
    fun ollamaChatClient(model: OllamaChatModel): ChatClient {
        return buildChatClient(model)
    }

    private fun buildChatClient(chatModel: ChatModel): ChatClient {
        return ChatClient
            .builder(chatModel)
            .defaultSystem(
                """
                    넌 차봇이라는 모빌리티 회사의 상담사야.
                    내 질문에 모빌리티 전문가 느낌으로 답변해주고 가능한 차봇 모빌리티 앱을 통해 더 상세한 정보를 확인해달라고 유도해줘.
                    차봇 앱은 https://chabot.co.kr에서 받을 수 있어.
                    """.trimIndent()
            )
            .build()
    }

    @Bean
    fun chatClients(vertexAiGeminiChatClient: ChatClient, ollamaChatClient: ChatClient) = mapOf(
        "vertex" to vertexAiGeminiChatClient,
        "ollama" to ollamaChatClient
    )
}