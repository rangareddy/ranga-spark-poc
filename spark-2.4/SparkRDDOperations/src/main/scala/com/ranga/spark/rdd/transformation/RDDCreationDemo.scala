package com.ranga.spark.rdd.transformation

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 21/05/20 */

object RDDCreationDemo extends App {

    def appName = this.getClass.getSimpleName
    val context = SparkContextUtil.getSparkContext(appName)

    // creating RDD by calling parallelize() by passing collection data
    var numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    var numberRDD = context.parallelize(numbers)
    numberRDD.foreach(line => println(line))

    // creating RDD by calling textFile()
    val textFileRDD = context.textFile("./README.md")
    textFileRDD.foreach(line => println(line))

    context.stop()
}
