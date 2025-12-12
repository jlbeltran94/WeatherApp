package com.jlbeltran94.searchlocationcomponent.data.mapper

import com.jlbeltran94.searchlocationcomponent.data.local.entity.RecentSearchEntity
import com.jlbeltran94.searchlocationcomponent.data.remote.dto.SearchResponseDto
import com.jlbeltran94.searchlocationcomponent.domain.model.City
import com.jlbeltran94.searchlocationcomponent.domain.model.RecentSearch

fun SearchResponseDto.toCity(): City = City(
    id = id.toString(),
    name = name,
    region = region,
    country = country,
    lat = lat,
    lon = lon
)
//fun Weather.toCity(): City {
//    return City(
//        id = "$cityName,$country",
//        name = cityName,
//        region = region,
//        country = country,
//        lat = cityLat,
//        lon = cityLon
//    )
//}

fun RecentSearchEntity.toRecentSearch(): RecentSearch = RecentSearch(
    id = id,
    cityName = cityName,
    region = region,
    country = country,
    temperature = temperature,
    condition = condition,
    conditionIcon = conditionIcon,
    timestamp = timestamp
)

fun RecentSearch.toRecentSearchEntity(): RecentSearchEntity = RecentSearchEntity(
    id = id,
    cityName = cityName,
    region = region,
    country = country,
    temperature = temperature,
    condition = condition,
    conditionIcon = conditionIcon,
    timestamp = System.currentTimeMillis()
)

