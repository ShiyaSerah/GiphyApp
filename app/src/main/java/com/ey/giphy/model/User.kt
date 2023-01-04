package com.ey.giphy.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
    @JsonProperty("avatar_url")
    private val avatarUrl: String? = null,

    @JsonProperty("banner_image")
    private val bannerImage: String? = null,

    @JsonProperty("banner_url")
    private val bannerUrl: String? = null,

    @JsonProperty("profile_url")
    private val profileUrl: String? = null,

    @JsonProperty("username")
    private val username: String? = null,

    @JsonProperty("display_name")
    private val displayName: String? = null,

    @JsonProperty("description")
    private val description: String? = null,

    @JsonProperty("instagram_url")
    private val instagramUrl: String? = null,

    @JsonProperty("website_url")
    private val websiteUrl: String? = null,

    @JsonProperty("is_verified")
    private val isVerified: Boolean? = null
)