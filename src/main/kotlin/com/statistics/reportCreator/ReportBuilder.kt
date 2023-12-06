package com.statistics.reportCreator

import com.statistics.databaseDao.RedirectLog
import com.statistics.reportCreator.reportLayers.LayerSelector

class ReportBuilder(val logList: MutableList<RedirectLog>,
                    val layerTypeList: MutableList<ReportLayerType>) {
    var reportEntityList = mutableListOf<ReportEntity>()
    var id = 0
    fun build():MutableList<ReportEntity>{
        reportEntityList = mutableListOf()
        id = 0
        LayerSelector(this).selectLayerClassByType(layerTypeList[0],0).addLayer()
        return reportEntityList
    }
    private fun getReportEntityById(id: Int): ReportEntity{
        for (reportEntity in reportEntityList){
            if (reportEntity.id == id) return reportEntity
        }
        return reportEntityList[id]
    }
    private fun getEntityLayerTypeById(id: Int): ReportLayerType{
        val entity = getReportEntityById(id)
        if (entity.url != null){
            return ReportLayerType.URL
        } else if (entity.sourceIP != null){
            return ReportLayerType.SourceIP
        }
        return ReportLayerType.TimeInterval
    }
    fun checkParentDependency(parentLayerType: ReportLayerType, parentId: Int, log : RedirectLog): Boolean{
        val parentReportEntity = getReportEntityById(parentId)
        if (parentLayerType == ReportLayerType.TimeInterval){
            if (log.redirectTime != parentReportEntity.timeInterval) return false
        }else if (parentLayerType == ReportLayerType.URL){
            if (log.urlInfo != parentReportEntity.url) return false
        }else if (parentLayerType == ReportLayerType.SourceIP){
            if (log.userId != parentReportEntity.sourceIP) return false
        }
        if (parentReportEntity.pid != null){
            return checkParentDependency(getEntityLayerTypeById(parentReportEntity.pid),parentReportEntity.pid,log)
        }
        return true
    }
}