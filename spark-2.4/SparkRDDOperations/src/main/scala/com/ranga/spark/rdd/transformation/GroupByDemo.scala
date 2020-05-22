package com.ranga.spark.rdd.transformation

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 21/05/20 */

object GroupByDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)

    var numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    var numberRDD = context.parallelize(numbers)

    val groupByRDD = numberRDD.groupBy(num => num %2 == 0)

    val groupByResult = groupByRDD.collect().toList
    println(s"groupByResult $groupByResult")

    context.stop()

}
