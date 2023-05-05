package com.nuc.online

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.rdd.RDD
import org.apache.spark.{SPARK_BRANCH, SparkConf}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.{Assign, Subscribe}
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

import java.text.SimpleDateFormat
import java.util.Date

/**
 * @Author：朱瑞敏
 * @Description： TODO 将Kafka负责分发的用于实时处理的数据，按照格式要求，进行处理，
 * */
object DataFormatOnline {

    val nowTime = new Date()
//    val time = new SimpleDateFormat("yyyyMMdd")
    nowTime.getTime
    val dateFormat = new SimpleDateFormat("yyyyMMdd")
    val toDay: String = dateFormat.format(nowTime)
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
            "bootstrap.servers" -> "hadoop101:9092,hadoop102:9092,hadoop103:9092",  // kafka集群地址
            "key.deserializer" -> classOf[StringDeserializer],
            "value.deserializer" -> classOf[StringDeserializer],
            "group.id" -> "uba",    // 消费者组名
            "auto.offset.reset" -> "latest",    // latest自动重置偏移量为最新的偏移量
            "enable.auto.commit" -> (false: java.lang.Boolean)  // 如果是true，则这个消费者的偏移量会在后台自动提交
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
            // Register-User-Success-u202305031933250444-咸-霭-女-10-重庆市-重庆市-大足区-03052023193325-13573571092-尼环美-无-无
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
            (user_id, surname, name, sex, age, province_name, city_name, county_name, create_time, phone, company, position, introduction, toDay)
        })
        userRegisterValuesDStream.foreachRDD((rdd:RDD[(String, String, String, String, String, String, String, String, String, String, String, String, String, String)]) => {
            val saveToUserRegisterOnlineSparkConf: SparkConf = new SparkConf().setAppName("saveToUserRegisterOnline")
            val saveToUserRegisterSparkSession: SparkSession = SparkSession.builder().config(saveToUserRegisterOnlineSparkConf).getOrCreate()
            import saveToUserRegisterSparkSession.implicits._
            val saveToUserRegisterDataFrame: DataFrame = rdd.toDF("user_id", "surname", "name", "sex", "age", "province_name", "city_name", "county_name", "create_time", "phone", "company", "position", "introduction", "ds")
            saveToUserRegisterDataFrame.createOrReplaceTempView("temp_stg_user_register_online_df")
            val table_name = "bs_ubs.stg_user_register_online_df"
            saveToUserRegisterDataFrame.write.insertInto(table_name)
            saveToUserRegisterSparkSession.table(table_name).show()

        })




        // 找出内容为企业注册的日志
        val companyRegisterDStream: DStream[(String, Array[String])] = registerDStream.filter(record => {
            val line: Array[String] = record._2
            line.apply(1).equals("Company")
        })
        val companyRegisterValuesDStream: DStream[(String, String, String, String, String, String, String, String, String, String, String)] = companyRegisterDStream.map(record => {
            val values: Array[String] = record._2
            // Register-Company-Success-c202305031933360438-森洁安丰正-15831720624-0hrg31@163.net-青海省-玉树藏族自治州-称多县-印刷-null-03052023193336
            var company_id: String = values.apply(3)
            if (company_id.isEmpty) {
                company_id = "-"
            }
            var name: String = values.apply(4)
            if (name.isEmpty) {
                name = "-"
            }
            var phone: String = values.apply(5)
            if (phone.isEmpty) {
                phone = "-"
            }
            var email: String = values.apply(6)
            if (email.isEmpty) {
                email = "-"
            }
            var province_name: String = values.apply(7)
            if (province_name.isEmpty) {
                province_name = "-"
            }
            var city_name: String = values.apply(8)
            if (city_name.isEmpty) {
                city_name = "-"
            }
            var county_name: String = values.apply(9)
            if (county_name.isEmpty) {
                county_name = "-"
            }
            var main_business: String = values.apply(10)
            if (main_business.isEmpty) {
                main_business = "-"
            }
            var slogan: String = values.apply(11)
            if (slogan.isEmpty) {
                slogan = "-"
            }
            var create_time: String = values.apply(12)
            if (create_time.isEmpty) {
                create_time = "-"
            }
            (company_id, name, phone, email, province_name, city_name, county_name, main_business, slogan, create_time, toDay)
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
