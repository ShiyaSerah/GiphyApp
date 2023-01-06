package com.ey.giphy.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class GiphyBaseModel(
    @JsonProperty("data")
    var data: ArrayList<GiphyModel>,

    @JsonProperty("pagination")
    var pagination: PaginationModel,

    @JsonProperty("meta")
    var meta: MetaModel
)
