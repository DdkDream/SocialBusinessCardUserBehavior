package test

import org.apache.hadoop.hive.conf.HiveConf
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.hive.HiveContext

/**
 * @Author：朱瑞敏
 * @Description： TODO
 * */
object test {
    def main(args: Array[String]): Unit = {
        System.setProperty("HADOOP_USER_NAME", "nuc_zrm")
        val spark: SparkSession = new SparkSession.Builder().master("local[*]")
                .appName("sparkReadHive")
                .enableHiveSupport()
                .getOrCreate()

        import spark.implicits._

        val frame: DataFrame = spark.sql("use bs_ubs")

        val frame1: DataFrame = spark.sql("select * from bs_ubs.stg_company_login_online_df")

        frame1.show(2);




        spark.stop()

    }

}
