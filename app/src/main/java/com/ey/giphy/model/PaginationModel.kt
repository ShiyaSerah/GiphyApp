package com.ey.giphy.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class PaginationModel(
    @JsonProperty("total_count")
    var totalCount:Int,

    @JsonProperty("count")
    var count:Int,

    @JsonProperty("offset")
    var offset:Int
)