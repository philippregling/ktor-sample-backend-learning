package main.kotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Message(@Expose @SerializedName("message") val message: String? = null)