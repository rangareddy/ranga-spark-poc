package com.ranga.spark.hbase

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import com.ranga.spark.hbase.entity.Employee
import com.ranga.spark.hbase.util.SparkHBaseUtil
import org.apache.hadoop.hbase.spark.datasources.HBaseTableCatalog

object SparkHBaseIntegrationApp extends App with Serializable {

  println("Creating the SparkSession")
  val conf = new SparkConf().setAppName("Spark HBase Integration").setIfMissing("spark.master", "local[4]")
  val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
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

  case class Employee(key: String, fName: String, lName: String,
                      mName:String, addressLine:String, city:String,
                      state:String, zipCode:String)

  val data = Seq(Employee("1", "Ranga", "Reddy", "A", "264", "Bangalore", "India", "560038"),
    Employee("2", "Nishanth", "Reddy", "A", "123", "Bangalore", "India", "560038"))

  import spark.implicits._
  val df = spark.sparkContext.parallelize(data).toDF()
  df.printSchema()

  import org.apache.spark.sql.execution.datasources.hbase._
  df.write.options(Map(HBaseTableCatalog.tableCatalog -> catalog, HBaseTableCatalog.newTable -> "4")).format("org.apache.spark.sql.execution.datasources.hbase").save()

  println("Saving the data to HBase")

  SparkHBaseUtil.saveDataToHBase(df, catalog)

  //val config = HBaseConfiguration.create()
  //config.addResource("/home/hadoop/hbase-1.2.2/conf/hbase-site.xml");
  //config.set("hbase.zookeeper.quorum", "node1,node2,node3");
  //val hbaseContext = new HBaseContext(sc, config, null)

  println("Data saved to Hbase")

  spark.close()
}
