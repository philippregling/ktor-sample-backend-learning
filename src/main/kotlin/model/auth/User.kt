package main.kotlin.model.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.ktor.auth.Principal

data class User(@Expose @SerializedName("user_id") val id: Int,
                @Expose @SerializedName("user_name") val userName: String,
                val passwordHash: String? = null) : Principal