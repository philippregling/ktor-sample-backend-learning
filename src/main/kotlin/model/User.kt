package main.kotlin.model.auth

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("user_id") val id: Int,
                @SerializedName("user_name") val userName: String)