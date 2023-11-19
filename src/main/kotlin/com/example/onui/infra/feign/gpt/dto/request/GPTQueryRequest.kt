package com.example.onui.infra.feign.gpt.dto.request

data class GPTQueryRequest(

    val messages: Array<Message>,
    val model: String = "gpt-3.5-turbo-1106",
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GPTQueryRequest

        if (!messages.contentEquals(other.messages)) return false
        if (model != other.model) return false

        return true
    }

    override fun hashCode(): Int {
        var result = messages.contentHashCode()
        result = 31 * result + model.hashCode()
        return result
    }
}