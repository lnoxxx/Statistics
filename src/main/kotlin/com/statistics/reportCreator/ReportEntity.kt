package com.statistics.reportCreator

import kotlinx.serialization.Serializable

@Serializable
data class ReportEntity(
    val id: Int,
    val pid: Int? = null,
    val url: String? = null,
    val sourceIP: String? = null,
    val timeInterval: String? = null,
    val count: Int
)