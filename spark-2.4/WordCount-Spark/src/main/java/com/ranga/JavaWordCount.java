package com.ranga;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;

public class JavaWordCount {
    public static void main(String[] args) {

        if(args.length < 2) {
            System.err.println("Usage: JavaWordCount <appName> <fileName>");
            System.exit(1);
        }

        String appName = args[0];
        String fileName = args[1];

        SparkSession spark = SparkSession.builder().appName(appName).config("spark.master", "local[*]").getOrCreate();
        JavaRDD<String> lines = spark.read().textFile(fileName).javaRDD();
        JavaRDD<String> map = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
        JavaPairRDD<String, Integer> pairRDD = map.mapToPair(word -> new Tuple2<>(word, 1));
        JavaPairRDD<String, Integer> resultRDD = pairRDD.reduceByKey((i1, i2) -> i1 + i2);
        resultRDD.foreach( result -> System.out.println(result._1 +" "+result._2));
        spark.close();
    }
}
