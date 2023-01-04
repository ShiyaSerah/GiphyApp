package com.ey.giphy.model

import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class GiphyModel (

    @JsonProperty("type")
    var type: String? = null,


    @JsonProperty("id")
    var id: String? = null,


    @JsonProperty("url")
    var url: String? = null,

    @JsonProperty("slug")
    var slug: String? = null,


    @JsonProperty("bitly_gif_url")
    var bitlyGifUrl: String? = null,

    @JsonProperty("bitly_url")
    var bitlyUrl: String? = null,

    @JsonProperty("embed_url")
    var embedUrl: String? = null,


    @JsonProperty("username")
    var username: String? = null,


    @JsonProperty("source")
    var source: String? = null,


    @JsonProperty("title")
    var title: String? = null,


    @JsonProperty("rating")
    var rating: String? = null,


    @JsonProperty("content_url")
    var contentUrl: String? = null,


    @JsonProperty("source_tld")
    var sourceTld: String? = null,


    @JsonProperty("source_post_url")
    var sourcePostUrl: String? = null,


    @JsonProperty("is_sticker")
    var isSticker: Int? = null,


    @JsonProperty("import_datetime")
    var importDatetime: String? = null,


    @JsonProperty("trending_datetime")
    var trendingDatetime: String? = null,

    @JsonProperty("images")
    var images: Images? = null,


    @JsonProperty("user")
    var user: User? = null,

    @JsonProperty("analytics_response_payload")
    var analyticsResponsePayload: String? = null,


    @JsonProperty("analytics")
    var analytics: Analytics? = null,


)