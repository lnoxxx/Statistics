package com.statistics.databaseDao

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class DatabaseServerDao {
    private val databaseServerAddress = "localhost"
    private val databaseServerPort = 6379
    fun getAllStatisticsLog(): MutableList<RedirectLog>{
        val socket = Socket(databaseServerAddress, databaseServerPort)
        val output = PrintWriter(socket.getOutputStream(), true)
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        val logList = mutableListOf<RedirectLog>()
        var id = 1
        while (true){
            val request = "--file StatisticsLog.txt --type hashmap --query HGET $id"
            output.println(request)
            val result = input.readLine()
            val log = result.split("___")
            if (result == "not found") break
            else {
                logList.add(RedirectLog(log[0],log[1],log[2]))
                id++
            }
        }
        socket.close()
        return logList
    }
    fun addToDatabase(redirectLog: RedirectLog){
        val socket = Socket(databaseServerAddress, databaseServerPort)
        val output = PrintWriter(socket.getOutputStream(), true)
        val id = getLastId()
        val request =
            "--file StatisticsLog.txt --type hashmap --query HSET $id " +
                    "${redirectLog.userId}___${redirectLog.urlInfo}___${redirectLog.redirectTime}"
        output.println(request)
        socket.close()
    }
    private fun getLastId(): Int{
        val socket = Socket(databaseServerAddress, databaseServerPort)
        val output = PrintWriter(socket.getOutputStream(), true)
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        var id = 1
        while (true){
            val request = "--file StatisticsLog.txt --type hashmap --query HGET $id"
            output.println(request)
            val result = input.readLine()
            if (result == "not found") break
            else id++
        }
        socket.close()
        return id
    }
}