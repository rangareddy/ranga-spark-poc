package com.ranga.spark.rdd.action

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 21/05/20 */

object CountByValueDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)
    var numbers = Array(1, 2, 3, 2, 8,7, 4, 5, 6, 7, 8, 9, 10)

    var numberRDD = context.parallelize(numbers)
    val countByValueResult = numberRDD.countByValue().toList

    println(s"countByValueResult $countByValueResult")
    context.stop()
}
