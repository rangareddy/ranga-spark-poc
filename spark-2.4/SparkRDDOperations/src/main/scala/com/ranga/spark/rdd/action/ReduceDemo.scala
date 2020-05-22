package com.ranga.spark.rdd.action

import com.ranga.spark.rdd.util.SparkContextUtil

object ReduceDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)

    var numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    var numberRDD = context.parallelize(numbers)

    val sum = numberRDD.reduce((x, y) => x + y )

    println(s"Reduce $sum")

    context.stop()

}
