package com.ranga.spark.hbase.util

import org.apache.spark.sql.{DataFrame, SparkSession}

import org.apache.spark.sql.execution.datasources.hbase._
import org.apache.hadoop.hbase.spark.datasources.HBaseTableCatalog

object SparkHBaseUtil extends Serializable {

  def saveDataToHBase(df: DataFrame, catalog:String) = {
    df.write.options(Map(HBaseTableCatalog.tableCatalog -> catalog, HBaseTableCatalog.newTable -> "4")).format("org.apache.spark.sql.execution.datasources.hbase").save()
  }

  def withCatalog(catalog: String, spark:SparkSession) : DataFrame = {
    spark
      .read
      .options(Map(HBaseTableCatalog.tableCatalog -> catalog))
      .format("org.apache.spark.sql.execution.datasources.hbase")
      .load()
  }
}
