package com.example.locationfinder.models

/**
 * McSameName
 */
data class McSameNameDto(
    val keyword: String,
    val region: List<String>,
    val selectedRegion: String
)