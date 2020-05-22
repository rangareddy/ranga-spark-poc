package com.ranga.spark.rdd.action

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 21/05/20 */

object CollectDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)
    var numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    var numberRDD = context.parallelize(numbers)

    val filterRDD = numberRDD.filter(num => num % 3 == 0)
    var result = filterRDD.collect().toList

    println(s"Collect result $result")
    context.stop()
}
