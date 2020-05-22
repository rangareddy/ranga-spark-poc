package com.ranga.spark.rdd.action

import com.ranga.spark.rdd.util.SparkContextUtil

object FoldByKeyDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)

    var names = Array("ranga", "yaswanth", "raja", "nishanth", "reddy", "manoj");
    var namesRDD = context.parallelize(names)
    var namesPairRDD = namesRDD.map(name => (name.length, name))
    val foldByKeyResult = namesPairRDD.foldByKey("")(_+_).collect().toList
    println(s"foldByKeyResult ${foldByKeyResult}")

    context.stop()

}
