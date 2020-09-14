#!/bin/bash

$SPARK_HOME/bin/spark-submit \
  --master yarn-cluster \
  --driver-memory 3G \
  --executor-memory 3G \
  --num-executors 2 \
  --executor-cores 3 \
  --files log4j.properties \
  --class com.ranga.spark.logger.SparkLoggingDemo \
  spark-logger-demo-1.0.0-SNAPSHOT.jar