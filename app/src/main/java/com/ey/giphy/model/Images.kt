package com.ey.giphy.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Images (
    @JsonProperty("original")
    private val original: ImageProperties? = null,

    @JsonProperty("downsized")
    private val downsized: ImageProperties? = null,

    @JsonProperty("downsized_large")
    private val downsizedLarge: ImageProperties? = null,

    @JsonProperty("downsized_medium")
    private val downsizedMedium: ImageProperties? = null,

    @JsonProperty("downsized_small")
    private val downsizedSmall: ImageProperties? = null,

    @JsonProperty("downsized_still")
    private val downsizedStill: ImageProperties? = null,

    @JsonProperty("fixed_height")
    private val fixedHeight: ImageProperties? = null,

    @JsonProperty("fixed_height_downsampled")
    private val fixedHeightDownsampled: ImageProperties? = null,

    @JsonProperty("fixed_height_small")
    private val fixedHeightSmall: ImageProperties? = null,

    @JsonProperty("fixed_height_small_still")
    private val fixedHeightSmallStill: ImageProperties? = null,

    @JsonProperty("fixed_height_still")
    private val fixedHeightStill: ImageProperties? = null,

    @JsonProperty("fixed_width")
    private val fixedWidth: ImageProperties? = null,

    @JsonProperty("fixed_width_downsampled")
    private val fixedWidthDownsampled: ImageProperties? = null,

    @JsonProperty("fixed_width_small")
    private val fixedWidthSmall: ImageProperties? = null,

    @JsonProperty("fixed_width_small_still")
    private val fixedWidthSmallStill: ImageProperties? = null,

    @JsonProperty("fixed_width_still")
    private val fixedWidthStill: ImageProperties? = null,

    @JsonProperty("looping")
    private val looping: ImageProperties? = null,

    @JsonProperty("original_still")
    private val originalStill: ImageProperties? = null,

    @JsonProperty("original_mp4")
    private val originalMp4: ImageProperties? = null,

    @JsonProperty("preview")
    private val preview: ImageProperties? = null,

    @JsonProperty("preview_gif")
    private val previewGif: ImageProperties? = null,

    @JsonProperty("preview_webp")
    private val previewWebp: ImageProperties? = null,

    @JsonProperty("480w_still")
    private val _480wStill: ImageProperties? = null
)