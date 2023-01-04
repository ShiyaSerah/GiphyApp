package com.ey.giphy.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class ImageProperties {

    @JsonProperty("height")
    var height: String? = null


    @JsonProperty("width")
    var width: String? = null


    @JsonProperty("size")
    var size: String? = null


    @JsonProperty("url")
    var url: String? = null


    @JsonProperty("mp4_size")
    var mp4Size: String? = null


    @JsonProperty("mp4")
    var mp4: String? = null


    @JsonProperty("webp_size")
    var webpSize: String? = null


    @JsonProperty("webp")
    var webp: String? = null


    @JsonProperty("frames")
    var frames: String? = null


    @JsonProperty("hash")
    var hash: String? = null


}