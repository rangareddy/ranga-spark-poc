package com.ranga.spark.avro

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.util.SizeEstimator

object BroadcastJoinExample extends App
{
  val conf = new SparkConf().setIfMissing("spark.master", "local[*]").setAppName("Broadcast Join Example")
  val spark = SparkSession.builder().config(conf).getOrCreate()

  val isFirstTime = false
  val df1Name = "df1"
  val df2Name = "df2"
  val filePath="/tmp/"
  val broadcastName = "broad_"
  val broadCastList = List(false,
     true
  )

  if(isFirstTime) {
    for(isBroadCast <- broadCastList) {
      if(isBroadCast) {
        persistDataToFile(100000000L, broadcastName + df1Name)
        persistDataToFile(2610000, broadcastName + df2Name)
      } else {
        persistDataToFile(100000000L, df1Name)
        persistDataToFile(2625000, df2Name)
        // 2650000 --> 10.1 MiB --> 2,649,999 --> SortMergeJoin     -->
        // 2625000 --> 10.0 MiB --> 2,624,999 --> SortMergeJoin     --> du -h -m /tmp/df2 (11MB) 10508368 --> storage tab 2.6 MiB
        // 2610000 --> 10.0 MiB --> 2,609,999 --> BroadcastHashJoin --> du -h -m /tmp/df2 (11MB) 10448261 --> storage tab 2.5 MiB
        // 2600000 --> 9.9 MiB  --> 2,599,999 --> BroadcastHashJoin -->
      }
    }
  }

  for(isBroadCast <- broadCastList) {
    if(isBroadCast) {
      val df1 = spark.read.load(filePath+broadcastName+df1Name).persist()
      val df2 = spark.read.load(filePath+broadcastName+df2Name).persist()
      joinDatasets(broadcastName,df1, df2)
    } else {
      val df1 = spark.read.load(filePath+df1Name).persist()
      val df2 = spark.read.load(filePath+df2Name).persist()
      joinDatasets("", df1, df2)
    }
  }

  private def joinDatasets(name:String, df1: DataFrame, df2: DataFrame) = {
    val joinedDf = df1.join(df2, df1("id") === df2("id"))
    val count = joinedDf.count()
    println(name+"df1 size " + SizeEstimator.estimate(df1))
    println(name+"df2 size " + SizeEstimator.estimate(df2))
    print(s"Joined ${name}df count ==> ${count}")
  }

  private def persistDataToFile(data:Long, fileName:String) = {
    val df = spark.range(1, data).persist()
    println(fileName + " size " + SizeEstimator.estimate(df))
    val dfCount = df.count()
    print(fileName+"Count ==> " +dfCount)
    df.coalesce(1).write.mode(SaveMode.Overwrite).save(filePath+fileName)
    df.toDF()
  }

  Console.readLine()
  spark.close()
}
