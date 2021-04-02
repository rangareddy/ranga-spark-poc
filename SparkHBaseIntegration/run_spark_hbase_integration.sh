#!/bin/bash

echo "Submitting the SparkHBaseIntegrationApp"

spark-submit --class com.ranga.spark.hbase.SparkHBaseIntegrationApp \
  --master yarn \
  --deploy-mode client \
  --driver-memory 1g \
  --executor-memory 2g \
  --executor-cores 5 \
  /usr/apps/spark/spark-hbase/spark-hase-integration-1.0.0-SNAPSHOT.jar

echo "SparkHBaseIntegrationApp submitted successfully."

