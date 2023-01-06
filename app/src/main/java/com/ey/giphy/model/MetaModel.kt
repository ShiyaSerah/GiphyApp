package com.ey.giphy.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class MetaModel(
    @JsonProperty("status")
    var status:Int,

    @JsonProperty("msg")
    var msg:String,

    @JsonProperty("response_id")
    var responseId:String
)