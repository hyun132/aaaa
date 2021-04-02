package com.example.locationfinder.models

import com.example.locationfinder.db.McItemEntity

/**
 * McSearchResponse
 */
data class McSearchResponseDto(
    val display: Int,
    val items: List<McItemEntity>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)