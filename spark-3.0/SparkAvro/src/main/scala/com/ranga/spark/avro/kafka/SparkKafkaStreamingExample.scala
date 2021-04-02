package com.ranga.spark.avro.kafka

import org.apache.spark.{SparkConf, TaskContext}
import org.apache.spark.sql.SparkSession
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}

object SparkKafkaStreamingExample extends App {

  if(args.length < 3) {
    println("Usage: SparkKafkaStreamingExample <bootstrap_servers> <topic_name> <group_id>")
    System.exit(0)
  }

  val appName = "Spark Kafka Streaming Example"
  var Array(bootstrapServers, topicName, groupId) = args.take(3)
  val DATA_CHECKPOINT_DIR = "/tmp/streaming_data_checkpoint/"
  val BATCH_INTERVAL = 10

  val conf = new SparkConf().setAppName(appName).setIfMissing("spark.master", "local[*]")

  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> bootstrapServers,
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> groupId,
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  val topics = topicName.split(",")

  def function2CreateStreamingContext()={
    val ssc = new StreamingContext(conf,Seconds(10))
    val messages = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    ssc.checkpoint(DATA_CHECKPOINT_DIR) // set checkpoint directory
    messages.checkpoint(Duration(8*10.toInt*1000))

    messages.foreachRDD(rdd=>{
      if (!rdd.isEmpty()){
        println("------asd------"+rdd.count())
      }
    })
    ssc
  }

  // If the checkpoint data exists, the context is reconstructed according to the checkpoint data,
  // if it does not exist, the context is constructed according to the second parameter
  val ssc =StreamingContext.getOrCreate(DATA_CHECKPOINT_DIR, function2CreateStreamingContext)
  ssc.start()
  ssc.awaitTermination()
}
