package com.ranga.spark.rdd.transformation.pair

import com.ranga.spark.rdd.util.SparkContextUtil

object MapValuesDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)

    var numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    var numberRDD = context.parallelize(numbers)

    val pairRDD = numberRDD.map(num => (num, num * num))
    val mapValuesResult = pairRDD.mapValues("'"+_+"'").collect().toList
    println(s"mapValuesResult $mapValuesResult")

    context.stop()

}
