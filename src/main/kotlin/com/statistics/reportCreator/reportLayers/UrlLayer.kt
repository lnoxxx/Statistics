package com.statistics.reportCreator.reportLayers

import com.statistics.reportCreator.ReportBuilder
import com.statistics.reportCreator.ReportEntity

class UrlLayer(
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
            if (log.urlInfo == data) count++
        }
        return count
    }
    override fun addLayer() {
        val uniqueUrlList = mutableSetOf<String>()
        for (log in reportBuilder.logList){
            if (parentId!=null){
                if(!reportBuilder.checkParentDependency(reportBuilder.layerTypeList[depth-1],parentId,log)){
                    continue
                }
            }
            uniqueUrlList.add(log.urlInfo)
        }
        for(url in uniqueUrlList){
            reportBuilder.id++
            reportBuilder.reportEntityList.add(
                ReportEntity(
                id = reportBuilder.id,
                pid = parentId,
                url = url,
                count = counting(url))
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