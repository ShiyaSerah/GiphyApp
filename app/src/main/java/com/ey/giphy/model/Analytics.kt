package com.ey.giphy.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.ey.giphy.model.AnalyticsAction

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Analytics (
    @JsonProperty("onload")
    private val onload: AnalyticsAction? = null,

    @JsonProperty("onclick")
    private val onclick: AnalyticsAction? = null,

    @JsonProperty("onsent")
    private val onsent: AnalyticsAction? = null
)