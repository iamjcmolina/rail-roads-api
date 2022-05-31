package com.il.cw.course.controller.response

import com.fasterxml.jackson.annotation.JsonInclude
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import software.amazon.awssdk.enhanced.dynamodb.model.Page
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.nio.charset.Charset
import java.util.*

data class PageResponse<T>(
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    val items: List<T> = emptyList(),
    val pageIndexToken: String?
) {
    companion object {

        private val charset = Charset.forName("UTF-8")

        fun <T> from(awsSdkPage: Page<T>): PageResponse<T> {
            val lastEvaluatedKey = awsSdkPage.lastEvaluatedKey()
            return if(lastEvaluatedKey != null){
                PageResponse(awsSdkPage.items(), getPageToken(lastEvaluatedKey))
            } else {
                PageResponse(awsSdkPage.items(), null)
            }
        }

        fun retrieveLastEvaluatedKey(nextPageIndex : String?) : Map<String, String> {
            var lastEvaluatedKey: Map<String, String> = emptyMap()
            nextPageIndex?.let {
                val jsonElement : JsonElement = Json.parseToJsonElement(
                    Base64.getDecoder()
                        .decode(nextPageIndex.toByteArray(charset))
                        .toString(charset))
                lastEvaluatedKey = Json.decodeFromJsonElement<Map<String, String>>(jsonElement)
            }
            return lastEvaluatedKey
        }

        fun getPageToken(lastEvaluatedKey: Map<String, AttributeValue>): String {
            return Base64.getEncoder()
                .encodeToString(
                    Json.encodeToString(lastEvaluatedKey.mapValues { it.value.s() })
                        .toByteArray(Charset.forName("UTF-8")))
        }
    }
}

