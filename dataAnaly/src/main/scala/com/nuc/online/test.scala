package com.nuc.online

import org.apache.hadoop.hive.common.jsonexplain.tez.Connection
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.hive.jdbc.HiveDriver
import org.apache.spark.rdd.RDD

import java.sql.DriverManager
import java.text.SimpleDateFormat
import java.util.Properties

/**
 * @Author：朱瑞敏
 * @Description： TODO
 * */
object test {
    val nowTime = new SimpleDateFormat("yyyyMMdd")

    def main(args: Array[String]): Unit = {
        /**
         * 1、创建StreamingContext
         */

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("DataFormatOnline")
        val sparkSession = SparkSession.builder()
                .enableHiveSupport().config(sparkConf)
                .config("dfs.client.use.datanode.hostname", "true")
                .config("dfs.replication", "2")
                .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                .getOrCreate()

        sparkSession.sqlContext.setConf("hive.exec.dynamic.partition", "true")
        sparkSession.sqlContext.setConf("hive.exec.dynamic.partition.mode", "nonstrict")

        val ssc = new StreamingContext(sparkSession.sparkContext, Seconds(10))


        /**
         * 2、读取Kafka的UserBehaviorAnalysis主题的数据源数据
         */
        val kafkaParams = Map[String, Object](
            "bootstrap.servers" -> "hadoop101:9092,hadoop102:9092,hadoop103:9092", // kafka集群地址
            "key.deserializer" -> classOf[StringDeserializer],
            "value.deserializer" -> classOf[StringDeserializer],
            "group.id" -> "uba", // 消费者组名
            "auto.offset.reset" -> "latest", // latest自动重置偏移量为最新的偏移量
            "enable.auto.commit" -> (false: java.lang.Boolean) // 如果是true，则这个消费者的偏移量会在后台自动提交
        )

        val topic = Array("uba")

        val ds: DStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
            ssc,
            PreferConsistent,
            Subscribe[String, String](topic, kafkaParams)
        )

        ds.print()

        val valueArray: DStream[(String, Array[String])] = ds.map((record: ConsumerRecord[String, String]) => {
            val line = record.value()
            val valueArray = line.split("-")
            val dataType = valueArray(0) // Registers\Login\OutLogin
            (dataType, valueArray)
        })

        // 3. 操作转换数据类型，找出需要的数据

        // 找出内容为操作成功的日志
        val successDStream: DStream[(String, Array[String])] = valueArray.filter(record => {
            val line: Array[String] = record._2
            line.apply(2).equals("Success")
        })


        /**
         * 注册模块
         */

        // 找出内容为注册的日志
        val registerDStream: DStream[(String, Array[String])] = successDStream.filter(record => {
            val line: Array[String] = record._2
            line.apply(0).equals("Register")
        })

        // 找出内容为用户注册的日志
        val userRegisterDStream: DStream[(String, Array[String])] = registerDStream.filter(record => {
            val line: Array[String] = record._2
            line.apply(1).equals("User")
        })
        val userRegisterValuesDStream: DStream[(String, String, String, String, String, String, String, String, String, String, String, String, String, String)] = userRegisterDStream.map(record => {
            val values: Array[String] = record._2
            var user_id: String = values.apply(3)
            if (user_id.isEmpty) {
                user_id = "-"
            }
            var surname: String = values.apply(4)
            if (surname.isEmpty) {
                surname = "-"
            }
            var name: String = values.apply(5)
            if (name.isEmpty) {
                name = "-"
            }
            var sex: String = values.apply(6)
            if (sex.isEmpty) {
                sex = "-"
            }
            var age: String = values.apply(7)
            if (age.isEmpty) {
                age = "-"
            }
            var province_name: String = values.apply(8)
            if (province_name.isEmpty) {
                province_name = "-"
            }
            var city_name: String = values.apply(9)
            if (city_name.isEmpty) {
                city_name = "-"
            }
            var county_name: String = values.apply(10)
            if (county_name.isEmpty) {
                county_name = "-"
            }
            var create_time: String = values.apply(11)
            if (create_time.isEmpty) {
                create_time = "-"
            }
            var phone: String = values.apply(12)
            if (phone.isEmpty) {
                phone = "-"
            }
            var company: String = values.apply(13)
            if (company.isEmpty) {
                company = "-"
            }
            var position: String = values.apply(14)
            if (position.isEmpty) {
                position = "-"
            }
            var introduction: String = values.apply(15)
            if (introduction.isEmpty) {
                introduction = "-"
            }
            //(user_id, surname, name, sex, age, province_name, city_name, county_name, create_time, phone, company, position, introduction, "20230504")
            //            val data = Array(
            //                (user_id, surname, name, sex, age, province_name, city_name, county_name, create_time, phone, company, position, introduction, "20230504")
            //            )

            //            val hiveTable = "bs_ubs.stg_user_register_online_df"
            //            val jdbcUrl = "jdbc:hive2://121.199.17.178:10000"
            //            val connection = DriverManager.getConnection(jdbcUrl, "root", "Zrm@hadoop")
            //            val sqlQuery = "INSERT INTO " + hiveTable + " partition(" + "ds=" + nowTime + ")" + " VALUES" + "(" + user_id +", " +
            //                    surname + ", " + name + ", " + sex + ", " + age + ", " + province_name + ", " + city_name + ", " + county_name + ", " +
            //                    create_time + ", " + phone + ", " + company + ", " + position + ", " + introduction + ")"
            //            val insertIntoTable: DataFrame = sparkSession.sql(sqlQuery)
            //            insertIntoTable.registerTempTable(hiveTable)
            (user_id, surname, name, sex, age, province_name, city_name, county_name, create_time, phone, company, position, introduction, "20230504")
        })
        userRegisterValuesDStream.foreachRDD((rdd:RDD[(String, String, String, String, String, String, String, String, String, String, String, String, String, String)]) => {
            val sparkConf1: SparkConf = new SparkConf().setAppName("save")
            val ss: SparkSession = SparkSession.builder().config(sparkConf1).getOrCreate()

            import ss.implicits._
            val dataFrame: DataFrame = rdd.toDF("user_id", "surname", "name", "sex", "age", "province_name", "city_name", "county_name", "create_time", "phone", "company", "position", "introduction", "ds")
            dataFrame.createOrReplaceTempView("temp_stg_user_register_online_df")
            val table_name = "bs_ubs.stg_user_register_online_df"
            dataFrame.write.insertInto(table_name)
            sparkSession.table(table_name).show()
//            val properties = new Properties()
//            properties.setProperty("user", "root")
//            properties.setProperty("password", "Zrm@hadoop")
//            dataFrame.write.mode(SaveMode.Append).jdbc("jdbc:hive2://121.199.17.178:10000", "bs_ubs.stg_user_register_online_df", properties)
//            val values: Array[(String, String, String, String, String, String, String, String, String, String, String, String, String, String)] = rdd.take(0)
//            val value: (String, String, String, String, String, String, String, String, String, String, String, String, String, String) = values.apply(0)
//            val user_id: String = value._1
//            val surname: String = value._2
//            val name: String = value._3
//            val sex: String = value._4
//            val age: String = value._5
//            val province_name: String = value._6
//            val city_name: String = value._7
//            val county_name: String = value._8
//            val create_time: String = value._9
//            val phone: String = value._10
//            val company: String = value._11
//            val position: String = value._12
//            val introduction: String = value._13
//            val hiveTable = "bs_ubs.stg_user_register_online_df"
//            val jdbcUrl = "jdbc:hive2://121.199.17.178:10000"
//            val connection = DriverManager.getConnection(jdbcUrl, "root", "Zrm@hadoop")
//            val sqlQuery = "INSERT INTO " + hiveTable + " partition(" + "ds=" + nowTime + ")" + " VALUES" + "(" + user_id +", " +
//                    surname + ", " + name + ", " + sex + ", " + age + ", " + province_name + ", " + city_name + ", " + county_name + ", " +
//                    create_time + ", " + phone + ", " + company + ", " + position + ", " + introduction + ")"
//            val insertIntoTable: DataFrame = sparkSession.sql(sqlQuery)
//            insertIntoTable.registerTempTable(hiveTable)
        })



        // 找出内容为企业注册的日志
        val companyRegisterDStream: DStream[(String, Array[String])] = registerDStream.filter(record => {
            val line: Array[String] = record._2
            line.apply(1).equals("Company")
        })


        /**
         * 登陆模块
         */

        // 找出内容为登陆的日志
        val loginDStream: DStream[(String, Array[String])] = successDStream.filter(record => {
            val line: Array[String] = record._2
            line.apply(0).equals("Login")
        })

        // 找出内容为用户登陆的日志
        val userLoginDStream: DStream[(String, Array[String])] = loginDStream.filter(record => {
            val line: Array[String] = record._2
            line.apply(1).equals("User")
        })

        // 找出内容为企业登陆的日志
        val compantLoginDStream: DStream[(String, Array[String])] = loginDStream.filter(record => {
            val line: Array[String] = record._2
            line.apply(1).equals("Company")
        })


        /**
         * 登出模块
         */

        // 找出内容为登出的日志
        val outLoginDStream: DStream[(String, Array[String])] = successDStream.filter(record => {
            val line: Array[String] = record._2
            line.apply(0).equals("OutLogin")
        })

        // 找出内容为用户登出的日志
        val userOutLoginDStream: DStream[(String, Array[String])] = outLoginDStream.filter(record => {
            val line: Array[String] = record._2
            line.apply(1).equals("User")
        })

        // 找出内容为企业登出的日志
        val companyOutLoginDStream: DStream[(String, Array[String])] = outLoginDStream.filter(record => {
            val line: Array[String] = record._2
            line.apply(1).equals("Company")
        })

        ssc.start()
        ssc.awaitTermination()

    }
}
