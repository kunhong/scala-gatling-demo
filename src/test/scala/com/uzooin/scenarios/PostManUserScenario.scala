package com.uzooin.scenarios

import com.uzooin.data.ArgsData
import com.uzooin.utils.JsonUtil
import io.gatling.core.Predef._
import io.gatling.core.session
import io.gatling.core.session.Expression
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.util.Random


object PostManUserScenario {
  val feeder = Iterator.continually(Map("email" -> (Random.alphanumeric.take(20).mkString + "@foo.com")))
  val data = List("aaa", "bbb", "ccc")

  def RunExec(index: String) = {
    exec(session=>{
      println(s"""index ===> ${session(index).as[String]} """)
      session
    })
  }

  var mainScenario = scenario("PostManUserScenario")
    .feed(feeder, 2)
    .exec(session=>{
      println("[[ kunhong ]] email1 : " + session("email1").as[String])
      println("[[ kunhong ]] email2 : " + session("email2").as[String])
      session
    })
    .foreach(data, "index") {
      exec(
        RunExec("index")
      )
    }
    .exec(
      http("GET postman-echo.com/get")
        .get("https://postman-echo.com/get")
        .queryParamMap(Map(
          "foo1" -> "bar1",
          "foo2" -> "bar2"
        ))
        .check(status is 200)
        .check(jsonPath("$.args").transform(body => JsonUtil.fromJson[ArgsData](body)).saveAs("args"))
    )
    .exec(session => {
      println("args => " + session("args").as[ArgsData].toString)
      session
    })

  var asLongAsScenario = scenario("AsLongAsScenario")
    .exec(session => session.set("flag", true))
    .asLongAsDuring(session => session("flag").as[Boolean], 5 seconds, "index") {
      exec(session => {
        println(session("index").as[Int])

        if (session("index").as[Int].equals(3)) {
          session.set("flag", false)
        } else {
          session
        }

      })
        .pause(1 seconds)
    }
    .during(5 seconds) {
      exec(session => {
        println(">>> Hello")
        session
      })
        .exec(
          http("GET postman-echo.com/get")
            .get("https://postman-echo.com/get")
            .queryParamMap(Map(
              "foo1" -> "bar1",
              "foo2" -> "bar2"
            ))
            .check(status is 200)
        ).pause(1)
    }

  var throttlingScenario = scenario("ThrottlingScenario")
    .exec(
      http("GET postman-echo.com/get")
        .get("https://postman-echo.com/get")
        .queryParamMap(Map(
          "foo1" -> "bar1",
          "foo2" -> "bar2"
        ))
        .check(status is 200)
    )


}
