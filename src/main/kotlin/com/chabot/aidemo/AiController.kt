package com.chabot.aidemo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.ChatMemoryRepository
import org.springframework.ai.chat.memory.MessageWindowChatMemory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestHeader

@RestController
class AiController(
    private val chatClients: Map<String, ChatClient>,
    private val chatMemoryRepository: ChatMemoryRepository
) {

    @GetMapping("/ai")
    fun generation(
        @RequestParam input: String,
        @RequestParam(required = false, defaultValue = "vertex") model: String,
        @RequestHeader(value = "Accept-Language", required = false) acceptLanguage: String?
    ): String {
        LOG.info("input = $input")
        
        val finalInput = buildPromptWithAcceptLanguage(input, acceptLanguage)

        val chatMemory = MessageWindowChatMemory.builder()
            .chatMemoryRepository(chatMemoryRepository)
            .build()
        val client = chatClients[model]
        checkNotNull(client)
        return client
            .prompt()
            .advisors {
                it.advisors(
                    MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
            }
            .user(finalInput)
            .call()
            .content().orEmpty()
    }

    private fun buildPromptWithAcceptLanguage(input: String, acceptLanguage: String?): String {
        if (acceptLanguage.isNullOrBlank()) {
            return input
        }
        
        val primaryLanguage = acceptLanguage.split(',')[0].split(';')[0].trim().lowercase()
        
        val languageInstruction = when {
            primaryLanguage.startsWith("ko") -> "한국어로 응답해줘"
            primaryLanguage.startsWith("en") -> "Please respond in English"
            primaryLanguage.startsWith("ja") -> "日本語で応答してください"
            primaryLanguage.startsWith("zh-cn") || primaryLanguage == "zh" -> "请用中文回答"
            primaryLanguage.startsWith("zh-tw") -> "請用繁體中文回答"
            primaryLanguage.startsWith("es") -> "Por favor responde en español"
            primaryLanguage.startsWith("fr") -> "Veuillez répondre en français"
            primaryLanguage.startsWith("de") -> "Bitte antworten Sie auf Deutsch"
            else -> null
        }
        
        return if (languageInstruction != null) {
            "$input\n\n$languageInstruction"
        } else {
            input
        }
    }

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(AiController::class.java)
    }
}