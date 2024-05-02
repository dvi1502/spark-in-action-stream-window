package ru.dmvivakiin.dmp.stream.window

import org.apache.spark.sql.SparkSession
import ru.dmvivakiin.dmp.utils.{Platform, SparkSessionBuilder}

object Main {

  println(System.getProperty("java.version"))
  implicit val spark: SparkSession = SparkSessionBuilder.getSession(Platform.LOCAL2)
  SparkSessionBuilder.getConfig(spark).foreach(p => println(p))

  def main(args: Array[String]): Unit = {
    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9093")
      .option("subscribe", "test")
      .option("startingOffsets", "earliest")
      //      .option("startingOffsets", "latest")
      .load()

    val query = df.selectExpr("CAST(key AS STRING)", "CAST(timestamp AS STRING)", "CAST(partition AS STRING)", "CAST(offset AS STRING)", "CAST(value AS STRING)")
      .writeStream
      .format("console")
      .option("numRows", 50)
      .option("truncate", false)
      .outputMode("append")
      //      .option("checkpointLocation", "path/to/HDFS/dir")
      .start()

    query.awaitTermination()
  }
}