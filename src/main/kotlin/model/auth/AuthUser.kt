package main.kotlin.model.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthUser(@Expose @SerializedName("Jwt-Token") val token: String? = null,
                    @Expose @SerializedName("user") val user: User? = null)