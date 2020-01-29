package com.uzooin.requests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import com.uzooin.config.Config.app_url

object GetTokenRequest {
  val get_token = http("Get Token Name")
    .get(app_url + "/token")
    .check(status is 200)
    .check(jsonPath("$..token").saveAs("token"))
}
