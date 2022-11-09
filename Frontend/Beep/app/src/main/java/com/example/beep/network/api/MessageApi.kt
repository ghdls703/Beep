package com.example.beep.network.api

import com.example.beep.data.dto.message.MessageRequest
import com.example.beep.data.dto.message.MessageResponse
import retrofit2.http.*

interface MessageApi {
    @GET("message/receive/{type}")
    suspend fun getReceiveMessage(@Path("type") type : Int) : List<MessageResponse>
    @GET("message/send")
    suspend fun getSendMessage() : List<MessageResponse>
    @PATCH("message/tag")
    suspend fun changeTag(@Body messageRequest: MessageRequest) : String
    @DELETE("message/{messageId}")
    suspend fun deleteMessage(@Path("messageId") messageId: Long) : String
    @POST("message/block/{messageId}")
    suspend fun blockMessage(@Path("messageId") messageId: Long) : String

}