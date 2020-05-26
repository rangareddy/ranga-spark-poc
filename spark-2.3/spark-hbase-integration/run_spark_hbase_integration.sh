#!/bin/bash

echo "Submitting the SparkHBaseIntegrationApp"

spark-submit --class com.ranga.spark.hbase.SparkHBaseIntegrationApp \
  --master yarn \
  --deploy-mode cluster \
  --executor-memory 512MB \
  --driver-memory 1g \
  --executor-memory 2g \
  --executor-cores 1 \
  /usr/apps/spark/spark-hbase/spark-hbase-integration-1.0.0-SNAPSHOT.jar

echo "SparkHBaseIntegrationApp submitted successfully."

