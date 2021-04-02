package com.example.locationfinder.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.locationfinder.models.McItemDto
import java.io.Serializable

/**
 * McItemEntity
 */
@Entity(tableName = "item_db")
data class McItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "address_name")
    val addressName: String,

    @ColumnInfo(name = "category_group_code")
    val categoryGroupCode: String,

    @ColumnInfo(name = "category_group_name")
    val categoryGroupName: String,

    @ColumnInfo(name = "category_name")
    val categoryName: String,

    val phone: String,

    @ColumnInfo(name = "place_name")
    val placeName: String,

    @ColumnInfo(name = "place_url")
    val placeUrl: String,

    @ColumnInfo(name = "road_address_name")
    val roadAddressName: String,

    @ColumnInfo(name = "pos_x")
    val posX: String,

    @ColumnInfo(name = "pos_y")
    val posY: String,

    var favorite: Boolean = false
) : Serializable{
    companion object{
        fun McItemDto.mapToEntity(): McItemEntity = McItemEntity(
            id,
            addressName,
            categoryGroupCode,
            categoryGroupName,
            categoryName,
            phone,
            placeName,
            placeUrl,
            roadAddressName,
            posX,
            posY,
            favorite = false
        )
    }
}