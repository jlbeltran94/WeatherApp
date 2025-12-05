package com.jlbeltran94.weatherapp.data.mapper

import com.jlbeltran94.weatherapp.data.local.entity.RecentSearchEntity
import com.jlbeltran94.weatherapp.domain.model.RecentSearch

fun RecentSearchEntity.toRecentSearch(): RecentSearch {
    return RecentSearch(
        id = id,
        cityName = cityName,
        region = region,
        country = country,
        temperature = temperature,
        condition = condition,
        conditionIcon = conditionIcon,
        timestamp = timestamp
    )
}
