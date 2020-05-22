package com.ranga.spark.rdd.transformation.pair

import com.ranga.spark.rdd.util.SparkContextUtil

object ReduceByKeyDemo extends App {

    def appName = this.getClass.getSimpleName

    val context = SparkContextUtil.getSparkContext(appName)
    val studentMarks = Seq(("ranga", 55), ("ranga", 56), ("raja", 57), ("nishanth", 58), ("nishanth", 59), ("vinod", 54), ("ranga", 80), ("nishanth", 84), ("vinod", 52))
    val studentMarksRDD = context.parallelize(studentMarks)

    val studentsMarksReduceByKeyRDD = studentMarksRDD.reduceByKey( (marks1, marks2) => marks1 + marks2)
    val reducedStudentMarksList = studentsMarksReduceByKeyRDD.sortByKey().collect().toList

    println("Reduced Student marks "+reducedStudentMarksList)
    context.stop()
}
