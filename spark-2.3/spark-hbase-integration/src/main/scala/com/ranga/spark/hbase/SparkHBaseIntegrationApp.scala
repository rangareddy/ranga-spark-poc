package com.ranga.spark.hbase

import com.ranga.spark.hbase.SparkHBaseIntegrationApp.outputCatalog
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql._
import org.apache.spark.sql.execution.datasources.hbase._
import org.apache.spark.SparkConf

import scala.collection.mutable

object SparkHBaseIntegrationApp extends App {

    def appName = this.getClass.getSimpleName

    val sparkConf = new SparkConf().setAppName(appName).setIfMissing("spark.master", "local[4]")
    val spark = SparkSession.builder.config(sparkConf).getOrCreate()
    val dataSourceFormat = "org.apache.spark.sql.execution.datasources.hbase"
    var isTruncateTableData = false

    def inputCatalog = s"""{
                        |"table":{"namespace":"default", "name":"table1"},
                        |"rowkey":"key",
                        |"columns":{
                        |"key":{"cf":"rowkey", "col":"key", "type":"string"},
                        |"city":{"cf":"addr", "col":"city", "type":"string"},
                        |"state":{"cf":"addr", "col":"state", "type":"string"},
                        |"numb":{"cf":"order", "col":"numb", "type":"string"}
                        |}
                        |}""".stripMargin

    def outputCatalog = s"""{
                        |"table":{"namespace":"default", "name":"table2"},
                        |"rowkey":"key",
                        |"columns":{
                        |"key":{"cf":"rowkey", "col":"key", "type":"string"},
                        |"city":{"cf":"addr", "col":"city", "type":"string"},
                        |"state":{"cf":"addr", "col":"state", "type":"string"},
                        |"numb":{"cf":"order", "col":"numb", "type":"string"}
                        |}
                        |}""".stripMargin

    // Reading the HBase data
    val inputTableDF = spark.read.options(Map(HBaseTableCatalog.tableCatalog->inputCatalog)).format(dataSourceFormat).load().cache()
    printSchemaInfo(inputTableDF)

    // creating temporary table and adding the filter
    inputTableDF.createOrReplaceTempView("table1_data")
    val outputTableDF = spark.sql("select * from table1_data where state='TX'")
    printSchemaInfo(outputTableDF)

    // Writing the data to HBase
    outputTableDF.write.options(Map(HBaseTableCatalog.tableCatalog-> outputCatalog, HBaseTableCatalog.newTable-> "4")).format(dataSourceFormat).save()  // regions value must be greater than 3 for new table

    // Used to print the DataFrame information
    def printSchemaInfo(df: DataFrame) : Unit = {
        df.printSchema()
        df.show(false)
        println(s"Total records ${df.count()}")
    }
}