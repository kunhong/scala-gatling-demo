package com.uzooin.requests

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

object Search {
  val email_feeder = Iterator.continually(Map("email" -> (Random.alphanumeric.take(20).mkString + "@foo.com")))
  val feeder = csv("user-files/search.csv").random // 1, 2

  val search = exec(http("Home Search") // let's give proper names, as they are displayed in the reports
    .get("/"))
    .pause(7)
    .exec(http("Search")
      .get("/computers?f=macbook"))
    .pause(2)
    .exec(http("Select")
      .get("/computers/6"))
    .pause(3)

  val random_search = exec(http("Home Random Search")
    .get("/"))
    .pause(1)
    .feed(feeder) // 3
    .exec(http("Search")
    .get("/computers?f=${searchCriterion}") // 4
    .check(css("a:contains('${searchComputerName}')", "href").saveAs("computerURL"))) // 5
    .pause(1)
    .exec(http("Select")
      .get("${computerURL}")) // 6
    .pause(1)
}
