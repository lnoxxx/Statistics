package com.statistics.reportCreator.reportLayers

import com.statistics.reportCreator.ReportBuilder
import com.statistics.reportCreator.ReportEntity

class IpLayer(
    override var depth: Int,
    override val parentId: Int?,
    override val reportBuilder: ReportBuilder
): LayerSelector.ReportLayer {
    override fun counting(data: String): Int {
        var count = 0
        for (log in reportBuilder.logList){
            if (parentId!=null){
                if(!reportBuilder.checkParentDependency(reportBuilder.layerTypeList[depth-1],parentId,log)){
                    continue
                }
            }
            if (log.userId == data) count++
        }
        return count
    }
    override fun addLayer() {
        val uniqueIpList = mutableSetOf<String>()
        for (log in reportBuilder.logList){
            if (parentId!=null){
                if(!reportBuilder.checkParentDependency(reportBuilder.layerTypeList[depth-1],parentId,log)){
                    continue
                }
            }
            uniqueIpList.add(log.userId)
        }
        for(ip in uniqueIpList){
            reportBuilder.id++
            reportBuilder.reportEntityList.add(
                ReportEntity(
                id = reportBuilder.id,
                pid = parentId,
                sourceIP = ip,
                count = counting(ip))
            )
            if (depth != reportBuilder.layerTypeList.size-1){
                addNextLayer()
            }
        }
    }
    override fun addNextLayer() {
        LayerSelector(reportBuilder).selectLayerClassByType(reportBuilder.layerTypeList[depth+1],depth+1,reportBuilder.id).addLayer()
    }
}