package com.uzooin.requests

import io.gatling.core.Predef._
import io.gatling.http.Predef._


object Browse {
//  def gotoPage(page: Int) = exec(http("page " + page)
//  .get("computer?p=" + page))
//    .pause(1)
//
//  val browse = exec(gotoPage(0), gotoPage(1), gotoPage(2), gotoPage(3))
val browse = repeat(5, "n") { // 1
  exec(http("Page ${n}")
    .get("/computers?p=${n}")) // 2
    .pause(1)
}

}
