package com.uzooin.scenarios

import com.uzooin.requests.{Browse, Search}
import io.gatling.core.Predef._
import io.gatling.core.session
import io.gatling.core.session.Expression
import io.gatling.http.Predef._

import scala.concurrent.duration._

object ComputerDataBaseScenario {
  var mainScenario = scenario("Search scenario").exec(Search.search)
}
