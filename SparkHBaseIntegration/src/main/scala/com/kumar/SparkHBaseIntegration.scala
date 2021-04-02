package com.kumar

import com.kumar.entity.Employee
import com.kumar.util.SessionBuilder
import org.apache.spark.sql.{ SparkSession}
import org.apache.spark.sql.execution.datasources.hbase._
import org.apache.hadoop.hbase.spark.datasources.HBaseTableCatalog

object SparkHBaseIntegration extends App with Serializable {

  val spark: SparkSession = SessionBuilder.getSparkSession()

  println("SparkSession created")

  def catalog =
    s"""{
       |"table":{"namespace":"default", "name":"employee"},
       |"rowkey":"key",
       |"columns":{
       |"key":{"cf":"rowkey", "col":"key", "type":"string"},
       |"fName":{"cf":"person", "col":"firstName", "type":"string"},
       |"lName":{"cf":"person", "col":"lastName", "type":"string"},
       |"mName":{"cf":"person", "col":"middleName", "type":"string"},
       |"addressLine":{"cf":"address", "col":"addressLine", "type":"string"},
       |"city":{"cf":"address", "col":"city", "type":"string"},
       |"state":{"cf":"address", "col":"state", "type":"string"},
       |"zipCode":{"cf":"address", "col":"zipCode", "type":"string"}
       |}
       |}""".stripMargin

  val data = Seq(Employee("1", "Abby", "Smith", "K", "3456 main", "Orlando", "FL", "45235"),
    Employee("2", "Amaya", "Williams", "L", "123 Orange", "Newark", "NJ", "27656"),
    Employee("3", "Alchemy", "Davis", "P", "Warners", "Sanjose", "CA", "34789"))

  import spark.implicits._

  val df = spark.sparkContext.parallelize(data).toDF()
  df.printSchema()

  println("Saving the data to HBase")

  df.write.options(
    Map(HBaseTableCatalog.tableCatalog -> catalog, HBaseTableCatalog.newTable -> "4"))
    .format("org.apache.spark.sql.execution.datasources.hbase")
    .save()

  // val df = withCatalog(catalog)

  //val config = HBaseConfiguration.create()
  //config.addResource("/home/hadoop/hbase-1.2.2/conf/hbase-site.xml");
  //config.set("hbase.zookeeper.quorum", "node1,node2,node3");
  //val hbaseContext = new HBaseContext(sc, config, null)

  println("Data saved to Hbase")

  def withCatalog(cat: String, spark:SparkSession) = {
    spark
      .read
      .options(Map(HBaseTableCatalog.tableCatalog->cat))
      .format("org.apache.spark.sql.execution.datasources.hbase")
      .load()
  }
}



