package com.statistics.databaseDao

import com.statistics.reportCreator.ReportLayerType
import kotlinx.serialization.Serializable

@Serializable
data class DimensionsRequest(
    val Dimensions: List<String>
)

fun DimensionsRequest.toLayerTypeList(): MutableList<ReportLayerType>{
    val layerTypeList: MutableList<ReportLayerType> = mutableListOf()
    for(layer in Dimensions){
        if (layer == "URL"){
            layerTypeList.add(ReportLayerType.URL)
        }
        if (layer == "SourceIp"){
            layerTypeList.add(ReportLayerType.SourceIP)
        }
        if (layer == "TimeInterval"){
            layerTypeList.add(ReportLayerType.TimeInterval)
        }
    }
    return layerTypeList
}