package main.kotlin.model.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(@Expose @SerializedName("user_id") val id: Int,
                @Expose @SerializedName("user_name") val userName: String,
                val passwordHash: String? = null)