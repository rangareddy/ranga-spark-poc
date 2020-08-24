#!/bin/bash

$SPARK_HOME/bin/spark-submit \
  --master yarn-cluster \
  --driver-memory 3G \
  --executor-memory 3G \
  --num-executors 2 \
  --executor-cores 3 \
  --files /log4j-driver.properties,/log4j-executor.properties \
  --conf spark.driver.extraJavaOptions=-Dlog4j.configuration=log4j-driver.properties \
  --conf spark.executor.extraJavaOptions=-Dlog4j.configuration=log4j-executor.properties \
  --class com.ranga.spark.logger.SparkLoggingDemo \
  spark-logger-demo-1.0.0-SNAPSHOT.jar