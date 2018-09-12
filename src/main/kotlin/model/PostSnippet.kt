package main.kotlin.model

import com.google.gson.annotations.SerializedName

data class PostSnippet(@SerializedName("snippet") val snippet: Snippet)