package com.example.locationfinder.models

/**
 * McMeta
 */
data class McMetaDto(
    val isEnd: Boolean,
    val pageableCount: Int,
    val sameNameDto: McSameNameDto,
    val totalCount: Int
)