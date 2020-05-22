package com.ranga.spark.rdd.transformation

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 21/05/20 */

object FilterByRangeDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)

    var names = Array("ranga", "reddy", "nishanth", "yaswanth", "abc", "manu", "raja")
    var namesRDD = context.parallelize(names)

    val namesMappedRDD = namesRDD.map(name => (name.length, name))
    val filterRangeList = namesMappedRDD.filterByRange(4, 6).collect().toList

    println(s"filterRangeList $filterRangeList")
    context.stop()

}
