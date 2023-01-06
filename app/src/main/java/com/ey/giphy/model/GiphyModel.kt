package com.ey.giphy.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
data class GiphyModel(

    @PrimaryKey(autoGenerate = false)
    @JsonProperty("id")
    var id: String = "",

    @JsonProperty("images")
    @Embedded
    var images: Images? = null,

    ) {

    @JsonIgnore
    var isFavourite = false

    override fun toString(): String {
        return "{\"id\":$id,\"images\":$images,\"isFavourite\":$isFavourite}"
    }
}