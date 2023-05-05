package com.nuc.online

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, ConsumerStrategies, HasOffsetRanges, KafkaUtils, LocationStrategies, OffsetRange}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import util.User_login

import java.text.SimpleDateFormat


/**
 * @Author：朱瑞敏
 * @Description： TODO
 * */
object data {
    val nowTime = new SimpleDateFormat("yyyyMMdd")
    def main(args: Array[String]): Unit = {





        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkSQL")
        val sparkSession = SparkSession.builder()
                .enableHiveSupport().config(sparkConf)
                .config("dfs.client.use.datanode.hostname", "true")
                .config("dfs.replication", "2")
                .getOrCreate()

        sparkSession.sqlContext.setConf("hive.exec.dynamic.partition","true")
        sparkSession.sqlContext.setConf("hive.exec.dynamic.partition.mode", "nonstrict")


        val data = Array(
            ("user_id2", "surname1", "name1", "login_time1", "province_name1", "city_name", "county_name", "20230425")
        )

        val df = sparkSession.createDataFrame(data).toDF("user_id", "surname", "name", "login_time", "province_name", "city_name", "county_name", "ds")

        df.createOrReplaceTempView("temp_table")

        val table_name = "bs_ubs.stg_user_login_online_df"

//        sql("use bs_ubs")

        df.write.insertInto(table_name)

        sparkSession.table(table_name).show()

//        sparkSession.stop();

    }


}
