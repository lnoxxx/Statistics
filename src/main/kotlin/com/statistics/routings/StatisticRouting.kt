package com.statistics.routings

import com.statistics.databaseDao.DimensionsRequest
import com.statistics.databaseDao.DatabaseServerDao
import com.statistics.databaseDao.RedirectLog
import com.statistics.reportCreator.ReportBuilder
import com.statistics.databaseDao.toLayerTypeList
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.statisticRouting() {
    routing {
        post("/") {
            val userId = call.parameters["userId"]
            val urlInfo = call.parameters["urlInfo"]
            val redirectTime = call.parameters["redirectTime"]
            if (userId != null && urlInfo!=null && redirectTime!=null){
                DatabaseServerDao().addToDatabase(RedirectLog(userId,urlInfo,redirectTime))
                call.respond(HttpStatusCode.Accepted)
            }
            else{
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        post("/report"){
            val logList = DatabaseServerDao().getAllStatisticsLog()
            val request = call.receive<DimensionsRequest>().toLayerTypeList()
            call.respond(ReportBuilder(logList, request).build())
        }
    }
}
