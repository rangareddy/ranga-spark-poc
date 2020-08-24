#SparkWordCount.scala

import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.SparkConf

object SparkWordCount extends Serializable  {
    def main(args: Array[String]) {
        if(args.length < 1) {
            println("Usage   : SparkWordCount <file_path>")
            println("Example : SparkWordCount /usr/applications.log")
            System.exit(0)
        }

        val conf = new SparkConf().setIfMissing("spark.master", "local[*]").setAppName("Spark Word Count Example")
        val sparkSession = SparkSession.builder().config(conf).getOrCreate();

        val threshold = 5
        val filePath = args(0)
        val data: Dataset[String] = sparkSession.read.text(filePath).as[String]

        // read in text file and split each document into words
        val tokenized = data.rdd.flatMap(value => value.split(" "))

        // count the occurrence of each word
        val wordCounts = tokenized.map((_, 1)).reduceByKey(_ + _)

        // filter out words with fewer than threshold occurrences
        val filtered = wordCounts.filter(_._2 >= threshold)

        // count characters
        val charCounts = filtered.flatMap(_._1.toCharArray).map((_, 1)).reduceByKey(_ + _)

        System.out.println(charCounts.collect().mkString(", "))
    }
}