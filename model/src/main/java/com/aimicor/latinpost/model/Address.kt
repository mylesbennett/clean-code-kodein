package com.aimicor.latinpost.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city") val city: String,
    @SerializedName("geo") val geo: Geo?,
    @SerializedName("street") val street: String,
    @SerializedName("suite") val suite: String,
    @SerializedName("zipcode") val zipcode: String
)