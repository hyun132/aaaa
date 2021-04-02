package com.example.locationfinder.models

/**
 * McKakaoSearchResponse
 */
data class McKakaoSearchResponseDto(
    val documents: List<McItemDto>,
    val metaDto: McMetaDto
)