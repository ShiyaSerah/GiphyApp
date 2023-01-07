package com.ey.giphy.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
data class Images(
    @JsonProperty("fixed_height_downsampled")
    @Embedded
    val imageProperties: ImageProperties? = null
) {
    @PrimaryKey(autoGenerate = true)
    @JsonIgnore
    var imgId: Int = 0
}