package com.ranga.spark.rdd.transformation

import com.ranga.spark.rdd.util.SparkContextUtil

/* Ranga Reddy created on 21/05/20 */

object CogroupDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)
    val studentMarks1 = List(("ranga", 55), ("ranga", 56), ("raja", 57), ("nishanth", 58),  ("vinod", 54))
    val studentMarks2 = List(("ranga", 80), ("nishanth", 84), ("nishanth", 59), ("vinod", 52))

    val studentMarks1RDD = context.parallelize(studentMarks1)
    val studentMarks2RDD = context.parallelize(studentMarks2)

    val cogroupRDD = studentMarks1RDD.cogroup(studentMarks2RDD)
    val cogroupMarkList = cogroupRDD.collect().toList
    println(s"CoGroup Marks Values $cogroupMarkList")
    context.stop()

}
