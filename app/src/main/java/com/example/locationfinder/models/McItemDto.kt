package com.example.locationfinder.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * McItem
 */
data class McItemDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("address_name")
    val addressName: String,

    @SerializedName("category_group_code")
    val categoryGroupCode: String,

    @SerializedName("category_group_name")
    val categoryGroupName: String,

    @SerializedName("category_name")
    val categoryName: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("place_name")
    val placeName: String,

    @SerializedName("place_url")
    val placeUrl: String,

    @SerializedName("road_address_name")
    val roadAddressName: String,

    @SerializedName("x")
    val posX: String,

    @SerializedName("y")
    val posY: String,

    @SerializedName("distance")
    val distance: String
) : Serializable
