package com.statistics.reportCreator.reportLayers

import com.statistics.reportCreator.ReportBuilder
import com.statistics.reportCreator.ReportLayerType

class LayerSelector(private val reportBuilder: ReportBuilder) {
    interface ReportLayer{
        var depth:Int
        val parentId: Int?
        val reportBuilder: ReportBuilder
        fun counting(data: String): Int
        fun addLayer()
        fun addNextLayer()
    }
    fun selectLayerClassByType(reportLayerType: ReportLayerType, depth: Int, parentId: Int? = null): ReportLayer {
        return when (reportLayerType) {
            ReportLayerType.SourceIP -> IpLayer(depth, parentId, reportBuilder)
            ReportLayerType.TimeInterval -> TimeLayer(depth, parentId, reportBuilder)
            ReportLayerType.URL -> UrlLayer(depth, parentId, reportBuilder)
        }
    }
}