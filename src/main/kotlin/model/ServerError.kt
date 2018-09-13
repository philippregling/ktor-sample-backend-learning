package main.kotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ServerError(@Expose @SerializedName("code") val code: Int? = null,
                       @Expose @SerializedName("message") val message: String? = null)