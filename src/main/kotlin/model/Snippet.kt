package main.kotlin.model

import com.google.gson.annotations.SerializedName

data class Snippet(@SerializedName("user") val user: String, @SerializedName("text") val text: String = "Default")