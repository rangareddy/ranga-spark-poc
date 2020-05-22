package com.ranga.spark.rdd.action

import com.ranga.spark.rdd.util.SparkContextUtil

object KeysAndValuesDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)
    val studentMarks = Seq(("ranga", 55), ("ranga", 56), ("raja", 57), ("nishanth", 58), ("nishanth", 59), ("vinod", 54), ("ranga", 80), ("nishanth", 84), ("vinod", 52))
    val studentMarksRDD = context.parallelize(studentMarks)

    val keysResult = studentMarksRDD.keys.collect()
    val valuesResult = studentMarksRDD.values.collect()

    println("keysResult "+keysResult.toList)
    println("valuesResult "+valuesResult.toList)

    context.stop()
}
